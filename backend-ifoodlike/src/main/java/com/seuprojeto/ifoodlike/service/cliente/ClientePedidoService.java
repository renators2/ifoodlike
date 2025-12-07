package com.seuprojeto.ifoodlike.service.cliente;

import com.seuprojeto.ifoodlike.dtos.cliente.DetalhePedidoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.ItemPedidoCriacaoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.PedidoCriacaoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.PedidoResumoDTO;
import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.event.publisher.PedidoEventPublisher;
import com.seuprojeto.ifoodlike.exception.EntidadeNaoEncontradaException;
import com.seuprojeto.ifoodlike.exception.NegocioException;
import com.seuprojeto.ifoodlike.mapper.PedidoMapper;
import com.seuprojeto.ifoodlike.model.*;
import com.seuprojeto.ifoodlike.repository.*;
import com.seuprojeto.ifoodlike.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientePedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final LojaRepository lojaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoEventPublisher eventPublisher;

    @Transactional
    public DetalhePedidoDTO criarPedido(PedidoCriacaoDTO dto) {
        Cliente cliente = getClienteLogado();

        Loja loja = lojaRepository.findById(dto.getLojaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Loja", dto.getLojaId()));

        if (!loja.getAtiva()) {
            throw new NegocioException("Loja não está ativa");
        }

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .loja(loja)
                .status(StatusPedido.CRIADO)
                .observacao(dto.getObservacao())
                .enderecoEntrega(dto.getEnderecoEntrega() != null ? dto.getEnderecoEntrega() : cliente.getEndereco())
                .total(BigDecimal.ZERO)
                .build();

        for (ItemPedidoCriacaoDTO itemDto : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto", itemDto.getProdutoId()));

            if (!produto.getLoja().getId().equals(loja.getId())) {
                throw new NegocioException("Produto não pertence à loja selecionada");
            }

            if (!produto.getDisponivel() || !produto.getAtivo()) {
                throw new NegocioException("Produto " + produto.getNome() + " não está disponível");
            }

            ItemPedido item = ItemPedido.builder()
                    .produto(produto)
                    .quantidade(itemDto.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .subtotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDto.getQuantidade())))
                    .observacao(itemDto.getObservacao())
                    .build();

            pedido.adicionarItem(item);
        }

        pedido.calcularTotal();
        pedido = pedidoRepository.save(pedido);

        eventPublisher.publicarPedidoCriado(pedido);

        return pedidoMapper.toDetalheDTO(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResumoDTO> listarPedidosCliente() {
        Cliente cliente = getClienteLogado();
        return pedidoRepository.findByClienteIdOrderByCreatedAtDesc(cliente.getId())
                .stream()
                .map(pedidoMapper::toResumoDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetalhePedidoDTO buscarPedidoPorId(Long pedidoId) {
        Cliente cliente = getClienteLogado();
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido", pedidoId));

        if (!pedido.getCliente().getId().equals(cliente.getId())) {
            throw new NegocioException("Pedido não pertence ao cliente");
        }

        return pedidoMapper.toDetalheDTO(pedido);
    }

    private Cliente getClienteLogado() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return clienteRepository.findByUsuarioId(userDetails.getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado para o usuário logado"));
    }
}
