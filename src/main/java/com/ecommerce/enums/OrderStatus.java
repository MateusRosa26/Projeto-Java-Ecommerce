package com.ecommerce.enums;

public enum OrderStatus {
    PREPARANDO("Preparando"),
    SAINDO_PARA_ENTREGA("Saindo para entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
