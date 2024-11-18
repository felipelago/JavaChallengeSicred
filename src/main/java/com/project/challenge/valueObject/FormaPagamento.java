package com.project.challenge.valueObject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Embeddable
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    @Column(name = "FPG_tipo")
    @NotNull(message = "O tipo de pagamento é obrigatório")
    private TipoFormaPagamento tipo;

    @Column(name = "FPG_parcelas")
    @NotNull(message = "O campo de parcelas é obrigatório")
    private Integer parcelas;

    public enum TipoFormaPagamento {
        AVISTA,
        PARCELADO_LOJA,
        PARCELADO_EMISSOR;
    }
}