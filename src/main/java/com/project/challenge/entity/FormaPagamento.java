package com.project.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FormaPagamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPG_id", unique = true)
    private Long id;

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
