package com.project.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRA_id", unique = true)
    private Long id;

    @Column(name = "TRA_cartao")
    private String cartao;

    @Embedded
    private Descricao descricao;

    @OneToOne
    @JoinColumn(name = "FPG_id")
    private FormaPagamento formaPagamento;

    @Getter
    @Setter
    @Embeddable
    public static class Descricao {
        @Column(name = "DES_valor")
        private BigDecimal valor;
        @Column(name = "DES_data_hora")
        private LocalDateTime dataHora;
        @Column(name = "DES_estabelecimento")
        private String estabelecimento;
        @Column(name = "DES_nsu")
        private String nsu;
        @Column(name = "DES_codigo_autorizador")
        private String codigoAutorizacao;
        @Enumerated(EnumType.STRING)
        @Column(name = "DES_status")
        private StatusTransacao status;
    }

    public enum StatusTransacao {
        AUTORIZADO,
        NEGADO,
        CANCELADO;
    }


}
