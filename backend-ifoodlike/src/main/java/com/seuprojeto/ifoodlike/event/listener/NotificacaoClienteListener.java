package com.seuprojeto.ifoodlike.event.listener;

import com.seuprojeto.ifoodlike.event.StatusPedidoAlteradoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificacaoClienteListener {

    @EventListener
    public void handleStatusAlterado(StatusPedidoAlteradoEvent event) {
        log.info("Pedido #{} atualizado de {} para {} - Notificando cliente {}",
                event.getPedido().getId(),
                event.getStatusAnterior(),
                event.getStatusNovo(),
                event.getPedido().getCliente().getUsuario().getNome());

        // Aqui você pode implementar notificações para o cliente
        // Ex: Email, SMS, push notification, etc.
    }
}
