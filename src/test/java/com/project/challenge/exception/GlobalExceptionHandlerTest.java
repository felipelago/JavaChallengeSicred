package com.project.challenge.exception;

import com.project.challenge.exception.ClienteNaoEncontradoException;
import com.project.challenge.exception.GlobalExceptionHandler;
import com.project.challenge.exception.TransacaoNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Configura o MockMvc com o GlobalExceptionHandler
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleClienteNaoEncontradoException_DeveRetornar404() throws Exception {
        mockMvc.perform(get("/test/cliente-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Cliente não encontrado."));
    }

    @Test
    void handleTransacaoNaoEncontradaException_DeveRetornar404() throws Exception {
        mockMvc.perform(get("/test/transacao-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Transação não encontrada."));
    }

//    @Test
//    void handleValidationExceptions_DeveRetornar400() throws Exception {
//        mockMvc.perform(get("/test/validation-error"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.campo").value("Valor obrigatório."));
//    }

    @Test
    void handleDataIntegrityViolationException_DeveRetornar409() throws Exception {
        mockMvc.perform(get("/test/data-integrity-error"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CPF já cadastrado no sistema."));
    }

    @Test
    void handleGlobalException_DeveRetornar500() throws Exception {
        mockMvc.perform(get("/test/general-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Ocorreu um erro interno no servidor."));
    }

    // Controlador de teste para simular exceções
    @org.springframework.stereotype.Controller
    static class TestController {
        @GetMapping("/test/cliente-not-found")
        public void clienteNotFound() {
            throw new ClienteNaoEncontradoException("Cliente não encontrado.");
        }

        @GetMapping("/test/transacao-not-found")
        public void transacaoNotFound() {
            throw new TransacaoNaoEncontradaException("Transação não encontrada.");
        }

        @GetMapping("/test/data-integrity-error")
        public void dataIntegrityError() {
            throw new DataIntegrityViolationException("cli_cpf");
        }

        @GetMapping("/test/general-error")
        public void generalError() {
            throw new RuntimeException("Erro geral.");
        }
    }
}
