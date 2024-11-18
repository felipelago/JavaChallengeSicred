package com.project.challenge.valueObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Descricao {

    @Column(name = "DES_valor")
    @NotNull(message = "O valor da transação é obrigatório")
    private BigDecimal valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "DES_data_hora")
    private LocalDateTime dataHora;

    @Column(name = "DES_estabelecimento")
    @NotNull(message = "O estabelecimento é obrigatório")
    private String estabelecimento;

    @Column(name = "DES_nsu")
    private String nsu;

    @Column(name = "DES_codigo_autorizador")
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "DES_status")
    private StatusTransacao status;

    public enum StatusTransacao {
        AUTORIZADO,
        NEGADO,
        CANCELADO;
    }
}
