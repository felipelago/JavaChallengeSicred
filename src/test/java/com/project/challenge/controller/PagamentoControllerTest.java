package com.project.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.challenge.dto.TransacaoDto;
import com.project.challenge.entity.Transacao;
import com.project.challenge.service.PagamentoService;
import com.project.challenge.valueObject.Descricao;
import com.project.challenge.valueObject.FormaPagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoService pagamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transacao criarTransacao() {
        Transacao transacao = new Transacao();
        transacao.setCartao("1234567812345678");

        Descricao descricao = new Descricao();
        descricao.setStatus(Descricao.StatusTransacao.AUTORIZADO);
        descricao.setNsu("12345678901");
        descricao.setCodigoAutorizacao("12345678901");
        descricao.setValor(BigDecimal.valueOf(200.00));
        descricao.setEstabelecimento("Pet Shop");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraString = "17/11/2024 23:28:46";
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, formatter);
        descricao.setDataHora(dataHora);

        FormaPagamento pagamento = new FormaPagamento();
        pagamento.setParcelas(1);
        pagamento.setTipo(FormaPagamento.TipoFormaPagamento.AVISTA);

        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(pagamento);

        return transacao;
    }

    @Test
    void listarTransacoes_DeveRetornarListaDeTransacoes() throws Exception {
        Transacao transacao1 = criarTransacao();
        List<Transacao> transacoes = Arrays.asList(transacao1);

        when(pagamentoService.listarTransacoes()).thenReturn(transacoes);

        mockMvc.perform(get("/api/v1/pagamento/transacoes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cartao").value(transacao1.getCartao()))
                .andExpect(jsonPath("$[0].descricao.status").value(transacao1.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$[0].descricao.nsu").value(transacao1.getDescricao().getNsu()))
                .andExpect(jsonPath("$[0].descricao.codigoAutorizacao").value(transacao1.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$[0].descricao.valor").value(transacao1.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$[0].descricao.estabelecimento").value(transacao1.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$[0].descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$[0].formaPagamento.parcelas").value(transacao1.getFormaPagamento().getParcelas()))
                .andExpect(jsonPath("$[0].formaPagamento.tipo").value(transacao1.getFormaPagamento().getTipo().toString()));
    }

    @Test
    void consultarTransacao_DeveRetornarTransacaoPorId() throws Exception {
        Transacao transacao = criarTransacao();
        Long id = 1L;
        transacao.setId(id);

        when(pagamentoService.consultarTransacaoPorId(id)).thenReturn(transacao);

        mockMvc.perform(get("/api/v1/pagamento/transacao/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartao").value(transacao.getCartao()))
                .andExpect(jsonPath("$.descricao.status").value(transacao.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$.descricao.nsu").value(transacao.getDescricao().getNsu()))
                .andExpect(jsonPath("$.descricao.codigoAutorizacao").value(transacao.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$.descricao.valor").value(transacao.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$.descricao.estabelecimento").value(transacao.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$.descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$.formaPagamento.parcelas").value(transacao.getFormaPagamento().getParcelas()))
                .andExpect(jsonPath("$.formaPagamento.tipo").value(transacao.getFormaPagamento().getTipo().toString()));
    }

    @Test
    void consultarEstornos_DeveRetornarListaDeEstornos() throws Exception {
        Transacao estorno1 = criarTransacao();
        estorno1.getDescricao().setStatus(Descricao.StatusTransacao.CANCELADO);

        Transacao estorno2 = criarTransacao();
        estorno2.getDescricao().setStatus(Descricao.StatusTransacao.CANCELADO);

        List<Transacao> estornos = Arrays.asList(estorno1, estorno2);

        when(pagamentoService.consultarEstornos()).thenReturn(estornos);

        mockMvc.perform(get("/api/v1/pagamento/estornos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cartao").value(estorno1.getCartao()))
                .andExpect(jsonPath("$[0].descricao.status").value(estorno1.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$[0].descricao.nsu").value(estorno1.getDescricao().getNsu()))
                .andExpect(jsonPath("$[0].descricao.codigoAutorizacao").value(estorno1.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$[0].descricao.valor").value(estorno1.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$[0].descricao.estabelecimento").value(estorno1.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$[0].descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$[1].cartao").value(estorno2.getCartao()))
                .andExpect(jsonPath("$[1].descricao.status").value(estorno2.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$[1].descricao.nsu").value(estorno2.getDescricao().getNsu()))
                .andExpect(jsonPath("$[1].descricao.codigoAutorizacao").value(estorno2.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$[1].descricao.valor").value(estorno2.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$[1].descricao.estabelecimento").value(estorno2.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$[1].descricao.dataHora").value("17/11/2024 23:28:46"));
    }


    @Test
    void consultarEstornoById_DeveRetornarEstornoPorId() throws Exception {
        Transacao estorno = criarTransacao();
        Long id = 1L;
        estorno.getDescricao().setStatus(Descricao.StatusTransacao.CANCELADO);

        when(pagamentoService.consultarEstornoById(id)).thenReturn(estorno);

        mockMvc.perform(get("/api/v1/pagamento/estorno/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(estorno.getId()))
                .andExpect(jsonPath("$.cartao").value(estorno.getCartao()))
                .andExpect(jsonPath("$.descricao.status").value(estorno.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$.descricao.nsu").value(estorno.getDescricao().getNsu()))
                .andExpect(jsonPath("$.descricao.codigoAutorizacao").value(estorno.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$.descricao.valor").value(estorno.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$.descricao.estabelecimento").value(estorno.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$.descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$.formaPagamento.parcelas").value(estorno.getFormaPagamento().getParcelas()))
                .andExpect(jsonPath("$.formaPagamento.tipo").value(estorno.getFormaPagamento().getTipo().toString()));
    }

    @Test
    void realizarPagamento_DeveCriarNovaTransacao() throws Exception {
        Transacao transacao = criarTransacao();

        when(pagamentoService.realizarPagamento(any(TransacaoDto.class))).thenReturn(transacao);

        mockMvc.perform(post("/api/v1/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartao").value(transacao.getCartao()))
                .andExpect(jsonPath("$.descricao.status").value(transacao.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$.descricao.nsu").value(transacao.getDescricao().getNsu()))
                .andExpect(jsonPath("$.descricao.codigoAutorizacao").value(transacao.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$.descricao.valor").value(transacao.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$.descricao.estabelecimento").value(transacao.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$.descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$.formaPagamento.parcelas").value(transacao.getFormaPagamento().getParcelas()))
                .andExpect(jsonPath("$.formaPagamento.tipo").value(transacao.getFormaPagamento().getTipo().toString()));
    }

    @Test
    void estornarPagamento_DeveAlterarStatusParaCancelado() throws Exception {
        Long id = 1L;
        Transacao transacao = criarTransacao();
        transacao.setId(id);
        transacao.getDescricao().setStatus(Descricao.StatusTransacao.CANCELADO);

        when(pagamentoService.estornarPagamento(id)).thenReturn(transacao);

        mockMvc.perform(patch("/api/v1/pagamento/estornar/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transacao.getId()))
                .andExpect(jsonPath("$.cartao").value(transacao.getCartao()))
                .andExpect(jsonPath("$.descricao.status").value(transacao.getDescricao().getStatus().toString()))
                .andExpect(jsonPath("$.descricao.nsu").value(transacao.getDescricao().getNsu()))
                .andExpect(jsonPath("$.descricao.codigoAutorizacao").value(transacao.getDescricao().getCodigoAutorizacao()))
                .andExpect(jsonPath("$.descricao.valor").value(transacao.getDescricao().getValor().doubleValue()))
                .andExpect(jsonPath("$.descricao.estabelecimento").value(transacao.getDescricao().getEstabelecimento()))
                .andExpect(jsonPath("$.descricao.dataHora").value("17/11/2024 23:28:46"))
                .andExpect(jsonPath("$.formaPagamento.parcelas").value(transacao.getFormaPagamento().getParcelas()))
                .andExpect(jsonPath("$.formaPagamento.tipo").value(transacao.getFormaPagamento().getTipo().toString()));
    }

}
