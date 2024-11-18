package com.project.challenge.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleTransacaoNaoEncontradaException(TransacaoNaoEncontradaException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocorreu um erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Captura exceções de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Coleta todas as mensagens de erro de validação
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    //TODO - Corrigir o método para identificar quando o CPF já está cadastrado e não estourar um internal server error
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();

        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        if (message != null) {
            if (message.toLowerCase().contains("cli_cpf")) {
                error.put("error", "CPF já cadastrado no sistema.");
            } else {
                error.put("error", "Violação de restrição de unicidade em um campo: " + message);
            }
        } else {
            error.put("error", "Erro de integridade dos dados.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
