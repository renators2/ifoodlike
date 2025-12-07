package com.seuprojeto.ifoodlike.event;

import com.seuprojeto.ifoodlike.enums.StatusPedido;
import com.seuprojeto.ifoodlike.model.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StatusPedidoAlteradoEvent extends ApplicationEvent {

    private final Pedido pedido;
    private final StatusPedido statusAnterior;
    private final StatusPedido statusNovo;

    public StatusPedidoAlteradoEvent(Object source, Pedido pedido, StatusPedido statusAnterior, StatusPedido statusNovo) {
        super(source);
        this.pedido = pedido;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
    }
}
