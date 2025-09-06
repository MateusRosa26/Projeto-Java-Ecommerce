package com.ecommerce.repository;

import com.ecommerce.model.Pagamento;

public class PagamentoRepository extends AbstractCsvRepository<Pagamento> {

    public PagamentoRepository() {
        super("data/pagamentos.csv", Pagamento.csvHeader(), Pagamento::fromCsv);
    }
}
