package com.ecommerce.enums;

public enum PaymentMethod {
    CARTAO_CREDITO("Cartão de Crédito", 1),
    CARTAO_DEBITO("Cartão de Débito", 2),
    BOLETO("Boleto", 3),
    PIX("PIX", 4),
    TICKET("Ticket", 5),
    DINHEIRO("Dinheiro", 6);
    
    private final String description;
    private final int code;
    
    PaymentMethod(String description, int code) {
        this.description = description;
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCode() {
        return code;
    }
    
    public static PaymentMethod fromCode(int code) {
        for (PaymentMethod method : values()) {
            if (method.code == code) {
                return method;
            }
        }
        return CARTAO_CREDITO; // default
    }
    
    public static PaymentMethod fromString(String methodString) {
        for (PaymentMethod method : values()) {
            if (method.description.equalsIgnoreCase(methodString)) {
                return method;
            }
        }
        return CARTAO_CREDITO; // default
    }
}
