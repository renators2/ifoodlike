package com.seuprojeto.ifoodlike.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResumoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagemUrl;
    private String categoriaNome;
    private Long lojaId;
    private String lojaNome;
}
