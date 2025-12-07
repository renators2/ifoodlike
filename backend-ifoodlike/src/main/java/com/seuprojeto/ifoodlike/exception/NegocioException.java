package com.seuprojeto.ifoodlike.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
