package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressValidatorTest {

    @Test
    void testValidCep() {
        // CEPs válidos
        assertTrue(AddressValidator.isValidCep("12345-678"));
        assertTrue(AddressValidator.isValidCep("01234-567"));
        assertTrue(AddressValidator.isValidCep("99999-999"));
    }

    @Test
    void testInvalidCep() {
        // CEPs inválidos
        assertFalse(AddressValidator.isValidCep("12345678")); // sem hífen
        assertFalse(AddressValidator.isValidCep("1234-567")); // muito curto
        assertFalse(AddressValidator.isValidCep("123456-789")); // muito longo
        assertFalse(AddressValidator.isValidCep("12345-67a")); // com letra
        assertFalse(AddressValidator.isValidCep("")); // vazio
        assertFalse(AddressValidator.isValidCep(null)); // nulo
    }

    @Test
    void testValidUf() {
        // UFs válidas
        assertTrue(AddressValidator.isValidUf("SP"));
        assertTrue(AddressValidator.isValidUf("RJ"));
        assertTrue(AddressValidator.isValidUf("MG"));
        assertTrue(AddressValidator.isValidUf("RS"));
    }

    @Test
    void testInvalidUf() {
        // UFs inválidas
        assertFalse(AddressValidator.isValidUf("São Paulo")); // nome completo
        assertFalse(AddressValidator.isValidUf("s")); // muito curto
        assertFalse(AddressValidator.isValidUf("SPA")); // muito longo
        assertFalse(AddressValidator.isValidUf("12")); // números
        assertFalse(AddressValidator.isValidUf("")); // vazio
        assertFalse(AddressValidator.isValidUf(null)); // nulo
    }

    @Test
    void testValidAddress() {
        // Endereços válidos
        assertTrue(AddressValidator.isValidAddress(
            "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567"
        ));
        assertTrue(AddressValidator.isValidAddress(
            "Avenida Paulista", "1000", "Bela Vista", "São Paulo", "SP", "01310-100"
        ));
    }

    @Test
    void testInvalidAddress() {
        // Endereços inválidos - campos obrigatórios vazios
        assertFalse(AddressValidator.isValidAddress(
            "", "123", "Centro", "São Paulo", "SP", "01234-567"
        ));
        assertFalse(AddressValidator.isValidAddress(
            "Rua das Flores", "", "Centro", "São Paulo", "SP", "01234-567"
        ));
        assertFalse(AddressValidator.isValidAddress(
            "Rua das Flores", "123", "", "São Paulo", "SP", "01234-567"
        ));
        assertFalse(AddressValidator.isValidAddress(
            "Rua das Flores", "123", "Centro", "", "SP", "01234-567"
        ));
        
        // Endereços inválidos - UF e CEP inválidos
        assertFalse(AddressValidator.isValidAddress(
            "Rua das Flores", "123", "Centro", "São Paulo", "São Paulo", "01234-567"
        ));
        assertFalse(AddressValidator.isValidAddress(
            "Rua das Flores", "123", "Centro", "São Paulo", "SP", "12345678"
        ));
    }

    @Test
    void testValidationMessage() {
        String message = AddressValidator.getValidationMessage();
        assertNotNull(message);
        assertTrue(message.contains("rua"));
        assertTrue(message.contains("número"));
        assertTrue(message.contains("bairro"));
        assertTrue(message.contains("cidade"));
        assertTrue(message.contains("estado"));
        assertTrue(message.contains("CEP"));
    }
}
