package com.ecommerce.util;

public class PasswordValidator {
    private static String lastValidationMessage = "";
    
    public static boolean isValid(String password) {
        if (password == null || password.length() < 8) {
            lastValidationMessage = "A senha deve ter pelo menos 8 caracteres";
            return false;
        }
        
        if (!password.matches(".*[A-Z].*")) {
            lastValidationMessage = "A senha deve conter pelo menos uma letra maiúscula";
            return false;
        }
        
        if (!password.matches(".*[a-z].*")) {
            lastValidationMessage = "A senha deve conter pelo menos uma letra minúscula";
            return false;
        }
        
        if (!password.matches(".*[0-9].*")) {
            lastValidationMessage = "A senha deve conter pelo menos um número";
            return false;
        }
        
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            lastValidationMessage = "A senha deve conter pelo menos um caractere especial";
            return false;
        }
        
        lastValidationMessage = "Senha válida";
        return true;
    }
    
    public static String getValidationMessage() {
        return lastValidationMessage;
    }
}
