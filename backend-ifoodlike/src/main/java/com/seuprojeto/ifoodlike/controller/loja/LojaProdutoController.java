package com.seuprojeto.ifoodlike.controller.loja;

import com.seuprojeto.ifoodlike.dtos.cliente.ProdutoResumoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.ProdutoAtualizacaoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.ProdutoCriacaoDTO;
import com.seuprojeto.ifoodlike.service.loja.LojaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loja/produtos")
@RequiredArgsConstructor
@Tag(name = "Loja - Produtos", description = "Endpoints para gerenciamento de produtos pela loja")
@SecurityRequirement(name = "Bearer Authentication")
public class LojaProdutoController {

    private final LojaProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Cadastra um novo produto na loja")
    public ResponseEntity<ProdutoResumoDTO> criarProduto(@Valid @RequestBody ProdutoCriacaoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criarProduto(request));
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos da loja")
    public ResponseEntity<List<ProdutoResumoDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutosDaLoja());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<ProdutoResumoDTO> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoAtualizacaoDTO request) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove (inativa) um produto")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
