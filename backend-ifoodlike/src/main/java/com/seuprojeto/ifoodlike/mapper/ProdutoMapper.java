package com.seuprojeto.ifoodlike.mapper;

import com.seuprojeto.ifoodlike.dtos.cliente.ProdutoResumoDTO;
import com.seuprojeto.ifoodlike.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoResumoDTO toResumoDTO(Produto produto) {
        return ProdutoResumoDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .imagemUrl(produto.getImagemUrl())
                .categoriaNome(produto.getCategoria().getNome())
                .lojaId(produto.getLoja().getId())
                .lojaNome(produto.getLoja().getNomeFantasia())
                .build();
    }
}
