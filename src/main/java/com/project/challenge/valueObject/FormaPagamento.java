package com.project.challenge.valueObject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    @Column(name = "FPG_tipo")
    private TipoFormaPagamento tipo;

    @Column(name = "FPG_parcelas")
    private Integer parcelas;

    public enum TipoFormaPagamento {
        AVISTA,
        PARCELADO_LOJA,
        PARCELADO_EMISSOR;
    }
}