package com.project.challenge.dto;

import com.project.challenge.valueObject.Descricao;
import com.project.challenge.valueObject.FormaPagamento;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class TransacaoDto {
    @NotNull(message = "O número do cartão é obrigatório")
    private String cartao;

    @Valid
    @NotNull(message = "A descrição da transação é obrigatória")
    private Descricao descricao;

    @Valid
    @NotNull(message = "A forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;
}
