package com.ecommerce.repository;

import com.ecommerce.model.Notificacao;

public class NotificacaoRepository extends AbstractCsvRepository<Notificacao> {

    public NotificacaoRepository() {
        super("data/notificacoes.csv", Notificacao.csvHeader(), Notificacao::fromCsv);
    }
}
