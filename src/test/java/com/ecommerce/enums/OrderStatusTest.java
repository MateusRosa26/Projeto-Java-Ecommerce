package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void testOrderStatusValues() {
        // Verifica se todos os status existem
        assertEquals(4, OrderStatus.values().length);
        assertTrue(OrderStatus.PREPARANDO != null);
        assertTrue(OrderStatus.SAINDO_PARA_ENTREGA != null);
        assertTrue(OrderStatus.ENTREGUE != null);
        assertTrue(OrderStatus.CANCELADO != null);
    }

    @Test
    void testOrderStatusDescriptions() {
        assertEquals("Preparando", OrderStatus.PREPARANDO.getDescription());
        assertEquals("Saindo para entrega", OrderStatus.SAINDO_PARA_ENTREGA.getDescription());
        assertEquals("Entregue", OrderStatus.ENTREGUE.getDescription());
        assertEquals("Cancelado", OrderStatus.CANCELADO.getDescription());
    }

    @Test
    void testOrderStatusCodes() {
        assertEquals(1, OrderStatus.PREPARANDO.getCode());
        assertEquals(2, OrderStatus.SAINDO_PARA_ENTREGA.getCode());
        assertEquals(3, OrderStatus.ENTREGUE.getCode());
        assertEquals(4, OrderStatus.CANCELADO.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(1));
        assertEquals(OrderStatus.SAINDO_PARA_ENTREGA, OrderStatus.fromCode(2));
        assertEquals(OrderStatus.ENTREGUE, OrderStatus.fromCode(3));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.fromCode(4));
        
        // Código inválido deve retornar PREPARANDO como default
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(999));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(0));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(-1));
    }

    @Test
    void testFromString() {
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString("Preparando"));
        assertEquals(OrderStatus.SAINDO_PARA_ENTREGA, OrderStatus.fromString("Saindo para entrega"));
        assertEquals(OrderStatus.ENTREGUE, OrderStatus.fromString("Entregue"));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.fromString("Cancelado"));
        
        // Case insensitive
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString("preparando"));
        assertEquals(OrderStatus.ENTREGUE, OrderStatus.fromString("ENTREGUE"));
        
        // String inválida deve retornar PREPARANDO como default
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString("Processando"));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString(""));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString(null));
    }
}
