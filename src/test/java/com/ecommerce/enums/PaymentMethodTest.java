package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PaymentMethodTest {

    @Test
    void values_ShouldReturnAllPaymentMethods() {
        PaymentMethod[] methods = PaymentMethod.values();
        
        assertEquals(6, methods.length);
        assertTrue(List.of(methods).contains(PaymentMethod.PIX));
        assertTrue(List.of(methods).contains(PaymentMethod.CARTAO_CREDITO));
        assertTrue(List.of(methods).contains(PaymentMethod.CARTAO_DEBITO));
        assertTrue(List.of(methods).contains(PaymentMethod.BOLETO));
        assertTrue(List.of(methods).contains(PaymentMethod.TICKET));
        assertTrue(List.of(methods).contains(PaymentMethod.DINHEIRO));
    }

    @Test
    void valueOf_ShouldReturnCorrectPaymentMethod() {
        assertEquals(PaymentMethod.PIX, PaymentMethod.valueOf("PIX"));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.valueOf("CARTAO_CREDITO"));
        assertEquals(PaymentMethod.CARTAO_DEBITO, PaymentMethod.valueOf("CARTAO_DEBITO"));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.valueOf("BOLETO"));
        assertEquals(PaymentMethod.TICKET, PaymentMethod.valueOf("TICKET"));
        assertEquals(PaymentMethod.DINHEIRO, PaymentMethod.valueOf("DINHEIRO"));
    }

    @Test
    void valueOf_ShouldThrowException_WhenInvalidPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            PaymentMethod.valueOf("INVALID_METHOD");
        });
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertEquals("PIX", PaymentMethod.PIX.getDescription());
        assertEquals("Cartão de Crédito", PaymentMethod.CARTAO_CREDITO.getDescription());
        assertEquals("Cartão de Débito", PaymentMethod.CARTAO_DEBITO.getDescription());
        assertEquals("Boleto", PaymentMethod.BOLETO.getDescription());
        assertEquals("Ticket", PaymentMethod.TICKET.getDescription());
        assertEquals("Dinheiro", PaymentMethod.DINHEIRO.getDescription());
    }

    @Test
    void fromString_ShouldReturnCorrectPaymentMethod() {
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("PIX"));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString("Cartão de Crédito"));
        assertEquals(PaymentMethod.CARTAO_DEBITO, PaymentMethod.fromString("Cartão de Débito"));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.fromString("Boleto"));
        assertEquals(PaymentMethod.TICKET, PaymentMethod.fromString("Ticket"));
        assertEquals(PaymentMethod.DINHEIRO, PaymentMethod.fromString("Dinheiro"));
    }

    @Test
    void fromString_ShouldReturnNull_WhenInvalidString() {
        assertNull(PaymentMethod.fromString("Invalid Method"));
        assertNull(PaymentMethod.fromString(""));
        assertNull(PaymentMethod.fromString(null));
    }

    @Test
    void fromString_ShouldBeCaseInsensitive() {
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("pix"));
        assertEquals(PaymentMethod.CARTAO_CREDITO, PaymentMethod.fromString("cartão de crédito"));
        assertEquals(PaymentMethod.CARTAO_DEBITO, PaymentMethod.fromString("CARTÃO DE DÉBITO"));
        assertEquals(PaymentMethod.BOLETO, PaymentMethod.fromString("BOLETO"));
        assertEquals(PaymentMethod.TICKET, PaymentMethod.fromString("ticket"));
        assertEquals(PaymentMethod.DINHEIRO, PaymentMethod.fromString("DINHEIRO"));
    }

    @Test
    void ordinal_ShouldReturnCorrectIndex() {
        assertEquals(0, PaymentMethod.PIX.ordinal());
        assertEquals(1, PaymentMethod.CARTAO_CREDITO.ordinal());
        assertEquals(2, PaymentMethod.CARTAO_DEBITO.ordinal());
        assertEquals(3, PaymentMethod.BOLETO.ordinal());
        assertEquals(4, PaymentMethod.TICKET.ordinal());
        assertEquals(5, PaymentMethod.DINHEIRO.ordinal());
    }

    @Test
    void name_ShouldReturnCorrectName() {
        assertEquals("PIX", PaymentMethod.PIX.name());
        assertEquals("CARTAO_CREDITO", PaymentMethod.CARTAO_CREDITO.name());
        assertEquals("CARTAO_DEBITO", PaymentMethod.CARTAO_DEBITO.name());
        assertEquals("BOLETO", PaymentMethod.BOLETO.name());
        assertEquals("TICKET", PaymentMethod.TICKET.name());
        assertEquals("DINHEIRO", PaymentMethod.DINHEIRO.name());
    }

    @Test
    void toString_ShouldReturnCorrectString() {
        assertEquals("PIX", PaymentMethod.PIX.toString());
        assertEquals("CARTAO_CREDITO", PaymentMethod.CARTAO_CREDITO.toString());
        assertEquals("CARTAO_DEBITO", PaymentMethod.CARTAO_DEBITO.toString());
        assertEquals("BOLETO", PaymentMethod.BOLETO.toString());
        assertEquals("TICKET", PaymentMethod.TICKET.toString());
        assertEquals("DINHEIRO", PaymentMethod.DINHEIRO.toString());
    }
}
