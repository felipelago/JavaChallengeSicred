package com.project.challenge.service;

import com.project.challenge.dto.TransacaoDto;
import com.project.challenge.entity.Transacao;
import com.project.challenge.repository.TransacaoRepository;
import com.project.challenge.valueObject.Descricao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PagamentoService {


    private final TransacaoRepository transacaoRepository;

    public List<Transacao> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        if (transacoes.isEmpty()) {
            throw new RuntimeException("Nenhuma transação encontrada");
        }
        return transacoes;
    }

    public Transacao consultarTransacaoPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public Transacao consultarEstornoById(Long id) {
        return transacaoRepository.findEstornoById(id)
                .orElseThrow(() -> new RuntimeException("Estorno com ID " + id + " não encontrado"));
    }

    public List<Transacao> consultarEstornos() {
        List<Transacao> estornos = transacaoRepository.findAllEstorno();
        if (estornos.isEmpty()) {
            throw new RuntimeException("Nenhum estorno encontrado");
        }
        return estornos;
    }

    //TODO - implementar uma validação para checar se o cliente tem limite no cartão de acordo com o valor da compra para utilizar o NEGADO
    @Transactional
    public Transacao realizarPagamento(TransacaoDto transacaoDto) {
        Transacao transacao = new Transacao();
        transacao.setCartao(transacaoDto.getCartao());

        // Criando e setando a descrição
        Descricao descricao = new Descricao();
        descricao.setValor(transacaoDto.getDescricao().getValor());
        descricao.setDataHora(LocalDateTime.now());
        descricao.setEstabelecimento(transacaoDto.getDescricao().getEstabelecimento());
        descricao.setNsu(gerarNsu());
        descricao.setCodigoAutorizacao(gerarCodigoAutorizacao());
        descricao.setStatus(Descricao.StatusTransacao.AUTORIZADO);

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
}
