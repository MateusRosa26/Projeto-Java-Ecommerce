package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.util.Date;

public class Cliente implements CsvSerializable {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cidade;
    private Date dataNascimento;
    private int role;
    private Date createdAt;
    private Date updatedAt;

    // Métodos
    public void realizarPedido() {
        // Implementação do método para realizar pedido
    }

    public void avaliarEntrega() {
        // Implementação do método para avaliar entrega
    }

    // Getters e Setters

    public Cliente(long id, String nome, String email) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{String.valueOf(id), nome, email};
    }

    public static Cliente fromCsv(String[] row) {
        long id = Long.parseLong(row[0]);
        return new Cliente(id, row[1], row[2]);
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "email"};
    }
}
