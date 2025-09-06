package com.ecommerce.repository;

import com.ecommerce.model.Avaliacao;

public class AvaliacaoRepository extends AbstractCsvRepository<Avaliacao> {

    public AvaliacaoRepository() {
        super("data/avaliacoes.csv", Avaliacao.csvHeader(), Avaliacao::fromCsv);
    }
}
