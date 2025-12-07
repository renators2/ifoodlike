package com.seuprojeto.ifoodlike.event.listener;

import com.seuprojeto.ifoodlike.event.PedidoCriadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificacaoCozinhaListener {

    @EventListener
    public void handlePedidoCriado(PedidoCriadoEvent event) {
        log.info("Novo pedido #{} recebido na cozinha da loja {}",
                event.getPedido().getId(),
                event.getPedido().getLoja().getNomeFantasia());

        // Aqui você pode implementar notificações em tempo real
        // Ex: WebSocket, push notification, etc.
    }
}
