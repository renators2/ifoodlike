package com.seuprojeto.ifoodlike.dtos.cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCriacaoDTO {

    @NotNull(message = "Loja é obrigatória")
    private Long lojaId;

    @NotEmpty(message = "Pedido deve conter ao menos um item")
    @Valid
    private List<ItemPedidoCriacaoDTO> itens;

    private String observacao;

    private String enderecoEntrega;
}
