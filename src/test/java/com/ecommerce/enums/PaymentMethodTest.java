package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    @Test
    void testPaymentMethodValues() {
        // Verifica se todos os métodos existem
        assertEquals(6, PaymentMethod.values().length);
        assertTrue(PaymentMethod.CARTAO_CREDITO != null);
        assertTrue(PaymentMethod.CARTAO_DEBITO != null);
        assertTrue(PaymentMethod.BOLETO != null);
        assertTrue(PaymentMethod.PIX != null);
        assertTrue(PaymentMethod.TICKET != null);
        assertTrue(PaymentMethod.DINHEIRO != null);
    }

    @Test
    void testPaymentMethodDescriptions() {
        assertEquals("Cartão de Crédito", PaymentMethod.CARTAO_CREDITO.getDescription());
        assertEquals("Cartão de Débito", PaymentMethod.CARTAO_DEBITO.getDescription());
        assertEquals("Boleto", PaymentMethod.BOLETO.getDescription());
        assertEquals("PIX", PaymentMethod.PIX.getDescription());
        assertEquals("Ticket", PaymentMethod.TICKET.getDescription());
        assertEquals("Dinheiro", PaymentMethod.DINHEIRO.getDescription());
    }

    @Test
    void testPaymentMethodCodes() {
        assertEquals(1, PaymentMethod.CARTAO_CREDITO.getCode());
        assertEquals(2, PaymentMethod.CARTAO_DEBITO.getCode());
        assertEquals(3, PaymentMethod.BOLETO.getCode());
        assertEquals(4, PaymentMethod.PIX.getCode());
        assertEquals(5, PaymentMethod.TICKET.getCode());
        assertEquals(6, PaymentMethod.DINHEIRO.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromCode(1));
        assertEquals(PaymentMethod.CARTAO_DEBITO, PaymentMethod.fromCode(2));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.fromCode(3));
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromCode(4));
        assertEquals(PaymentMethod.TICKET, PaymentMethod.fromCode(5));
        assertEquals(PaymentMethod.DINHEIRO, PaymentMethod.fromCode(6));
        
        // Código inválido deve retornar CARTAO_CREDITO como default
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromCode(999));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromCode(0));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromCode(-1));
    }

    @Test
    void testFromString() {
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString("Cartão de Crédito"));
        assertEquals(PaymentMethod.CARTAO_DEBITO, PaymentMethod.fromString("Cartão de Débito"));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.fromString("Boleto"));
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("PIX"));
        assertEquals(PaymentMethod.TICKET, PaymentMethod.fromString("Ticket"));
        assertEquals(PaymentMethod.DINHEIRO, PaymentMethod.fromString("Dinheiro"));
        
        // Case insensitive
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("pix"));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.fromString("BOLETO"));
        
        // String inválida deve retornar CARTAO_CREDITO como default
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString("PayPal"));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString(""));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString(null));
    }
}
