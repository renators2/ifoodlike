package com.seuprojeto.ifoodlike.dtos.auth;

import com.seuprojeto.ifoodlike.enums.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;
    private String tipo;
    private Long usuarioId;
    private String email;
    private String nome;
    private PerfilUsuario perfil;
}
