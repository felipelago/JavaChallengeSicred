package com.project.challenge.service;

import com.project.challenge.dto.TransacaoDto;
import com.project.challenge.entity.Cliente;
import com.project.challenge.entity.Transacao;
import com.project.challenge.exception.ClienteNaoEncontradoException;
import com.project.challenge.exception.TransacaoNaoEncontradaException;
import com.project.challenge.repository.ClienteRepository;
import com.project.challenge.repository.TransacaoRepository;
import com.project.challenge.valueObject.Descricao;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTransacoes_DeveRetornarListaDeTransacoes() {
        Transacao transacao = criarTransacao();
        when(transacaoRepository.findAll()).thenReturn(Arrays.asList(transacao));

        var resultado = pagamentoService.listarTransacoes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(transacao.getCartao(), resultado.get(0).getCartao());
    }

    @Test
    void consultarTransacaoPorId_DeveRetornarTransacao() {
        Long id = 1L;
        Transacao transacao = criarTransacao();
        when(transacaoRepository.findById(id)).thenReturn(Optional.of(transacao));

        var resultado = pagamentoService.consultarTransacaoPorId(id);

        assertNotNull(resultado);
        assertEquals(transacao.getCartao(), resultado.getCartao());
    }

    @Test
    void consultarTransacaoPorId_DeveLancarExcecaoSeNaoEncontrada() {
        Long id = 1L;
        when(transacaoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TransacaoNaoEncontradaException.class, () -> pagamentoService.consultarTransacaoPorId(id));
    }

    @Test
    void realizarPagamento_DeveSalvarTransacaoAutorizada() {
        TransacaoDto transacaoDto = criarTransacaoDto();
        Cliente cliente = criarCliente();

        when(clienteRepository.findByNumeroCartao(transacaoDto.getCartao())).thenReturn(Optional.of(cliente));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = pagamentoService.realizarPagamento(transacaoDto);

        assertNotNull(resultado);
        assertEquals(transacaoDto.getCartao(), resultado.getCartao());
        assertEquals(Descricao.StatusTransacao.AUTORIZADO, resultado.getDescricao().getStatus());
    }

    @Test
    void realizarPagamento_DeveLancarExcecaoSeClienteNaoEncontrado() {
        TransacaoDto transacaoDto = criarTransacaoDto();

        when(clienteRepository.findByNumeroCartao(transacaoDto.getCartao())).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> pagamentoService.realizarPagamento(transacaoDto));
    }

    @Test
    void estornarPagamento_DeveAlterarStatusParaCancelado() {
        Long id = 1L;
        Transacao transacao = criarTransacao();
        when(transacaoRepository.findById(id)).thenReturn(Optional.of(transacao));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = pagamentoService.estornarPagamento(id);

        assertNotNull(resultado);
        assertEquals(Descricao.StatusTransacao.CANCELADO, resultado.getDescricao().getStatus());
    }

    @Test
    void estornarPagamento_DeveLancarExcecaoSeTransacaoNaoEncontrada() {
        Long id = 1L;
        when(transacaoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TransacaoNaoEncontradaException.class, () -> pagamentoService.estornarPagamento(id));
    }

    // Métodos auxiliares para criação de objetos
    private Transacao criarTransacao() {
        Transacao transacao = new Transacao();
        transacao.setCartao("1234567812345678");

        Descricao descricao = new Descricao();
        descricao.setStatus(Descricao.StatusTransacao.AUTORIZADO);
        descricao.setNsu("12345678901");
        descricao.setCodigoAutorizacao("12345678901");
        descricao.setValor(BigDecimal.valueOf(200.00));
        descricao.setEstabelecimento("Pet Shop");
        descricao.setDataHora(LocalDateTime.now());

        transacao.setDescricao(descricao);
        return transacao;
    }

    private TransacaoDto criarTransacaoDto() {
        TransacaoDto transacaoDto = new TransacaoDto();
        transacaoDto.setCartao("1234567812345678");

        Descricao descricao = new Descricao();
        descricao.setValor(BigDecimal.valueOf(200.00));
        descricao.setEstabelecimento("Pet Shop");

        transacaoDto.setDescricao(descricao);
        return transacaoDto;
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNumeroCartao("1234567812345678");
        cliente.setLimiteCredito(BigDecimal.valueOf(300.00));
        return cliente;
    }
}
