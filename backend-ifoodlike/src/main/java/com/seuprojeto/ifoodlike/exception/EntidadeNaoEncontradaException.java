package com.seuprojeto.ifoodlike.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public EntidadeNaoEncontradaException(String entidade, Long id) {
        super(String.format("%s não encontrado(a) com ID: %d", entidade, id));
    }
}
