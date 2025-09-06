package com.ecommerce.util;

public class AddressValidator {
    private static String lastValidationMessage = "";
    
    public static boolean isValidAddress(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        if (rua == null || rua.trim().isEmpty()) {
            lastValidationMessage = "Rua é obrigatória";
            return false;
        }
        
        if (numero == null || numero.trim().isEmpty()) {
            lastValidationMessage = "Número é obrigatório";
            return false;
        }
        
        if (bairro == null || bairro.trim().isEmpty()) {
            lastValidationMessage = "Bairro é obrigatório";
            return false;
        }
        
        if (cidade == null || cidade.trim().isEmpty()) {
            lastValidationMessage = "Cidade é obrigatória";
            return false;
        }
        
        if (estado == null || estado.trim().isEmpty()) {
            lastValidationMessage = "Estado é obrigatório";
            return false;
        }
        
        if (cep == null || !isValidCep(cep)) {
            lastValidationMessage = "CEP inválido";
            return false;
        }
        
        lastValidationMessage = "Endereço válido";
        return true;
    }
    
    private static boolean isValidCep(String cep) {
        // Verifica se contém apenas dígitos (sem espaços, hífens, etc.)
        if (!cep.matches("^\\d{8}$")) {
            return false;
        }
        
        // Verifica se não são todos os dígitos iguais
        return !cep.matches("(\\d)\\1{7}");
    }
    
    public static String getValidationMessage() {
        return lastValidationMessage;
    }
}
