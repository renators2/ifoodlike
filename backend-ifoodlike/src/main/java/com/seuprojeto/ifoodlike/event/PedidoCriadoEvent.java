package com.seuprojeto.ifoodlike.event;

import com.seuprojeto.ifoodlike.model.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PedidoCriadoEvent extends ApplicationEvent {

    private final Pedido pedido;

    public PedidoCriadoEvent(Object source, Pedido pedido) {
        super(source);
        this.pedido = pedido;
    }
}
