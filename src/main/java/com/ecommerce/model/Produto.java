package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.csv.EntityWithId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Produto implements CsvSerializable, EntityWithId {
    private long id;
    private String nome;
    private float preco;
    private String descricao;
    private String imagemUrl;
    private String codigoBarras;
    private boolean disponivel;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Produto() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Produto(long id, String nome, float preco, String descricao, String imagemUrl, boolean disponivel) {
        this();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.disponivel = disponivel;
    }

    public Produto(long id, String nome, float preco, String descricao, String imagemUrl, String codigoBarras, boolean disponivel) {
        this();
        
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.codigoBarras = codigoBarras;
        this.disponivel = disponivel;
    }

    // Métodos de negócio
    public void atualizarDisponibilidade(boolean disponibilidade) {
        this.disponivel = disponibilidade;
        this.updatedAt = new Date();
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.updatedAt = new Date();
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
        this.updatedAt = new Date();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        this.updatedAt = new Date();
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
        this.updatedAt = new Date();
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
        this.updatedAt = new Date();
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        this.updatedAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String[] toCsvRow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new String[]{
                String.valueOf(id),
                nome,
                String.valueOf(preco),
                descricao != null ? descricao : "",
                imagemUrl != null ? imagemUrl : "",
                codigoBarras != null ? codigoBarras : "",
                String.valueOf(disponivel),
                sdf.format(createdAt),
                sdf.format(updatedAt)
        };
    }

    public static Produto fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long id = Long.parseLong(row[0]);
            String nome = row[1];
            float preco = Float.parseFloat(row[2]);
            String descricao = row[3];
            String imagemUrl = row[4];
            String codigoBarras = row[5];
            boolean disponivel = Boolean.parseBoolean(row[6]);
            Date createdAt = sdf.parse(row[7]);
            Date updatedAt = sdf.parse(row[8]);
            
            Produto produto = new Produto(id, nome, preco, descricao, imagemUrl, codigoBarras, disponivel);
            produto.setCreatedAt(createdAt);
            produto.setUpdatedAt(updatedAt);
            return produto;
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "preco", "descricao", "imagemUrl", "codigoBarras", "disponivel", "createdAt", "updatedAt"};
    }
}
