package com.project.challenge.service;

import com.project.challenge.entity.Cliente;
import com.project.challenge.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void cadastrarCliente_DeveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678901");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);
        assertEquals("João Silva", clienteSalvo.getNome());
        assertEquals("12345678901", clienteSalvo.getCpf());
    }

    @Test
    void listarClientes_DeveRetornarListaDeClientes() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Collections.singletonList(cliente));

        List<Cliente> clientes = clienteService.listarClientes();
        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
    }
}

