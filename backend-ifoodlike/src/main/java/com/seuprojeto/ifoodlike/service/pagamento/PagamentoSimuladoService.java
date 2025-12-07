package com.seuprojeto.ifoodlike.service.pagamento;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PagamentoSimuladoService {

    public boolean processarPagamento(Long pedidoId, BigDecimal valor) {
        log.info("Processando pagamento simulado - Pedido: {}, Valor: R$ {}", pedidoId, valor);

        // Simula processamento
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Sempre retorna sucesso para simulação
        log.info("Pagamento aprovado - Pedido: {}", pedidoId);
        return true;
    }
}
