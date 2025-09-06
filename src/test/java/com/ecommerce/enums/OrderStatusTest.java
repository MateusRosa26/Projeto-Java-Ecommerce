package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class OrderStatusTest {

    @Test
    void values_ShouldReturnAllStatuses() {
        OrderStatus[] statuses = OrderStatus.values();
        
        assertEquals(4, statuses.length);
        assertTrue(List.of(statuses).contains(OrderStatus.PREPARANDO));
        assertTrue(List.of(statuses).contains(OrderStatus.SAINDO_PARA_ENTREGA));
        assertTrue(List.of(statuses).contains(OrderStatus.ENTREGUE));
        assertTrue(List.of(statuses).contains(OrderStatus.CANCELADO));
    }

    @Test
    void valueOf_ShouldReturnCorrectStatus() {
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.valueOf("PREPARANDO"));
        assertEquals(OrderStatus.SAINDO_PARA_ENTREGA, OrderStatus.valueOf("SAINDO_PARA_ENTREGA"));
        assertEquals(OrderStatus.ENTREGUE, OrderStatus.valueOf("ENTREGUE"));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.valueOf("CANCELADO"));
    }

    @Test
    void valueOf_ShouldThrowException_WhenInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            OrderStatus.valueOf("INVALID_STATUS");
        });
    }

    @Test
    void ordinal_ShouldReturnCorrectIndex() {
        assertEquals(0, OrderStatus.PREPARANDO.ordinal());
        assertEquals(1, OrderStatus.SAINDO_PARA_ENTREGA.ordinal());
        assertEquals(2, OrderStatus.ENTREGUE.ordinal());
        assertEquals(3, OrderStatus.CANCELADO.ordinal());
    }

    @Test
    void name_ShouldReturnCorrectName() {
        assertEquals("PREPARANDO", OrderStatus.PREPARANDO.name());
        assertEquals("SAINDO_PARA_ENTREGA", OrderStatus.SAINDO_PARA_ENTREGA.name());
        assertEquals("ENTREGUE", OrderStatus.ENTREGUE.name());
        assertEquals("CANCELADO", OrderStatus.CANCELADO.name());
    }

    @Test
    void toString_ShouldReturnCorrectString() {
        assertEquals("PREPARANDO", OrderStatus.PREPARANDO.toString());
        assertEquals("SAINDO_PARA_ENTREGA", OrderStatus.SAINDO_PARA_ENTREGA.toString());
        assertEquals("ENTREGUE", OrderStatus.ENTREGUE.toString());
        assertEquals("CANCELADO", OrderStatus.CANCELADO.toString());
    }
}
