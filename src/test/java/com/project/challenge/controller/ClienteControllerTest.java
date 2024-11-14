package com.project.challenge.controller;

import com.project.challenge.entity.Cliente;
import com.project.challenge.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void cadastrarCliente_DeveRetornarClienteCriado() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678901");

        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/cliente/cadastrar")
                        .contentType("application/json")
                        .content("{\"nome\": \"João Silva\", \"cpf\": \"12345678901\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
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