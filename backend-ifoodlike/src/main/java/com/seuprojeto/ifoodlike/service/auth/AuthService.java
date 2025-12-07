package com.seuprojeto.ifoodlike.service.auth;

import com.seuprojeto.ifoodlike.dtos.auth.LoginRequestDTO;
import com.seuprojeto.ifoodlike.dtos.auth.LoginResponseDTO;
import com.seuprojeto.ifoodlike.mapper.UsuarioMapper;
import com.seuprojeto.ifoodlike.model.Usuario;
import com.seuprojeto.ifoodlike.repository.UsuarioRepository;
import com.seuprojeto.ifoodlike.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        String token = jwtTokenProvider.generateToken(authentication);

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return usuarioMapper.toLoginResponse(usuario, token);
    }
}
