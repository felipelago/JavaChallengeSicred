package com.project.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.challenge.entity.Cliente;
import com.project.challenge.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrarCliente_DeveRetornarClienteCriado() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678901");
        cliente.setLimiteCredito(new BigDecimal("2000.00"));
        cliente.setNumeroCartao("1234567812345678");

        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        // Converte o objeto Cliente em uma string JSON
        String clienteJson = objectMapper.writeValueAsString(cliente);

        mockMvc.perform(post("/api/v1/cliente/cadastrar")
                        .contentType("application/json")
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.limiteCredito").value(2000.00))
                .andExpect(jsonPath("$.numeroCartao").value("1234567812345678"));
    }

    @Test
    void listarClientes_DeveRetornarListaDeClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");

        when(clienteService.listarClientes()).thenReturn(Collections.singletonList(cliente));

        mockMvc.perform(get("/api/v1/cliente/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Maria"));
    }

}