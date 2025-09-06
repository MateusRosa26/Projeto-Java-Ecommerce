package com.ecommerce.repository;

import com.ecommerce.model.SistemaPagamento;

public class SistemaPagamentoRepository extends AbstractCsvRepository<SistemaPagamento> {
    
    public SistemaPagamentoRepository() {
        super("data/sistema_pagamento.csv", SistemaPagamento.csvHeader(), SistemaPagamento::fromCsv);
    }
}
