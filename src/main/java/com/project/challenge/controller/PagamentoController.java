package com.project.challenge.controller;

import com.project.challenge.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;
}
