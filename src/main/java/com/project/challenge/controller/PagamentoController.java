package com.project.challenge.controller;

import com.project.challenge.dto.TransacaoDto;
import com.project.challenge.entity.Transacao;
import com.project.challenge.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping("/transacoes")
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        List<Transacao> transacoes = pagamentoService.listarTransacoes();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Transacao> consultarTransacao(@PathVariable Long id) {
        Transacao estorno = pagamentoService.consultarTransacaoPorId(id);
        return ResponseEntity.ok(estorno);
    }

    @GetMapping("/estornos")
    public ResponseEntity<List<Transacao>> consultarEstornos() {
        List<Transacao> estornos = pagamentoService.consultarEstornos();
        return ResponseEntity.ok(estornos);
    }

    @GetMapping("/estorno/{id}")
    public ResponseEntity<Transacao> consultarEstornoById(@PathVariable Long id) {
        Transacao transacao = pagamentoService.consultarEstornoById(id);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    public ResponseEntity<Transacao> realizarPagamento(@RequestBody TransacaoDto transacaoDto) {
        Transacao transacao = pagamentoService.realizarPagamento(transacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @PostMapping("/estornar/{id}")
    public ResponseEntity<Transacao> estornarPagamento(@PathVariable Long id) {
        Transacao transacao = pagamentoService.estornarPagamento(id);
        return ResponseEntity.ok(transacao);
    }

}
