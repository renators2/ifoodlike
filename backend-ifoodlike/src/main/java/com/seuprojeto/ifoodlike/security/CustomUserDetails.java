package com.seuprojeto.ifoodlike.security;

import com.seuprojeto.ifoodlike.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String senha;
    private final String nome;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean ativo;

    public CustomUserDetails(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.authorities = List.of(new SimpleGrantedAuthority(usuario.getPerfil().name()));
        this.ativo = usuario.getAtivo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
