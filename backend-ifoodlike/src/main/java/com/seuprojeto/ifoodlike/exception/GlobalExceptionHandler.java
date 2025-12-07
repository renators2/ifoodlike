package com.seuprojeto.ifoodlike.exception;

import com.seuprojeto.ifoodlike.dtos.comum.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ApiErrorDTO> handleEntidadeNaoEncontrada(
            EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Não Encontrado")
                .mensagem(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiErrorDTO> handleNegocio(
            NegocioException ex, HttpServletRequest request) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Erro de Negócio")
                .mensagem(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiErrorDTO> handleValidacao(
            ValidacaoException ex, HttpServletRequest request) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Erro de Validação")
                .mensagem(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> detalhes = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Erro de Validação")
                .mensagem("Campos inválidos")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .detalhes(detalhes)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ApiErrorDTO> handleAuthenticationException(
            Exception ex, HttpServletRequest request) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .erro("Não Autorizado")
                .mensagem("Credenciais inválidas")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGenericException(
            Exception ex, HttpServletRequest request) {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .erro("Erro Interno")
                .mensagem("Ocorreu um erro inesperado")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
