package com.seuprojeto.ifoodlike.dtos.loja;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusPedidoDTO {

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;
}
