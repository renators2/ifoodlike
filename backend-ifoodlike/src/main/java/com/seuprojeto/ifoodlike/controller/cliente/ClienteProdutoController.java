package com.seuprojeto.ifoodlike.controller.cliente;

import com.seuprojeto.ifoodlike.dtos.cliente.CategoriaDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.ProdutoResumoDTO;
import com.seuprojeto.ifoodlike.service.cliente.ClienteProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Tag(name = "Cliente - Produtos", description = "Endpoints para consulta de produtos pelo cliente")
public class ClienteProdutoController {

    private final ClienteProdutoService produtoService;

    @GetMapping("/produtos")
    @Operation(summary = "Listar produtos disponíveis", description = "Lista todos os produtos disponíveis para compra")
    public ResponseEntity<List<ProdutoResumoDTO>> listarProdutos(
            @RequestParam(required = false) Long lojaId,
            @RequestParam(required = false) Long categoriaId) {

        List<ProdutoResumoDTO> produtos;

        if (lojaId != null) {
            produtos = produtoService.listarProdutosPorLoja(lojaId);
        } else if (categoriaId != null) {
            produtos = produtoService.listarProdutosPorCategoria(categoriaId);
        } else {
            produtos = produtoService.listarProdutosDisponiveis();
        }

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias ativas")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(produtoService.listarCategorias());
    }
}
