package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import com.ecommerce.csv.EntityWithId;
import com.ecommerce.enums.UserRole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente implements CsvSerializable, EntityWithId {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cidade;
    private Date dataNascimento;
    private UserRole role;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Cliente() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Cliente(long id, String nome, String email) {
        this();
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = UserRole.CLIENTE;
    }

    public Cliente(long id, String nome, String email, String senha, String telefone, String cidade, Date dataNascimento, UserRole role) {
        this();
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cidade = cidade;
        this.dataNascimento = dataNascimento;
        this.role = role != null ? role : UserRole.CLIENTE;
    }

    // Métodos de negócio
    public void realizarPedido() {
        // Implementação do método para realizar pedido
    }

    public void avaliarEntrega() {
        // Implementação do método para avaliar entrega
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = new Date();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
        this.updatedAt = new Date();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
        this.updatedAt = new Date();
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
        this.updatedAt = new Date();
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.updatedAt = new Date();
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
            email, 
            senha != null ? senha : "", 
            telefone != null ? telefone : "", 
            cidade != null ? cidade : "", 
            dataNascimento != null ? sdf.format(dataNascimento) : "", 
            role != null ? String.valueOf(role.ordinal()) : "0", 
            sdf.format(createdAt), 
            sdf.format(updatedAt)
        };
    }

    public static Cliente fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long id = Long.parseLong(row[0]);
            String nome = row[1];
            String email = row[2];
            String senha = row[3];
            String telefone = row[4];
            String cidade = row[5];
            Date dataNascimento = row[6].isEmpty() ? null : sdf.parse(row[6]);
            UserRole role = UserRole.values()[Integer.parseInt(row[7])];
            Date createdAt = sdf.parse(row[8]);
            Date updatedAt = sdf.parse(row[9]);
            
            return new Cliente(id, nome, email, senha, telefone, cidade, dataNascimento, role);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "email", "senha", "telefone", "cidade", "dataNascimento", "role", "createdAt", "updatedAt"};
    }
}
