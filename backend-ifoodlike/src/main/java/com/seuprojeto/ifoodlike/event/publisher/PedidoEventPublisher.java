package com.seuprojeto.ifoodlike.event.publisher;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.event.PedidoCriadoEvent;
import com.seuprojeto.ifoodlike.event.StatusPedidoAlteradoEvent;
import com.seuprojeto.ifoodlike.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publicarPedidoCriado(Pedido pedido) {
        eventPublisher.publishEvent(new PedidoCriadoEvent(this, pedido));
    }

    public void publicarStatusAlterado(Pedido pedido, StatusPedido statusAnterior, StatusPedido statusNovo) {
        eventPublisher.publishEvent(new StatusPedidoAlteradoEvent(this, pedido, statusAnterior, statusNovo));
    }
}
