package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Pedido implements CsvSerializable {
    private Long id;
    private String numero;
    private Date dataPedido;
    private int status;
    private float total;
    private String comment;
    private int rating;
    private Date createdAt;
    private Date updatedAt;

    // Métodos
    public void atualizarStatus(int novoStatus) {
        // Implementação do método para atualizar status
    }

    public void cancelarPedido() {
        // Implementação do método para cancelar pedido
    }

    // Getters e Setters

    public int getId() {
        return id.intValue();
    }

    public int getClienteId() {
        // Assuming clienteId is part of the number or derived
        return numero.hashCode() % 1000; // Placeholder, needs actual logic
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String[] toCsvRow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new String[]{
            String.valueOf(id), 
            numero, 
            sdf.format(dataPedido), 
            String.valueOf(status), 
            String.valueOf(total), 
            comment, 
            String.valueOf(rating), 
            sdf.format(createdAt), 
            sdf.format(updatedAt)
        };
    }

    public static Pedido fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Long id = Long.parseLong(row[0]);
            String numero = row[1];
            Date dataPedido = sdf.parse(row[2]);
            int status = Integer.parseInt(row[3]);
            float total = Float.parseFloat(row[4]);
            String comment = row[5];
            int rating = Integer.parseInt(row[6]);
            Date createdAt = sdf.parse(row[7]);
            Date updatedAt = sdf.parse(row[8]);
            return new Pedido(id, numero, dataPedido, status, total, comment, rating, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "numero", "dataPedido", "status", "total", "comment", "rating", "createdAt", "updatedAt"};
    }

    public Pedido(Long id, String numero, Date dataPedido, int status, float total, String comment, int rating, Date createdAt, Date updatedAt) {
        this.id = id;
        this.numero = numero;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
