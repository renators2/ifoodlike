package com.seuprojeto.ifoodlike.controller.auth;

import com.seuprojeto.ifoodlike.dtos.auth.LoginRequestDTO;
import com.seuprojeto.ifoodlike.dtos.auth.LoginResponseDTO;
import com.seuprojeto.ifoodlike.dtos.auth.UsuarioCadastroDTO;
import com.seuprojeto.ifoodlike.service.auth.AuthService;
import com.seuprojeto.ifoodlike.service.auth.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de autenticação e cadastro")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna o token JWT")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário (cliente ou loja)")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody UsuarioCadastroDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(request));
    }
}
