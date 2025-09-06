package com.ecommerce.repository;

import com.ecommerce.model.RelatorioVenda;

public class RelatorioVendaRepository extends AbstractCsvRepository<RelatorioVenda> {
    
    public RelatorioVendaRepository() {
        super("data/relatorio_venda.csv", RelatorioVenda.csvHeader(), RelatorioVenda::fromCsv);
    }
}
