package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;


public class Cliente implements CsvSerializable {
    private int id;
    private String nome;
    private String email;

    public Cliente(int id, String nome, String email) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
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
        int id = Integer.parseInt(row[0]);
        return new Cliente(id, row[1], row[2]);
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "email"};
    }
}
