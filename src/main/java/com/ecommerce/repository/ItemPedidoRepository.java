package com.ecommerce.repository;

import com.ecommerce.model.ItemPedido;

public class ItemPedidoRepository extends AbstractCsvRepository<ItemPedido> {

    public ItemPedidoRepository() {
        super("data/itens_pedido.csv", ItemPedido.csvHeader(), ItemPedido::fromCsv);
    }
}
