package com.seuprojeto.ifoodlike.dtos.loja;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoAtualizacaoDTO {

    private String nome;

    private String descricao;

    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;

    private Long categoriaId;

    private String imagemUrl;

    private Boolean disponivel;
}
