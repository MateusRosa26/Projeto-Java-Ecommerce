package com.ecommerce.util;

import java.util.regex.Pattern;

public class PasswordValidator {
    
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
    
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    
    public static boolean isValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
    
    public static String getValidationMessage() {
        return "A senha deve ter pelo menos 8 caracteres, incluindo: " +
               "uma letra minúscula, uma maiúscula, um número e um caractere especial (@$!%*?&)";
    }
}
