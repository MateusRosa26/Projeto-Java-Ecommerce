package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

public class ItemPedido implements CsvSerializable {
    private Long id;
    private Long produtoId;
    private Long pedidoId;
    private int quantidade;
    private double precoUnitario;

    // Construtores
    public ItemPedido() {}

    public ItemPedido(Long produtoId, int quantidade, double precoUnitario) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        if (precoUnitario < 0) throw new IllegalArgumentException("Preço unitário deve ser maior ou igual a zero");
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemPedido(Long id, Long produtoId, Long pedidoId, int quantidade, double precoUnitario) {
        this(produtoId, quantidade, precoUnitario);
        this.id = id;
        this.pedidoId = pedidoId;
    }

    // Métodos de negócio
    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    public void atualizarQuantidade(int novaQuantidade) {
        if (novaQuantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.quantidade = novaQuantidade;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        if (precoUnitario < 0) throw new IllegalArgumentException("Preço unitário deve ser maior ou igual a zero");
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{
            String.valueOf(id != null ? id : ""), 
            String.valueOf(produtoId), 
            String.valueOf(pedidoId != null ? pedidoId : ""), 
            String.valueOf(quantidade), 
            String.valueOf(precoUnitario)
        };
    }

    public static ItemPedido fromCsv(String[] row) {
        Long id = row[0].isEmpty() ? null : Long.parseLong(row[0]);
        Long produtoId = Long.parseLong(row[1]);
        Long pedidoId = row[2].isEmpty() ? null : Long.parseLong(row[2]);
        int quantidade = Integer.parseInt(row[3]);
        double precoUnitario = Double.parseDouble(row[4]);
        return new ItemPedido(id, produtoId, pedidoId, quantidade, precoUnitario);
    }

    public static String[] csvHeader() {
        return new String[]{"id", "produtoId", "pedidoId", "quantidade", "precoUnitario"};
    }
}
