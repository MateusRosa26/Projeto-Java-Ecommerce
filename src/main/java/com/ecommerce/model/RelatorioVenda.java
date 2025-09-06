package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import java.util.Date;

public class RelatorioVenda implements CsvSerializable {
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String produtos;
    private Double totalVendas;
    private Long criadoPor;
    private Date createdAt;
    private Date updatedAt;

    public RelatorioVenda() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public RelatorioVenda(Long id, Date dataInicio, Date dataFim, String produtos, Double totalVendas, Long criadoPor) {
        this();
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.produtos = produtos;
        this.totalVendas = totalVendas;
        this.criadoPor = criadoPor;
    }

    public RelatorioVenda(Long id, Date dataInicio, Date dataFim, String produtos, Double totalVendas, Long criadoPor, Date createdAt, Date updatedAt) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.produtos = produtos;
        this.totalVendas = totalVendas;
        this.criadoPor = criadoPor;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public Double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(Double totalVendas) {
        this.totalVendas = totalVendas;
    }

    public Long getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Long criadoPor) {
        this.criadoPor = criadoPor;
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
                dataInicio != null ? String.valueOf(dataInicio.getTime()) : "",
                dataFim != null ? String.valueOf(dataFim.getTime()) : "",
                produtos != null ? produtos : "",
                totalVendas != null ? String.valueOf(totalVendas) : "",
                criadoPor != null ? String.valueOf(criadoPor) : "",
                createdAt != null ? String.valueOf(createdAt.getTime()) : "",
                updatedAt != null ? String.valueOf(updatedAt.getTime()) : ""
        };
    }

    public static RelatorioVenda fromCsv(String[] fields) {
        if (fields.length >= 8) {
            RelatorioVenda relatorio = new RelatorioVenda();
            relatorio.id = fields[0].isEmpty() ? null : Long.parseLong(fields[0]);
            relatorio.dataInicio = fields[1].isEmpty() ? null : new Date(Long.parseLong(fields[1]));
            relatorio.dataFim = fields[2].isEmpty() ? null : new Date(Long.parseLong(fields[2]));
            relatorio.produtos = fields[3].isEmpty() ? null : fields[3];
            relatorio.totalVendas = fields[4].isEmpty() ? null : Double.parseDouble(fields[4]);
            relatorio.criadoPor = fields[5].isEmpty() ? null : Long.parseLong(fields[5]);
            relatorio.createdAt = fields[6].isEmpty() ? null : new Date(Long.parseLong(fields[6]));
            relatorio.updatedAt = fields[7].isEmpty() ? null : new Date(Long.parseLong(fields[7]));
            return relatorio;
        }
        return null;
    }

    public static String[] csvHeader() {
        return new String[]{"id", "dataInicio", "dataFim", "produtos", "totalVendas", "criadoPor", "createdAt", "updatedAt"};
    }
}
