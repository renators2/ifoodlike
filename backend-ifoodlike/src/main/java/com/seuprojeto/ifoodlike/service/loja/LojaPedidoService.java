package com.seuprojeto.ifoodlike.service.loja;

import com.seuprojeto.ifoodlike.dtos.loja.AtualizarStatusPedidoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.PedidoCozinhaDTO;
import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.event.publisher.PedidoEventPublisher;
import com.seuprojeto.ifoodlike.exception.EntidadeNaoEncontradaException;
import com.seuprojeto.ifoodlike.exception.NegocioException;
import com.seuprojeto.ifoodlike.mapper.PedidoMapper;
import com.seuprojeto.ifoodlike.model.Loja;
import com.seuprojeto.ifoodlike.model.Pedido;
import com.seuprojeto.ifoodlike.repository.LojaRepository;
import com.seuprojeto.ifoodlike.repository.PedidoRepository;
import com.seuprojeto.ifoodlike.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaPedidoService {

    private final PedidoRepository pedidoRepository;
    private final LojaRepository lojaRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public List<PedidoCozinhaDTO> listarPedidosCozinha(StatusPedido status) {
        Loja loja = getLojaLogada();

        List<Pedido> pedidos;
        if (status != null) {
            pedidos = pedidoRepository.findByLojaIdAndStatusOrderByCreatedAtAsc(loja.getId(), status);
        } else {
            pedidos = pedidoRepository.findPedidosAtivosByLojaId(loja.getId());
        }

        return pedidos.stream()
                .map(pedidoMapper::toCozinhaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoCozinhaDTO atualizarStatus(Long pedidoId, AtualizarStatusPedidoDTO dto) {
        Loja loja = getLojaLogada();

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido", pedidoId));

        if (!pedido.getLoja().getId().equals(loja.getId())) {
            throw new NegocioException("Pedido não pertence à sua loja");
        }

        StatusPedido statusAnterior = pedido.getStatus();
        StatusPedido statusNovo = dto.getStatus();

        validarTransicaoStatus(statusAnterior, statusNovo);

        pedido.setStatus(statusNovo);
        pedido = pedidoRepository.save(pedido);

        eventPublisher.publicarStatusAlterado(pedido, statusAnterior, statusNovo);

        return pedidoMapper.toCozinhaDTO(pedido);
    }

    private void validarTransicaoStatus(StatusPedido atual, StatusPedido novo) {
        boolean valido = switch (atual) {
            case CRIADO -> novo == StatusPedido.EM_PREPARO || novo == StatusPedido.CANCELADO;
            case EM_PREPARO -> novo == StatusPedido.PRONTO || novo == StatusPedido.CANCELADO;
            case PRONTO -> novo == StatusPedido.ENTREGUE;
            case ENTREGUE, CANCELADO -> false;
        };

        if (!valido) {
            throw new NegocioException(
                    String.format("Transição de status inválida: %s -> %s", atual, novo)
            );
        }
    }

    private Loja getLojaLogada() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return lojaRepository.findByUsuarioId(userDetails.getId())
                .orElseThrow(() -> new NegocioException("Loja não encontrada para o usuário logado"));
    }
}
