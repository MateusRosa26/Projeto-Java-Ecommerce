package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;



public class Pedido implements CsvSerializable {
    private int id;
    private int clienteId;
    private String status;

    public Pedido(int id, int clienteId, String status) {
        if (status == null || status.isBlank()) throw new IllegalArgumentException("Status é obrigatório");
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{String.valueOf(id), String.valueOf(clienteId), status};
    }

    public static Pedido fromCsv(String[] row) {
        int id = Integer.parseInt(row[0]);
        int clienteId = Integer.parseInt(row[1]);
        String status = row[2];
        return new Pedido(id, clienteId, status);
    }

    public static String[] csvHeader() {
        return new String[]{"id", "clienteId", "status"};
    }
}
