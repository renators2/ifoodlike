package com.seuprojeto.ifoodlike.repository;

import com.seuprojeto.ifoodlike.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

    Optional<Loja> findByUsuarioId(Long usuarioId);

    Optional<Loja> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
