package com.ecommerce.model;


import com.ecommerce.csv.CsvSerializable;

import java.util.Date;

public class Produto implements CsvSerializable {
    private long id;
    private String nome;
    private float preco;
    private String descricao;
    private String imagemUrl;
    private boolean disponivel;
    private Date createdAt;
    private Date updatedAt;

    // Métodos
    public void atualizarDisponibilidade(boolean disponibilidade) {
        // Implementação do método para atualizar disponibilidade
    }

    // Getters e Setters

    public Produto() {
    }

    public Produto(long id, String nome, float preco, String descricao, String imagemUrl, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.disponivel = disponivel;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public static Produto fromCsv(String[] row) {
        long id = Long.parseLong(row[0]);
        String nome = row[1];
        float preco = Float.parseFloat(row[2]);
        String descricao = row[3];
        String imagemUrl = row[4];
        boolean disponivel = Boolean.parseBoolean(row[5]);
        return new Produto(id, nome, preco, descricao, imagemUrl, disponivel);
    }


    @Override
    public String[] toCsvRow() {
        return new String[]{
                String.valueOf(id),
                nome,
                String.valueOf(preco),
                descricao,
                imagemUrl,
                String.valueOf(disponivel)
        };
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "preco", "descricao", "imagemUrl", "disponivel"};
    }
}
