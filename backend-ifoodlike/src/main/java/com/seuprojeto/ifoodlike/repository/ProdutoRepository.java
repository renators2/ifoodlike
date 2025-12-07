package com.seuprojeto.ifoodlike.repository;

import com.seuprojeto.ifoodlike.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByLojaIdAndAtivoTrue(Long lojaId);

    List<Produto> findByLojaId(Long lojaId);

    @Query("SELECT p FROM Produto p WHERE p.loja.id = :lojaId AND p.ativo = true AND p.disponivel = true")
    List<Produto> findDisponivelByLojaId(@Param("lojaId") Long lojaId);

    List<Produto> findByCategoriaIdAndAtivoTrueAndDisponivelTrue(Long categoriaId);

    @Query("SELECT p FROM Produto p WHERE p.ativo = true AND p.disponivel = true AND p.loja.ativa = true")
    List<Produto> findAllDisponiveis();
}
