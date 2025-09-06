package com.ecommerce.enums;

public enum PaymentMethod {
    PIX("PIX"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    BOLETO("Boleto"),
    TICKET("Ticket"),
    DINHEIRO("Dinheiro");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public static PaymentMethod fromString(String method) {
        for (PaymentMethod pm : values()) {
            if (pm.getDescription().equalsIgnoreCase(method)) {
                return pm;
            }
        }
        return null;
    }
}
