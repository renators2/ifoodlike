package com.seuprojeto.ifoodlike.service.cliente;

import com.seuprojeto.ifoodlike.dtos.cliente.CategoriaDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.ProdutoResumoDTO;
import com.seuprojeto.ifoodlike.mapper.ProdutoMapper;
import com.seuprojeto.ifoodlike.repository.CategoriaProdutoRepository;
import com.seuprojeto.ifoodlike.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaProdutoRepository categoriaRepository;
    private final ProdutoMapper produtoMapper;

    @Cacheable(value = "produtos", key = "'all'")
    public List<ProdutoResumoDTO> listarProdutosDisponiveis() {
        return produtoRepository.findAllDisponiveis()
                .stream()
                .map(produtoMapper::toResumoDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "produtos", key = "'loja_' + #lojaId")
    public List<ProdutoResumoDTO> listarProdutosPorLoja(Long lojaId) {
        return produtoRepository.findDisponivelByLojaId(lojaId)
                .stream()
                .map(produtoMapper::toResumoDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "produtos", key = "'categoria_' + #categoriaId")
    public List<ProdutoResumoDTO> listarProdutosPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaIdAndAtivoTrueAndDisponivelTrue(categoriaId)
                .stream()
                .map(produtoMapper::toResumoDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "categorias", key = "'all'")
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findByAtivaTrue()
                .stream()
                .map(cat -> CategoriaDTO.builder()
                        .id(cat.getId())
                        .nome(cat.getNome())
                        .descricao(cat.getDescricao())
                        .build())
                .collect(Collectors.toList());
    }
}
