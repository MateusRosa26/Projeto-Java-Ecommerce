package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import java.util.Date;

public class SistemaPagamento implements CsvSerializable {
    private Long id;
    private String nome;
    private String apiKey;
    private Date createdAt;
    private Date updatedAt;

    public SistemaPagamento() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public SistemaPagamento(Long id, String nome, String apiKey) {
        this();
        this.id = id;
        this.nome = nome;
        this.apiKey = apiKey;
    }

    public SistemaPagamento(Long id, String nome, String apiKey, Date createdAt, Date updatedAt) {
        this.id = id;
        this.nome = nome;
        this.apiKey = apiKey;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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

    // CSV Serialization
    @Override
    public String[] toCsvRow() {
        return new String[]{
                String.valueOf(id != null ? id : ""),
                nome != null ? nome : "",
                apiKey != null ? apiKey : "",
                createdAt != null ? String.valueOf(createdAt.getTime()) : "",
                updatedAt != null ? String.valueOf(updatedAt.getTime()) : ""
        };
    }

    public static SistemaPagamento fromCsv(String[] fields) {
        if (fields.length >= 5) {
            SistemaPagamento sistema = new SistemaPagamento();
            sistema.id = fields[0].isEmpty() ? null : Long.parseLong(fields[0]);
            sistema.nome = fields[1].isEmpty() ? null : fields[1];
            sistema.apiKey = fields[2].isEmpty() ? null : fields[2];
            sistema.createdAt = fields[3].isEmpty() ? null : new Date(Long.parseLong(fields[3]));
            sistema.updatedAt = fields[4].isEmpty() ? null : new Date(Long.parseLong(fields[4]));
            return sistema;
        }
        return null;
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "apiKey", "createdAt", "updatedAt"};
    }
}
