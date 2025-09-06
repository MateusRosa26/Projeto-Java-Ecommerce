package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnderecoEntrega implements CsvSerializable {
    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private String cep;
    private Long clienteId;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public EnderecoEntrega() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public EnderecoEntrega(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this();
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public EnderecoEntrega(Long id, String rua, String numero, String bairro, String cidade, String estado, String complemento, String cep, Long clienteId) {
        this(rua, numero, bairro, cidade, estado, cep);
        this.id = id;
        this.complemento = complemento;
        this.clienteId = clienteId;
    }

    public EnderecoEntrega(Long id, String rua, String numero, String bairro, String cidade, String estado, String complemento, String cep, Long clienteId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
        this.cep = cep;
        this.clienteId = clienteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public String getEnderecoCompleto() {
        StringBuilder endereco = new StringBuilder();
        endereco.append(rua).append(", ").append(numero);
        if (complemento != null && !complemento.isEmpty()) {
            endereco.append(", ").append(complemento);
        }
        endereco.append(" - ").append(bairro);
        endereco.append(", ").append(cidade).append(" - ").append(estado);
        endereco.append(" CEP: ").append(cep);
        return endereco.toString();
    }

    public boolean isValido() {
        return rua != null && !rua.isEmpty() &&
               numero != null && !numero.isEmpty() &&
               bairro != null && !bairro.isEmpty() &&
               cidade != null && !cidade.isEmpty() &&
               estado != null && !estado.isEmpty() &&
               cep != null && !cep.isEmpty();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
        this.updatedAt = new Date();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
        this.updatedAt = new Date();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
        this.updatedAt = new Date();
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
        this.updatedAt = new Date();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.updatedAt = new Date();
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
        this.updatedAt = new Date();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
            rua,
            numero,
            bairro,
            cidade,
            estado,
            complemento != null ? complemento : "",
            cep,
            String.valueOf(clienteId),
            sdf.format(createdAt),
            sdf.format(updatedAt)
        };
    }

    public static EnderecoEntrega fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Long id = Long.parseLong(row[0]);
            String rua = row[1];
            String numero = row[2];
            String bairro = row[3];
            String cidade = row[4];
            String estado = row[5];
            String complemento = row[6];
            String cep = row[7];
            Long clienteId = Long.parseLong(row[8]);
            Date createdAt = sdf.parse(row[9]);
            Date updatedAt = sdf.parse(row[10]);
            return new EnderecoEntrega(id, rua, numero, bairro, cidade, estado, complemento, cep, clienteId, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "rua", "numero", "bairro", "cidade", "estado", "complemento", "cep", "clienteId", "createdAt", "updatedAt"};
    }
}
