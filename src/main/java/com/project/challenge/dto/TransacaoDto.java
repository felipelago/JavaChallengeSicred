package com.project.challenge.dto;

import com.project.challenge.entity.Transacao;
import com.project.challenge.valueObject.Descricao;
import com.project.challenge.valueObject.FormaPagamento;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TransacaoDto {
    private String cartao;
    private Descricao descricao;
    private FormaPagamento formaPagamento;
}
