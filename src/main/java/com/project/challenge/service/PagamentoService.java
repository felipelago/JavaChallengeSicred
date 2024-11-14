package com.project.challenge.service;

import com.project.challenge.dto.TransacaoDto;
import com.project.challenge.entity.Cliente;
import com.project.challenge.entity.Transacao;
import com.project.challenge.exception.ClienteNaoEncontradoException;
import com.project.challenge.exception.TransacaoNaoEncontradaException;
import com.project.challenge.repository.ClienteRepository;
import com.project.challenge.repository.TransacaoRepository;
import com.project.challenge.valueObject.Descricao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PagamentoService {


    private final TransacaoRepository transacaoRepository;
    private final ClienteRepository clienteRepository;

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }

    public Transacao consultarTransacaoPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação com ID " + id + " não encontrada"));
    }

    public Transacao consultarEstornoById(Long id) {
        return transacaoRepository.findEstornoById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Estorno com ID " + id + " não encontrado"));
    }

    public List<Transacao> consultarEstornos() {
        return transacaoRepository.findAllEstorno();
    }

    //TODO - Criar as validações ou uma nova exception para esse método
    @Transactional
    public Transacao realizarPagamento(TransacaoDto transacaoDto) {
        //Step 1 - Verificar o cliente pelo numero do cartão e verificar se tem limite
        Cliente cliente = clienteRepository.findByNumeroCartao(transacaoDto.getCartao())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com número de cartão " + transacaoDto.getCartao() + " não encontrado"));

        BigDecimal valorTransacao = transacaoDto.getDescricao().getValor();
        Descricao.StatusTransacao status = valorTransacao.compareTo(cliente.getLimiteCredito()) > 0 ?
                Descricao.StatusTransacao.NEGADO : Descricao.StatusTransacao.AUTORIZADO;

        //Step 2 - Salva a transação sendo negado ou autorizado
        Transacao transacao = new Transacao();
        transacao.setCartao(transacaoDto.getCartao());

        // Criando e setando a descrição
        Descricao descricao = new Descricao();
        descricao.setValor(transacaoDto.getDescricao().getValor());
        descricao.setDataHora(LocalDateTime.now());
        descricao.setEstabelecimento(transacaoDto.getDescricao().getEstabelecimento());
        descricao.setNsu(gerarNsu());
        descricao.setCodigoAutorizacao(gerarCodigoAutorizacao());
        descricao.setStatus(status);

        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(transacaoDto.getFormaPagamento());

        return transacaoRepository.save(transacao);
    }

    // Método para gerar NSU aleatório
    private String gerarNsu() {
        Random random = new Random();
        return String.format("%010d", random.nextInt(1000000000));
    }

    // Método para gerar Código de Autorização aleatório (9 dígitos)
    private String gerarCodigoAutorizacao() {
        Random random = new Random();
        return String.format("%09d", random.nextInt(1000000000));
    }

    public Transacao estornarPagamento(Long id) {
        Optional<Transacao> transacaoEstorno = transacaoRepository.findById(id);
        if (transacaoEstorno.isPresent()) {
            Transacao transacao = transacaoEstorno.get();
            transacao.getDescricao().setStatus(Descricao.StatusTransacao.CANCELADO);
            return transacaoRepository.save(transacao);
        } else {
            throw new TransacaoNaoEncontradaException("Transação não encontrada");
        }
    }
}
