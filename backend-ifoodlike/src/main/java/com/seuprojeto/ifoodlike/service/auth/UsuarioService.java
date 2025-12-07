package com.seuprojeto.ifoodlike.service.auth;

import com.seuprojeto.ifoodlike.dtos.auth.LoginResponseDTO;
import com.seuprojeto.ifoodlike.dtos.auth.UsuarioCadastroDTO;
import com.seuprojeto.ifoodlike.enums.PerfilUsuario;
import com.seuprojeto.ifoodlike.exception.NegocioException;
import com.seuprojeto.ifoodlike.mapper.UsuarioMapper;
import com.seuprojeto.ifoodlike.model.Cliente;
import com.seuprojeto.ifoodlike.model.Loja;
import com.seuprojeto.ifoodlike.model.Usuario;
import com.seuprojeto.ifoodlike.repository.ClienteRepository;
import com.seuprojeto.ifoodlike.repository.LojaRepository;
import com.seuprojeto.ifoodlike.repository.UsuarioRepository;
import com.seuprojeto.ifoodlike.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final LojaRepository lojaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioMapper usuarioMapper;
    private final UserDetailsService userDetailsService;

    @Transactional
    public LoginResponseDTO cadastrar(UsuarioCadastroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new NegocioException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfil(dto.getPerfil())
                .ativo(true)
                .build();

        usuario = usuarioRepository.save(usuario);

        if (dto.getPerfil() == PerfilUsuario.CLIENTE) {
            criarCliente(usuario, dto);
        } else if (dto.getPerfil() == PerfilUsuario.LOJA) {
            criarLoja(usuario, dto);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        String token = jwtTokenProvider.generateToken(auth);

        return usuarioMapper.toLoginResponse(usuario, token);
    }

    private void criarCliente(Usuario usuario, UsuarioCadastroDTO dto) {
        Cliente cliente = Cliente.builder()
                .usuario(usuario)
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();
        clienteRepository.save(cliente);
    }

    private void criarLoja(Usuario usuario, UsuarioCadastroDTO dto) {
        if (dto.getCnpj() == null || dto.getCnpj().isBlank()) {
            throw new NegocioException("CNPJ é obrigatório para cadastro de loja");
        }
        if (dto.getNomeFantasia() == null || dto.getNomeFantasia().isBlank()) {
            throw new NegocioException("Nome fantasia é obrigatório para cadastro de loja");
        }
        if (lojaRepository.existsByCnpj(dto.getCnpj())) {
            throw new NegocioException("CNPJ já cadastrado");
        }

        Loja loja = Loja.builder()
                .usuario(usuario)
                .nomeFantasia(dto.getNomeFantasia())
                .cnpj(dto.getCnpj())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .ativa(true)
                .build();
        lojaRepository.save(loja);
    }
}
