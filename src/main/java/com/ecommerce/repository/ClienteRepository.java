package com.ecommerce.repository;

import com.ecommerce.model.Cliente;


public class ClienteRepository extends AbstractCsvRepository<Cliente> {

    public ClienteRepository() {
        super("clientes.csv", Cliente.csvHeader(), Cliente::fromCsv);
    }
}
