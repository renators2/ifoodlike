package com.seuprojeto.ifoodlike.service.loja;

import com.seuprojeto.ifoodlike.dtos.cliente.ProdutoResumoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.ProdutoAtualizacaoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.ProdutoCriacaoDTO;
import com.seuprojeto.ifoodlike.exception.EntidadeNaoEncontradaException;
import com.seuprojeto.ifoodlike.exception.NegocioException;
import com.seuprojeto.ifoodlike.mapper.ProdutoMapper;
import com.seuprojeto.ifoodlike.model.CategoriaProduto;
import com.seuprojeto.ifoodlike.model.Loja;
import com.seuprojeto.ifoodlike.model.Produto;
import com.seuprojeto.ifoodlike.repository.CategoriaProdutoRepository;
import com.seuprojeto.ifoodlike.repository.LojaRepository;
import com.seuprojeto.ifoodlike.repository.ProdutoRepository;
import com.seuprojeto.ifoodlike.security.CustomUserDetails;
import com.seuprojeto.ifoodlike.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaProdutoService {

    private final ProdutoRepository produtoRepository;
    private final LojaRepository lojaRepository;
    private final CategoriaProdutoRepository categoriaRepository;
    private final ProdutoMapper produtoMapper;
    private final CacheService cacheService;

    @Transactional
    public ProdutoResumoDTO criarProduto(ProdutoCriacaoDTO dto) {
        Loja loja = getLojaLogada();

        CategoriaProduto categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria", dto.getCategoriaId()));

        Produto produto = Produto.builder()
                .loja(loja)
                .categoria(categoria)
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .imagemUrl(dto.getImagemUrl())
                .disponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true)
                .ativo(true)
                .build();

        produto = produtoRepository.save(produto);
        cacheService.evictCache("produtos");

        return produtoMapper.toResumoDTO(produto);
    }

    @Transactional
    public ProdutoResumoDTO atualizarProduto(Long produtoId, ProdutoAtualizacaoDTO dto) {
        Loja loja = getLojaLogada();

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto", produtoId));

        if (!produto.getLoja().getId().equals(loja.getId())) {
            throw new NegocioException("Produto não pertence à sua loja");
        }

        if (dto.getNome() != null) {
            produto.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            produto.setDescricao(dto.getDescricao());
        }
        if (dto.getPreco() != null) {
            produto.setPreco(dto.getPreco());
        }
        if (dto.getImagemUrl() != null) {
            produto.setImagemUrl(dto.getImagemUrl());
        }
        if (dto.getDisponivel() != null) {
            produto.setDisponivel(dto.getDisponivel());
        }
        if (dto.getCategoriaId() != null) {
            CategoriaProduto categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria", dto.getCategoriaId()));
            produto.setCategoria(categoria);
        }

        produto = produtoRepository.save(produto);
        cacheService.evictCache("produtos");

        return produtoMapper.toResumoDTO(produto);
    }

    @Transactional
    public void deletarProduto(Long produtoId) {
        Loja loja = getLojaLogada();

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto", produtoId));

        if (!produto.getLoja().getId().equals(loja.getId())) {
            throw new NegocioException("Produto não pertence à sua loja");
        }

        produto.setAtivo(false);
        produto.setDisponivel(false);
        produtoRepository.save(produto);
        cacheService.evictCache("produtos");
    }

    @Transactional(readOnly = true)
    public List<ProdutoResumoDTO> listarProdutosDaLoja() {
        Loja loja = getLojaLogada();
        return produtoRepository.findByLojaId(loja.getId())
                .stream()
                .map(produtoMapper::toResumoDTO)
                .collect(Collectors.toList());
    }

    private Loja getLojaLogada() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return lojaRepository.findByUsuarioId(userDetails.getId())
                .orElseThrow(() -> new NegocioException("Loja não encontrada para o usuário logado"));
    }
}
