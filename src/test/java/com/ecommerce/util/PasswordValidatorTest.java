package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    void isValid_ShouldReturnTrue_WhenPasswordIsValid() {
        assertTrue(PasswordValidator.isValid("Password123!"));
        assertTrue(PasswordValidator.isValid("MySecure1@"));
        assertTrue(PasswordValidator.isValid("Test123#"));
        assertTrue(PasswordValidator.isValid("ValidPass9$"));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordIsTooShort() {
        assertFalse(PasswordValidator.isValid("Pass1!"));
        assertFalse(PasswordValidator.isValid("Ab1@"));
        assertFalse(PasswordValidator.isValid(""));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasNoUppercase() {
        assertFalse(PasswordValidator.isValid("password123!"));
        assertFalse(PasswordValidator.isValid("mypassword1@"));
        assertFalse(PasswordValidator.isValid("test123#"));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasNoLowercase() {
        assertFalse(PasswordValidator.isValid("PASSWORD123!"));
        assertFalse(PasswordValidator.isValid("MYPASSWORD1@"));
        assertFalse(PasswordValidator.isValid("TEST123#"));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasNoNumber() {
        assertFalse(PasswordValidator.isValid("Password!"));
        assertFalse(PasswordValidator.isValid("MySecure@"));
        assertFalse(PasswordValidator.isValid("TestPassword#"));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasNoSpecialChar() {
        assertFalse(PasswordValidator.isValid("Password123"));
        assertFalse(PasswordValidator.isValid("MySecure1"));
        assertFalse(PasswordValidator.isValid("Test123"));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordIsNull() {
        assertFalse(PasswordValidator.isValid(null));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordIsEmpty() {
        assertFalse(PasswordValidator.isValid(""));
        assertFalse(PasswordValidator.isValid("   "));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasOnlySpaces() {
        assertFalse(PasswordValidator.isValid("        "));
    }

    @Test
    void isValid_ShouldReturnTrue_WhenPasswordHasMinimumRequirements() {
        assertTrue(PasswordValidator.isValid("A1!bcdefg")); // 8 chars, uppercase, lowercase, number, special
        assertTrue(PasswordValidator.isValid("Z9@hijklm")); // 8 chars, uppercase, lowercase, number, special
    }

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordHasOnlyOneType() {
        assertFalse(PasswordValidator.isValid("abcdefgh")); // only lowercase
        assertFalse(PasswordValidator.isValid("ABCDEFGH")); // only uppercase
        assertFalse(PasswordValidator.isValid("12345678")); // only numbers
        assertFalse(PasswordValidator.isValid("!@#$%^&*")); // only special chars
    }
}
