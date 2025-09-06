package com.ecommerce;

import com.ecommerce.util.PasswordValidator;
import com.ecommerce.util.AddressValidator;
import com.ecommerce.util.OrderNumberGenerator;
import com.ecommerce.enums.UserRole;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste rápido para verificar se todas as funcionalidades básicas estão funcionando
 */
class QuickTest {

    @Test
    void testPasswordValidation() {
        assertTrue(PasswordValidator.isValid("MinhaSenh@123"));
        assertFalse(PasswordValidator.isValid("123456"));
    }

    @Test
    void testAddressValidation() {
        assertTrue(AddressValidator.isValidAddress("Rua Teste", "123", "Centro", "São Paulo", "SP", "01234-567"));
        assertFalse(AddressValidator.isValidAddress("", "123", "Centro", "São Paulo", "SP", "01234-567"));
    }

    @Test
    void testOrderNumberGeneration() {
        String order1 = OrderNumberGenerator.generateOrderNumber();
        String order2 = OrderNumberGenerator.generateOrderNumber();
        assertNotEquals(order1, order2);
        assertTrue(order1.startsWith("PED"));
    }

    @Test
    void testEnums() {
        assertEquals("Cliente", UserRole.CLIENTE.getDescription());
        assertEquals("Preparando", OrderStatus.PREPARANDO.getDescription());
        assertEquals("PIX", PaymentMethod.PIX.getDescription());
    }

    @Test
    void testEnumConversions() {
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(1));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(1));
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("PIX"));
    }
}
