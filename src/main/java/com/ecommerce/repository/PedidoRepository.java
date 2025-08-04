package com.ecommerce.repository;

import com.ecommerce.model.Pedido;

public class PedidoRepository extends AbstractCsvRepository<Pedido> {
    public PedidoRepository() {
        super("pedidos.csv", Pedido.csvHeader(), Pedido::fromCsv);
    }
}
