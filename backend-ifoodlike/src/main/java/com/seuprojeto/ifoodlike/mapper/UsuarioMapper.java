package com.seuprojeto.ifoodlike.mapper;

import com.seuprojeto.ifoodlike.dtos.auth.LoginResponseDTO;
import com.seuprojeto.ifoodlike.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public LoginResponseDTO toLoginResponse(Usuario usuario, String token) {
        return LoginResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .usuarioId(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .perfil(usuario.getPerfil())
                .build();
    }
}
