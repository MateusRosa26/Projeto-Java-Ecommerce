package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressValidatorTest {

    @Test
    void isValidAddress_ShouldReturnTrue_WhenAddressIsValid() {
        assertTrue(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234567"));
        assertTrue(AddressValidator.isValidAddress("Avenida Paulista", "1000", "Bela Vista", "São Paulo", "SP", "01310100"));
        assertTrue(AddressValidator.isValidAddress("Rua Augusta", "456", "Consolação", "São Paulo", "SP", "01305000"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenRuaIsEmpty() {
        assertFalse(AddressValidator.isValidAddress("", "123", "Centro", "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress(null, "123", "Centro", "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("   ", "123", "Centro", "São Paulo", "SP", "01234567"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenNumeroIsEmpty() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "", "Centro", "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", null, "Centro", "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "   ", "Centro", "São Paulo", "SP", "01234567"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenBairroIsEmpty() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "", "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", null, "São Paulo", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "   ", "São Paulo", "SP", "01234567"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenCidadeIsEmpty() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "", "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", null, "SP", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "   ", "SP", "01234567"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenEstadoIsEmpty() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "", "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", null, "01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "   ", "01234567"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenCepIsInvalid() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", ""));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", null));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "123"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "123456"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "123456789"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "abcdefgh"));
    }

    @Test
    void isValidAddress_ShouldReturnTrue_WhenCepIsValid() {
        assertTrue(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234567"));
        assertTrue(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "12345678"));
        assertTrue(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "98765432"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenCepHasSpaces() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234 567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", " 01234567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234567 "));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenCepHasDashes() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "012345-67"));
    }

    @Test
    void isValidAddress_ShouldReturnFalse_WhenCepHasAllSameDigits() {
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "11111111"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "00000000"));
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "99999999"));
    }

    @Test
    void getValidationMessage_ShouldReturnCorrectMessage() {
        // Test with valid address
        AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234567");
        assertEquals("Endereço válido", AddressValidator.getValidationMessage());
        
        // Test with invalid rua
        AddressValidator.isValidAddress("", "123", "Centro", "São Paulo", "SP", "01234567");
        assertEquals("Rua é obrigatória", AddressValidator.getValidationMessage());
        
        // Test with invalid numero
        AddressValidator.isValidAddress("Rua das Flores", "", "Centro", "São Paulo", "SP", "01234567");
        assertEquals("Número é obrigatório", AddressValidator.getValidationMessage());
        
        // Test with invalid bairro
        AddressValidator.isValidAddress("Rua das Flores", "123", "", "São Paulo", "SP", "01234567");
        assertEquals("Bairro é obrigatório", AddressValidator.getValidationMessage());
        
        // Test with invalid cidade
        AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "", "SP", "01234567");
        assertEquals("Cidade é obrigatória", AddressValidator.getValidationMessage());
        
        // Test with invalid estado
        AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "", "01234567");
        assertEquals("Estado é obrigatório", AddressValidator.getValidationMessage());
        
        // Test with invalid cep
        AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "123");
        assertEquals("CEP inválido", AddressValidator.getValidationMessage());
    }
}