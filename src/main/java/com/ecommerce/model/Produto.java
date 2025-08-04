package com.ecommerce.model;


import com.ecommerce.csv.CsvSerializable;

public class Produto implements CsvSerializable {
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private String imagemUrl;
    private boolean disponivel;

    public Produto() {
    }

    public Produto(int id, String nome, double preco, String descricao, String imagemUrl, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
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
        int id = Integer.parseInt(row[0]);
        String nome = row[1];
        double preco = Double.parseDouble(row[2]);
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
