package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderNumberGeneratorTest {

    @Test
    void testGenerateOrderNumber() {
        // Gera dois números e verifica se são diferentes
        String order1 = OrderNumberGenerator.generateOrderNumber();
        String order2 = OrderNumberGenerator.generateOrderNumber();
        
        assertNotNull(order1);
        assertNotNull(order2);
        assertNotEquals(order1, order2);
        
        // Verifica formato PED + data + sequência
        assertTrue(order1.startsWith("PED"));
        assertTrue(order2.startsWith("PED"));
        
        // Verifica se tem 15 caracteres (PED + 8 data + 4 sequência)
        assertEquals(15, order1.length());
        assertEquals(15, order2.length());
    }

    @Test
    void testGenerateOrderNumberWithPrefix() {
        String order1 = OrderNumberGenerator.generateOrderNumber("ORD");
        String order2 = OrderNumberGenerator.generateOrderNumber("PED");
        
        assertNotNull(order1);
        assertNotNull(order2);
        assertNotEquals(order1, order2);
        
        // Verifica prefixos
        assertTrue(order1.startsWith("ORD"));
        assertTrue(order2.startsWith("PED"));
        
        // Verifica se tem 15 caracteres
        assertEquals(15, order1.length());
        assertEquals(15, order2.length());
    }

    @Test
    void testOrderNumberUniqueness() {
        // Gera 100 números e verifica se são únicos
        String[] orders = new String[100];
        for (int i = 0; i < 100; i++) {
            orders[i] = OrderNumberGenerator.generateOrderNumber();
        }
        
        // Verifica se todos são únicos
        for (int i = 0; i < orders.length; i++) {
            for (int j = i + 1; j < orders.length; j++) {
                assertNotEquals(orders[i], orders[j], 
                    "Números de pedido devem ser únicos: " + orders[i] + " == " + orders[j]);
            }
        }
    }

    @Test
    void testResetCounter() {
        // Gera alguns números
        String order1 = OrderNumberGenerator.generateOrderNumber();
        String order2 = OrderNumberGenerator.generateOrderNumber();
        
        // Reseta o contador
        OrderNumberGenerator.resetCounter();
        
        // Gera novos números
        String order3 = OrderNumberGenerator.generateOrderNumber();
        String order4 = OrderNumberGenerator.generateOrderNumber();
        
        // Verifica se os números são diferentes dos anteriores
        assertNotEquals(order1, order3);
        assertNotEquals(order2, order4);
    }

    @Test
    void testOrderNumberFormat() {
        String order = OrderNumberGenerator.generateOrderNumber();
        
        // Verifica se começa com PED
        assertTrue(order.startsWith("PED"));
        
        // Verifica se tem exatamente 15 caracteres
        assertEquals(15, order.length());
        
        // Verifica se os últimos 4 caracteres são números
        String sequence = order.substring(11);
        assertTrue(sequence.matches("\\d{4}"));
    }
}
