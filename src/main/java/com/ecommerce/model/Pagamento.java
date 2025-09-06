package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.enums.PaymentMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pagamento implements CsvSerializable {
    private Long id;
    private Long pedidoId;
    private PaymentMethod metodoPagamento;
    private double valor;
    private String status;
    private String codigoTransacao;
    private Date dataPagamento;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Pagamento() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = "PENDENTE";
    }

    public Pagamento(Long pedidoId, PaymentMethod metodoPagamento, double valor) {
        this();
        this.pedidoId = pedidoId;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
    }

    public Pagamento(Long id, Long pedidoId, PaymentMethod metodoPagamento, double valor, String status, String codigoTransacao, Date dataPagamento) {
        this(pedidoId, metodoPagamento, valor);
        this.id = id;
        this.status = status;
        this.codigoTransacao = codigoTransacao;
        this.dataPagamento = dataPagamento;
    }

    public Pagamento(Long id, Long pedidoId, PaymentMethod metodoPagamento, double valor, String status, String codigoTransacao, Date dataPagamento, Date createdAt, Date updatedAt) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.status = status;
        this.codigoTransacao = codigoTransacao;
        this.dataPagamento = dataPagamento;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public void processarPagamento() {
        this.status = "PROCESSANDO";
        this.updatedAt = new Date();
    }

    public void confirmarPagamento(String codigoTransacao) {
        this.status = "APROVADO";
        this.codigoTransacao = codigoTransacao;
        this.dataPagamento = new Date();
        this.updatedAt = new Date();
    }

    public void rejeitarPagamento() {
        this.status = "REJEITADO";
        this.updatedAt = new Date();
    }

    public boolean isAprovado() {
        return "APROVADO".equals(this.status);
    }

    public boolean isPendente() {
        return "PENDENTE".equals(this.status);
    }

    public boolean isRejeitado() {
        return "REJEITADO".equals(this.status);
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

    public PaymentMethod getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(PaymentMethod metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
        this.updatedAt = new Date();
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
        this.updatedAt = new Date();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = new Date();
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
        this.updatedAt = new Date();
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
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
            String.valueOf(metodoPagamento.ordinal()),
            String.valueOf(valor),
            status,
            codigoTransacao != null ? codigoTransacao : "",
            dataPagamento != null ? sdf.format(dataPagamento) : "",
            sdf.format(createdAt),
            sdf.format(updatedAt)
        };
    }

    public static Pagamento fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long id = Long.parseLong(row[0]);
            Long pedidoId = Long.parseLong(row[1]);
            PaymentMethod metodoPagamento = PaymentMethod.values()[Integer.parseInt(row[2])];
            double valor = Double.parseDouble(row[3]);
            String status = row[4];
            String codigoTransacao = row[5];
            Date dataPagamento = row[6].isEmpty() ? null : sdf.parse(row[6]);
            Date createdAt = sdf.parse(row[7]);
            Date updatedAt = sdf.parse(row[8]);
            return new Pagamento(id, pedidoId, metodoPagamento, valor, status, codigoTransacao, dataPagamento, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "pedidoId", "metodoPagamento", "valor", "status", "codigoTransacao", "dataPagamento", "createdAt", "updatedAt"};
    }
}
