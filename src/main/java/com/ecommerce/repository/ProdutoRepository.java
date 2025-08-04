package com.ecommerce.repository;

import com.ecommerce.model.Produto;


public class ProdutoRepository extends AbstractCsvRepository<Produto> {

    public ProdutoRepository() {
        super("produtos.csv", Produto.csvHeader(), Produto::fromCsv);
    }
}
