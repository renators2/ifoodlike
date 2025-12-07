package com.seuprojeto.ifoodlike.mapper;

import com.seuprojeto.ifoodlike.dtos.cliente.DetalhePedidoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.ItemPedidoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.PedidoResumoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.PedidoCozinhaDTO;
import com.seuprojeto.ifoodlike.model.ItemPedido;
import com.seuprojeto.ifoodlike.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoResumoDTO toResumoDTO(Pedido pedido) {
        return PedidoResumoDTO.builder()
                .id(pedido.getId())
                .lojaNome(pedido.getLoja().getNomeFantasia())
                .status(pedido.getStatus())
                .total(pedido.getTotal())
                .quantidadeItens(pedido.getItens().size())
                .createdAt(pedido.getCreatedAt())
                .build();
    }

    public DetalhePedidoDTO toDetalheDTO(Pedido pedido) {
        return DetalhePedidoDTO.builder()
                .id(pedido.getId())
                .lojaId(pedido.getLoja().getId())
                .lojaNome(pedido.getLoja().getNomeFantasia())
                .status(pedido.getStatus())
                .total(pedido.getTotal())
                .observacao(pedido.getObservacao())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .itens(toItemDTOList(pedido.getItens()))
                .createdAt(pedido.getCreatedAt())
                .updatedAt(pedido.getUpdatedAt())
                .build();
    }

    public PedidoCozinhaDTO toCozinhaDTO(Pedido pedido) {
        return PedidoCozinhaDTO.builder()
                .id(pedido.getId())
                .clienteNome(pedido.getCliente().getUsuario().getNome())
                .status(pedido.getStatus())
                .total(pedido.getTotal())
                .observacao(pedido.getObservacao())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .itens(toItemDTOList(pedido.getItens()))
                .createdAt(pedido.getCreatedAt())
                .build();
    }

    public ItemPedidoDTO toItemDTO(ItemPedido item) {
        return ItemPedidoDTO.builder()
                .id(item.getId())
                .produtoNome(item.getProduto().getNome())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(item.getSubtotal())
                .observacao(item.getObservacao())
                .build();
    }

    public List<ItemPedidoDTO> toItemDTOList(List<ItemPedido> itens) {
        return itens.stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
    }
}
