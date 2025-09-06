package com.ecommerce.repository;

import com.ecommerce.model.EnderecoEntrega;

public class EnderecoEntregaRepository extends AbstractCsvRepository<EnderecoEntrega> {

    public EnderecoEntregaRepository() {
        super("data/enderecos_entrega.csv", EnderecoEntrega.csvHeader(), EnderecoEntrega::fromCsv);
    }
}
