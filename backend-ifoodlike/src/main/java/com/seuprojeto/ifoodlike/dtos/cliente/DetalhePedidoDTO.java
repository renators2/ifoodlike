package com.seuprojeto.ifoodlike.dtos.cliente;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalhePedidoDTO {

    private Long id;
    private Long lojaId;
    private String lojaNome;
    private StatusPedido status;
    private BigDecimal total;
    private String observacao;
    private String enderecoEntrega;
    private List<ItemPedidoDTO> itens;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
