package com.ecommerce.enums;

public enum UserRole {
    CLIENTE("Cliente", 1),
    GERENTE("Gerente", 2),
    ENTREGADOR("Entregador", 3);
    
    private final String description;
    private final int code;
    
    UserRole(String description, int code) {
        this.description = description;
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCode() {
        return code;
    }
    
    public static UserRole fromCode(int code) {
        for (UserRole role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        return CLIENTE; // default
    }
    
    public static UserRole fromString(String roleString) {
        for (UserRole role : values()) {
            if (role.description.equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        return CLIENTE; // default
    }
}
