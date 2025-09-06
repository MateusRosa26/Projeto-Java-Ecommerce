package com.ecommerce.enums;

public enum OrderStatus {
    PREPARANDO("Preparando", 1),
    SAINDO_PARA_ENTREGA("Saindo para entrega", 2),
    ENTREGUE("Entregue", 3),
    CANCELADO("Cancelado", 4);
    
    private final String description;
    private final int code;
    
    OrderStatus(String description, int code) {
        this.description = description;
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCode() {
        return code;
    }
    
    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return PREPARANDO; // default
    }
    
    public static OrderStatus fromString(String statusString) {
        for (OrderStatus status : values()) {
            if (status.description.equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        return PREPARANDO; // default
    }
}
