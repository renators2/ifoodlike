package com.seuprojeto.ifoodlike.repository;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByCreatedAtDesc(Long clienteId);

    List<Pedido> findByLojaIdOrderByCreatedAtDesc(Long lojaId);

    List<Pedido> findByLojaIdAndStatusOrderByCreatedAtAsc(Long lojaId, StatusPedido status);

    @Query("SELECT p FROM Pedido p WHERE p.loja.id = :lojaId AND p.status IN :statuses ORDER BY p.createdAt ASC")
    List<Pedido> findByLojaIdAndStatusIn(@Param("lojaId") Long lojaId, @Param("statuses") List<StatusPedido> statuses);

    @Query("SELECT p FROM Pedido p WHERE p.loja.id = :lojaId AND p.status NOT IN ('ENTREGUE', 'CANCELADO') ORDER BY p.createdAt ASC")
    List<Pedido> findPedidosAtivosByLojaId(@Param("lojaId") Long lojaId);
}
