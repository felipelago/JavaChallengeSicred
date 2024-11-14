package com.project.challenge.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_id", unique = true)
    private Long id;

    @Column(name = "CLI_nome", nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @Column(name = "CLI_cpf", unique = true, length = 11, nullable = false)
    @NotNull
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    private String cpf;

    @Column(name = "CLI_limite_credito")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal limiteCredito;

    @Column(name = "CLI_numero_cartao")
    @Size(min = 16, max = 16, message = "O número do cartão deve conter 16 dígitos")
    private String numeroCartao; //String para evitar problema caso comece com 0
}
