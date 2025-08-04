package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;


public class ItemPedido implements CsvSerializable {
    private int produtoId;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido(int produtoId, int quantidade, double precoUnitario) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{String.valueOf(produtoId), String.valueOf(quantidade), String.valueOf(precoUnitario)};
    }

    public static ItemPedido fromCsv(String[] row) {
        int produtoId = Integer.parseInt(row[0]);
        int quantidade = Integer.parseInt(row[1]);
        double preco = Double.parseDouble(row[2]);
        return new ItemPedido(produtoId, quantidade, preco);
    }

    public static String[] csvHeader() {
        return new String[]{"produtoId", "quantidade", "precoUnitario"};
    }
}
