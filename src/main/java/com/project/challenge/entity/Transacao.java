package com.project.challenge.entity;

import com.project.challenge.valueObject.Descricao;
import com.project.challenge.valueObject.FormaPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRA_id", unique = true)
    private Long id;

    @Column(name = "TRA_cartao", nullable = false)
    @NotNull
    private String cartao;

    @Embedded
    @NotNull
    private Descricao descricao;

    @Embedded
    @NotNull
    private FormaPagamento formaPagamento;
}
