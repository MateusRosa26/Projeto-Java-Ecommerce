package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.csv.EntityWithId;
import com.ecommerce.enums.OrderStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pedido implements CsvSerializable, EntityWithId {
    private Long id;
    private String numero;
    private Date dataPedido;
    private OrderStatus status;
    private float total;
    private String comment;
    private int rating;
    private Long clienteId;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Pedido() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.dataPedido = new Date();
        this.status = OrderStatus.PREPARANDO;
    }

    public Pedido(Long id, String numero, Date dataPedido, OrderStatus status, float total, String comment, int rating, Long clienteId) {
        this();
        this.id = id;
        this.numero = numero;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.comment = comment;
        this.rating = rating;
        this.clienteId = clienteId;
    }

    public Pedido(Long id, String numero, Date dataPedido, OrderStatus status, float total, String comment, int rating, Long clienteId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.numero = numero;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.comment = comment;
        this.rating = rating;
        this.clienteId = clienteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public void atualizarStatus(OrderStatus novoStatus) {
        this.status = novoStatus;
        this.updatedAt = new Date();
    }

    public void cancelarPedido() {
        if (this.status == OrderStatus.ENTREGUE) {
            throw new IllegalStateException("Não é possível cancelar um pedido já entregue");
        }
        this.status = OrderStatus.CANCELADO;
        this.updatedAt = new Date();
    }

    public boolean podeSerCancelado() {
        return this.status != OrderStatus.ENTREGUE && this.status != OrderStatus.CANCELADO;
    }

    // Getters e Setters
    public long getId() {
        return id != null ? id : 0L;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
        this.updatedAt = new Date();
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
        this.updatedAt = new Date();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.updatedAt = new Date();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
        this.updatedAt = new Date();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        this.updatedAt = new Date();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        this.updatedAt = new Date();
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
            numero, 
            sdf.format(dataPedido), 
            String.valueOf(status.ordinal()), 
            String.valueOf(total), 
            comment != null ? comment : "", 
            String.valueOf(rating), 
            String.valueOf(clienteId),
            sdf.format(createdAt), 
            sdf.format(updatedAt)
        };
    }

    public static Pedido fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Tratar valores nulos
            Long id = (row[0] != null && !row[0].equals("null") && !row[0].isEmpty()) ? Long.parseLong(row[0]) : null;
            String numero = (row[1] != null && !row[1].equals("null")) ? row[1] : "";
            Date dataPedido = (row[2] != null && !row[2].equals("null") && !row[2].isEmpty()) ? sdf.parse(row[2]) : new Date();
            OrderStatus status = (row[3] != null && !row[3].equals("null") && !row[3].isEmpty()) ? OrderStatus.values()[Integer.parseInt(row[3])] : OrderStatus.PREPARANDO;
            float total = (row[4] != null && !row[4].equals("null") && !row[4].isEmpty()) ? Float.parseFloat(row[4]) : 0.0f;
            String comment = (row[5] != null && !row[5].equals("null")) ? row[5] : "";
            int rating = (row[6] != null && !row[6].equals("null") && !row[6].isEmpty()) ? Integer.parseInt(row[6]) : 0;
            Long clienteId = (row[7] != null && !row[7].equals("null") && !row[7].isEmpty()) ? Long.parseLong(row[7]) : null;
            Date createdAt = (row[8] != null && !row[8].equals("null") && !row[8].isEmpty()) ? sdf.parse(row[8]) : new Date();
            Date updatedAt = (row[9] != null && !row[9].equals("null") && !row[9].isEmpty()) ? sdf.parse(row[9]) : new Date();
            
            return new Pedido(id, numero, dataPedido, status, total, comment, rating, clienteId, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "numero", "dataPedido", "status", "total", "comment", "rating", "clienteId", "createdAt", "updatedAt"};
    }
}
