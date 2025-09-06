package com.ecommerce.enums;

public enum UserRole {
    CLIENTE("Cliente"),
    ADMIN("Admin"),
    GERENTE("Gerente"),
    ENTREGADOR("Entregador");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
