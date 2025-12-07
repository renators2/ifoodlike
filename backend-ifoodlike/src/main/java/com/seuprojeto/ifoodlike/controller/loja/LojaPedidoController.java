package com.seuprojeto.ifoodlike.controller.loja;

import com.seuprojeto.ifoodlike.dtos.loja.AtualizarStatusPedidoDTO;
import com.seuprojeto.ifoodlike.dtos.loja.PedidoCozinhaDTO;
import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.service.loja.LojaPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loja/pedidos")
@RequiredArgsConstructor
@Tag(name = "Loja - Pedidos", description = "Endpoints para gerenciamento de pedidos pela loja (cozinha)")
@SecurityRequirement(name = "Bearer Authentication")
public class LojaPedidoController {

    private final LojaPedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar pedidos da cozinha", description = "Lista pedidos ativos da loja para a cozinha")
    public ResponseEntity<List<PedidoCozinhaDTO>> listarPedidos(
            @RequestParam(required = false) StatusPedido status) {
        return ResponseEntity.ok(pedidoService.listarPedidosCozinha(status));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido")
    public ResponseEntity<PedidoCozinhaDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusPedidoDTO request) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, request));
    }
}
