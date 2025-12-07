package com.seuprojeto.ifoodlike.controller.cliente;

import com.seuprojeto.ifoodlike.dtos.cliente.DetalhePedidoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.PedidoCriacaoDTO;
import com.seuprojeto.ifoodlike.dtos.cliente.PedidoResumoDTO;
import com.seuprojeto.ifoodlike.service.cliente.ClientePedidoService;
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
@RequestMapping("/cliente/pedidos")
@RequiredArgsConstructor
@Tag(name = "Cliente - Pedidos", description = "Endpoints para gerenciamento de pedidos pelo cliente")
@SecurityRequirement(name = "Bearer Authentication")
public class ClientePedidoController {

    private final ClientePedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido para o cliente logado")
    public ResponseEntity<DetalhePedidoDTO> criarPedido(@Valid @RequestBody PedidoCriacaoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(request));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Lista todos os pedidos do cliente logado")
    public ResponseEntity<List<PedidoResumoDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidosCliente());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido", description = "Busca os detalhes de um pedido específico")
    public ResponseEntity<DetalhePedidoDTO> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPedidoPorId(id));
    }
}
