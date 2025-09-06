package com.ecommerce.repository;

import com.ecommerce.model.Carrinho;

public class CarrinhoRepository extends AbstractCsvRepository<Carrinho> {

    public CarrinhoRepository() {
        super("data/carrinhos.csv", Carrinho.csvHeader(), Carrinho::fromCsv);
    }
}
