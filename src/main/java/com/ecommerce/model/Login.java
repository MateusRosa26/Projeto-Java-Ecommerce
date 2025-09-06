package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;
import java.util.Date;

public class Login implements CsvSerializable {
    private String email;
    private String senha;
    private Date dataLogin;
    private Long clienteId;
    private Date createdAt;
    private Date updatedAt;

    public Login() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Login(String email, String senha, Date dataLogin, Long clienteId) {
        this();
        this.email = email;
        this.senha = senha;
        this.dataLogin = dataLogin;
        this.clienteId = clienteId;
    }

    public Login(String email, String senha, Date dataLogin, Long clienteId, Date createdAt, Date updatedAt) {
        this.email = email;
        this.senha = senha;
        this.dataLogin = dataLogin;
        this.clienteId = clienteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(Date dataLogin) {
        this.dataLogin = dataLogin;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
                email != null ? email : "",
                senha != null ? senha : "",
                dataLogin != null ? String.valueOf(dataLogin.getTime()) : "",
                clienteId != null ? String.valueOf(clienteId) : "",
                createdAt != null ? String.valueOf(createdAt.getTime()) : "",
                updatedAt != null ? String.valueOf(updatedAt.getTime()) : ""
        };
    }

    public static Login fromCsv(String[] fields) {
        if (fields.length >= 6) {
            Login login = new Login();
            login.email = fields[0].isEmpty() ? null : fields[0];
            login.senha = fields[1].isEmpty() ? null : fields[1];
            login.dataLogin = fields[2].isEmpty() ? null : new Date(Long.parseLong(fields[2]));
            login.clienteId = fields[3].isEmpty() ? null : Long.parseLong(fields[3]);
            login.createdAt = fields[4].isEmpty() ? null : new Date(Long.parseLong(fields[4]));
            login.updatedAt = fields[5].isEmpty() ? null : new Date(Long.parseLong(fields[5]));
            return login;
        }
        return null;
    }

    public static String[] csvHeader() {
        return new String[]{"email", "senha", "dataLogin", "clienteId", "createdAt", "updatedAt"};
    }
}
