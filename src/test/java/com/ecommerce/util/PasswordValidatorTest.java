package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    void testValidPasswords() {
        // Senhas válidas
        assertTrue(PasswordValidator.isValid("MinhaSenh@123"));
        assertTrue(PasswordValidator.isValid("Teste123!"));
        assertTrue(PasswordValidator.isValid("Admin@2024"));
        assertTrue(PasswordValidator.isValid("User#456"));
        assertTrue(PasswordValidator.isValid("Pass$word789"));
    }

    @Test
    void testInvalidPasswords() {
        // Senhas inválidas - muito curtas
        assertFalse(PasswordValidator.isValid("1234567")); // 7 caracteres
        assertFalse(PasswordValidator.isValid("Abc@1")); // 5 caracteres
        
        // Senhas inválidas - sem maiúscula
        assertFalse(PasswordValidator.isValid("minhasenha@123"));
        assertFalse(PasswordValidator.isValid("teste123!"));
        
        // Senhas inválidas - sem minúscula
        assertFalse(PasswordValidator.isValid("MINHASENHA@123"));
        assertFalse(PasswordValidator.isValid("TESTE123!"));
        
        // Senhas inválidas - sem número
        assertFalse(PasswordValidator.isValid("MinhaSenha@"));
        assertFalse(PasswordValidator.isValid("Teste!"));
        
        // Senhas inválidas - sem caractere especial
        assertFalse(PasswordValidator.isValid("MinhaSenha123"));
        assertFalse(PasswordValidator.isValid("Teste123"));
        
        // Senhas inválidas - nulas ou vazias
        assertFalse(PasswordValidator.isValid(null));
        assertFalse(PasswordValidator.isValid(""));
        assertFalse(PasswordValidator.isValid("   "));
    }

    @Test
    void testValidationMessage() {
        String message = PasswordValidator.getValidationMessage();
        assertNotNull(message);
        assertTrue(message.contains("8 caracteres"));
        assertTrue(message.contains("maiúscula"));
        assertTrue(message.contains("minúscula"));
        assertTrue(message.contains("número"));
        assertTrue(message.contains("especial"));
    }
}
