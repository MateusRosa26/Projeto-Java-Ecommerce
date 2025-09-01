package com.ecommerce.model;


import com.ecommerce.csv.CsvSerializable;


public class Produto implements CsvSerializable {
    private long id;
    private String nome;
    private float preco;
    private String codigoBarras;
    private String descricao;
    private String imagemUrl;
    private boolean disponivel;
    // Métodos
    public void atualizarDisponibilidade(boolean disponibilidade) {
        this.disponivel = disponibilidade;
    }

    // Getters e Setters

    public Produto() {
    }

    public Produto(long id, String nome, float preco, String codigoBarras, String descricao, String imagemUrl, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.codigoBarras = codigoBarras;
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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
    
    public String getImagem() {
        return imagemUrl;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public static Produto fromCsv(String[] row) {
        long id = Long.parseLong(row[0]);
        String nome = row[1];
        float preco = Float.parseFloat(row[2]);
        String codigoBarras = row.length > 3 ? row[3] : "";
        String descricao = row.length > 4 ? row[4] : "";
        String imagemUrl = row.length > 5 ? row[5] : "";
        boolean disponivel = row.length <= 6 || Boolean.parseBoolean(row[6]);
        return new Produto(id, nome, preco, codigoBarras, descricao, imagemUrl, disponivel);
    }


    @Override
    public String[] toCsvRow() {
        return new String[]{
                String.valueOf(id),
                nome,
                String.valueOf(preco),
                codigoBarras,
                descricao,
                imagemUrl,
                String.valueOf(disponivel)
        };
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "preco", "codigoBarras", "descricao", "imagemUrl", "disponivel"};
    }
}
