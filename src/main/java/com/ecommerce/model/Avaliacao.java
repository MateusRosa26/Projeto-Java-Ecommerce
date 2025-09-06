package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Avaliacao implements CsvSerializable {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private Long clienteId;
    private Long entregadorId;
    private int notaProduto;
    private String comentarioProduto;
    private int notaEntrega;
    private String comentarioEntrega;
    private Date dataAvaliacao;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Avaliacao() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.dataAvaliacao = new Date();
    }

    public Avaliacao(Long pedidoId, Long produtoId, Long clienteId, int notaProduto, String comentarioProduto) {
        this();
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.clienteId = clienteId;
        this.notaProduto = notaProduto;
        this.comentarioProduto = comentarioProduto;
    }

    public Avaliacao(Long pedidoId, Long produtoId, Long clienteId, Long entregadorId, int notaProduto, String comentarioProduto, int notaEntrega, String comentarioEntrega) {
        this(pedidoId, produtoId, clienteId, notaProduto, comentarioProduto);
        this.entregadorId = entregadorId;
        this.notaEntrega = notaEntrega;
        this.comentarioEntrega = comentarioEntrega;
    }

    public Avaliacao(Long id, Long pedidoId, Long produtoId, Long clienteId, Long entregadorId, int notaProduto, String comentarioProduto, int notaEntrega, String comentarioEntrega, Date dataAvaliacao, Date createdAt, Date updatedAt) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.clienteId = clienteId;
        this.entregadorId = entregadorId;
        this.notaProduto = notaProduto;
        this.comentarioProduto = comentarioProduto;
        this.notaEntrega = notaEntrega;
        this.comentarioEntrega = comentarioEntrega;
        this.dataAvaliacao = dataAvaliacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public boolean isNotaValida(int nota) {
        return nota >= 1 && nota <= 5;
    }

    public void validarNotas() {
        if (!isNotaValida(notaProduto)) {
            throw new IllegalArgumentException("Nota do produto deve estar entre 1 e 5");
        }
        if (entregadorId != null && !isNotaValida(notaEntrega)) {
            throw new IllegalArgumentException("Nota da entrega deve estar entre 1 e 5");
        }
    }

    public double getNotaMedia() {
        if (entregadorId != null) {
            return (notaProduto + notaEntrega) / 2.0;
        }
        return notaProduto;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
        this.updatedAt = new Date();
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
        this.updatedAt = new Date();
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        this.updatedAt = new Date();
    }

    public Long getEntregadorId() {
        return entregadorId;
    }

    public void setEntregadorId(Long entregadorId) {
        this.entregadorId = entregadorId;
        this.updatedAt = new Date();
    }

    public int getNotaProduto() {
        return notaProduto;
    }

    public void setNotaProduto(int notaProduto) {
        if (!isNotaValida(notaProduto)) {
            throw new IllegalArgumentException("Nota do produto deve estar entre 1 e 5");
        }
        this.notaProduto = notaProduto;
        this.updatedAt = new Date();
    }

    public String getComentarioProduto() {
        return comentarioProduto;
    }

    public void setComentarioProduto(String comentarioProduto) {
        this.comentarioProduto = comentarioProduto;
        this.updatedAt = new Date();
    }

    public int getNotaEntrega() {
        return notaEntrega;
    }

    public void setNotaEntrega(int notaEntrega) {
        if (!isNotaValida(notaEntrega)) {
            throw new IllegalArgumentException("Nota da entrega deve estar entre 1 e 5");
        }
        this.notaEntrega = notaEntrega;
        this.updatedAt = new Date();
    }

    public String getComentarioEntrega() {
        return comentarioEntrega;
    }

    public void setComentarioEntrega(String comentarioEntrega) {
        this.comentarioEntrega = comentarioEntrega;
        this.updatedAt = new Date();
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new String[]{
            String.valueOf(id),
            String.valueOf(pedidoId),
            String.valueOf(produtoId),
            String.valueOf(clienteId),
            String.valueOf(entregadorId != null ? entregadorId : ""),
            String.valueOf(notaProduto),
            comentarioProduto != null ? comentarioProduto : "",
            String.valueOf(notaEntrega),
            comentarioEntrega != null ? comentarioEntrega : "",
            sdf.format(dataAvaliacao),
            sdf.format(createdAt),
            sdf.format(updatedAt)
        };
    }

    public static Avaliacao fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // Tratar valores nulos
            Long id = (row[0] != null && !row[0].equals("null") && !row[0].isEmpty()) ? Long.parseLong(row[0]) : null;
            Long pedidoId = (row[1] != null && !row[1].equals("null") && !row[1].isEmpty()) ? Long.parseLong(row[1]) : null;
            Long produtoId = (row[2] != null && !row[2].equals("null") && !row[2].isEmpty()) ? Long.parseLong(row[2]) : null;
            Long clienteId = (row[3] != null && !row[3].equals("null") && !row[3].isEmpty()) ? Long.parseLong(row[3]) : null;
            Long entregadorId = (row[4] != null && !row[4].equals("null") && !row[4].isEmpty()) ? Long.parseLong(row[4]) : null;
            int notaProduto = (row[5] != null && !row[5].equals("null") && !row[5].isEmpty()) ? Integer.parseInt(row[5]) : 0;
            String comentarioProduto = (row[6] != null && !row[6].equals("null")) ? row[6] : "";
            int notaEntrega = (row[7] != null && !row[7].equals("null") && !row[7].isEmpty()) ? Integer.parseInt(row[7]) : 0;
            String comentarioEntrega = (row[8] != null && !row[8].equals("null")) ? row[8] : "";
            Date dataAvaliacao = (row[9] != null && !row[9].equals("null") && !row[9].isEmpty()) ? sdf.parse(row[9]) : new Date();
            Date createdAt = (row[10] != null && !row[10].equals("null") && !row[10].isEmpty()) ? sdf.parse(row[10]) : new Date();
            Date updatedAt = (row[11] != null && !row[11].equals("null") && !row[11].isEmpty()) ? sdf.parse(row[11]) : new Date();
            return new Avaliacao(id, pedidoId, produtoId, clienteId, entregadorId, notaProduto, comentarioProduto, notaEntrega, comentarioEntrega, dataAvaliacao, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "pedidoId", "produtoId", "clienteId", "entregadorId", "notaProduto", "comentarioProduto", "notaEntrega", "comentarioEntrega", "dataAvaliacao", "createdAt", "updatedAt"};
    }
}
