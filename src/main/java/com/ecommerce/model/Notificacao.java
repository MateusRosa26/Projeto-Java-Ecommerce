package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.csv.EntityWithId;

import java.util.Date;

public class Notificacao implements CsvSerializable, EntityWithId {
    private Long id;
    private Long clienteId;
    private Long pedidoId;
    private String titulo;
    private String mensagem;
    private String tipo;
    private boolean lida;
    private Date dataEnvio;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Notificacao() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.dataEnvio = new Date();
        this.lida = false;
    }

    public Notificacao(Long clienteId, Long pedidoId, String titulo, String mensagem, String tipo) {
        this();
        this.clienteId = clienteId;
        this.pedidoId = pedidoId;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipo = tipo;
    }

    public Notificacao(Long id, Long clienteId, Long pedidoId, String titulo, String mensagem, String tipo, boolean lida, Date dataEnvio, Date createdAt, Date updatedAt) {
        this.id = id;
        this.clienteId = clienteId;
        this.pedidoId = pedidoId;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.lida = lida;
        this.dataEnvio = dataEnvio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public void marcarComoLida() {
        this.lida = true;
        this.updatedAt = new Date();
    }

    public void marcarComoNaoLida() {
        this.lida = false;
        this.updatedAt = new Date();
    }

    public boolean isLida() {
        return lida;
    }

    public String getResumo() {
        return titulo + ": " + mensagem;
    }

    // Getters e Setters
    public long getId() {
        return id != null ? id : 0L;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        this.updatedAt = new Date();
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
        this.updatedAt = new Date();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.updatedAt = new Date();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
        this.updatedAt = new Date();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.updatedAt = new Date();
    }

    public void setLida(boolean lida) {
        this.lida = lida;
        this.updatedAt = new Date();
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
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
        return new String[]{
                String.valueOf(id != null ? id : ""),
                String.valueOf(clienteId != null ? clienteId : ""),
                String.valueOf(pedidoId != null ? pedidoId : ""),
                titulo != null ? titulo : "",
                mensagem != null ? mensagem : "",
                tipo != null ? tipo : "",
                String.valueOf(lida),
                dataEnvio != null ? String.valueOf(dataEnvio.getTime()) : "",
                createdAt != null ? String.valueOf(createdAt.getTime()) : "",
                updatedAt != null ? String.valueOf(updatedAt.getTime()) : ""
        };
    }

    public static Notificacao fromCsv(String[] fields) {
        if (fields.length >= 10) {
            Notificacao notificacao = new Notificacao();
            notificacao.id = fields[0].isEmpty() ? null : Long.parseLong(fields[0]);
            notificacao.clienteId = fields[1].isEmpty() ? null : Long.parseLong(fields[1]);
            notificacao.pedidoId = fields[2].isEmpty() ? null : Long.parseLong(fields[2]);
            notificacao.titulo = fields[3].isEmpty() ? null : fields[3];
            notificacao.mensagem = fields[4].isEmpty() ? null : fields[4];
            notificacao.tipo = fields[5].isEmpty() ? null : fields[5];
            notificacao.lida = Boolean.parseBoolean(fields[6]);
            notificacao.dataEnvio = fields[7].isEmpty() ? null : new Date(Long.parseLong(fields[7]));
            notificacao.createdAt = fields[8].isEmpty() ? null : new Date(Long.parseLong(fields[8]));
            notificacao.updatedAt = fields[9].isEmpty() ? null : new Date(Long.parseLong(fields[9]));
            return notificacao;
        }
        return null;
    }

    public static String[] csvHeader() {
        return new String[]{"id", "clienteId", "pedidoId", "titulo", "mensagem", "tipo", "lida", "dataEnvio", "createdAt", "updatedAt"};
    }
}
