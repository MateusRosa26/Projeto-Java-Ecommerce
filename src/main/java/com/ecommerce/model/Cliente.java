package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.util.Date;

public class Cliente implements CsvSerializable {
    private final long id;
    private final String nome;
    private final String email;
    private String senha;
    private String telefone;
    private String cidade;
    private Date dataNascimento;
    private int role;
    private Date createdAt;
    private Date updatedAt;

    // Métodos
    public void realizarPedido() {
        System.out.println("Cliente " + nome + " está realizando um pedido...");
        System.out.println("Telefone: " + (getTelefone() != null ? getTelefone() : "Não informado"));
        System.out.println("Cidade: " + (getCidade() != null ? getCidade() : "Não informada"));
        // Implementação do método para realizar pedido
    }

    public void avaliarEntrega() {
        System.out.println("Cliente " + nome + " está avaliando a entrega...");
        System.out.println("Data de nascimento: " + (getDataNascimento() != null ? getDataNascimento().toString() : "Não informada"));
        System.out.println("Role: " + getRole());
        System.out.println("Criado em: " + (getCreatedAt() != null ? getCreatedAt().toString() : "Não informado"));
        System.out.println("Atualizado em: " + (getUpdatedAt() != null ? getUpdatedAt().toString() : "Não informado"));
        // Implementação do método para avaliar entrega
    }
    
    public void demonstrarFuncionalidades() {
        realizarPedido();
        avaliarEntrega();
    }

    // Getters e Setters

    public Cliente(long id, String nome, String email) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email é obrigatório");
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
            String.valueOf(id), 
            nome, 
            email,
            senha != null ? senha : "",
            telefone != null ? telefone : "",
            cidade != null ? cidade : "",
            dataNascimento != null ? dataNascimento.toString() : "",
            String.valueOf(role),
            createdAt != null ? createdAt.toString() : "",
            updatedAt != null ? updatedAt.toString() : ""
        };
    }

    public static Cliente fromCsv(String[] row) {
        long id = Long.parseLong(row[0]);
        Cliente cliente = new Cliente(id, row[1], row[2]);
        
        if (row.length > 3 && !row[3].isEmpty()) cliente.setSenha(row[3]);
        if (row.length > 4 && !row[4].isEmpty()) cliente.setTelefone(row[4]);
        if (row.length > 5 && !row[5].isEmpty()) cliente.setCidade(row[5]);
        if (row.length > 6 && !row[6].isEmpty()) {
            try {
                cliente.setDataNascimento(new Date(Long.parseLong(row[6])));
            } catch (NumberFormatException e) {
                // Ignorar se não conseguir parsear a data
            }
        }
        if (row.length > 7 && !row[7].isEmpty()) {
            try {
                cliente.setRole(Integer.parseInt(row[7]));
            } catch (NumberFormatException e) {
                // Ignorar se não conseguir parsear o role
            }
        }
        if (row.length > 8 && !row[8].isEmpty()) {
            try {
                cliente.setCreatedAt(new Date(Long.parseLong(row[8])));
            } catch (NumberFormatException e) {
                // Ignorar se não conseguir parsear a data
            }
        }
        if (row.length > 9 && !row[9].isEmpty()) {
            try {
                cliente.setUpdatedAt(new Date(Long.parseLong(row[9])));
            } catch (NumberFormatException e) {
                // Ignorar se não conseguir parsear a data
            }
        }
        
        return cliente;
    }

    public static String[] csvHeader() {
        return new String[]{"id", "nome", "email", "senha", "telefone", "cidade", "dataNascimento", "role", "createdAt", "updatedAt"};
    }
}
