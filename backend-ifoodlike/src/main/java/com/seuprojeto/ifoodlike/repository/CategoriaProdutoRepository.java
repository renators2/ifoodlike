package com.seuprojeto.ifoodlike.repository;

import com.seuprojeto.ifoodlike.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    List<CategoriaProduto> findByAtivaTrue();

    boolean existsByNome(String nome);
}
