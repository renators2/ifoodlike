package com.seuprojeto.ifoodlike.dtos.cliente;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResumoDTO {

    private Long id;
    private String lojaNome;
    private StatusPedido status;
    private BigDecimal total;
    private Integer quantidadeItens;
    private LocalDateTime createdAt;
}
