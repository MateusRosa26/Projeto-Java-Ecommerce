package com.ecommerce.util;

import java.util.regex.Pattern;

public class AddressValidator {
    
    private static final String CEP_PATTERN = "^\\d{5}-\\d{3}$";
    private static final String UF_PATTERN = "^[A-Z]{2}$";
    
    private static final Pattern cepPattern = Pattern.compile(CEP_PATTERN);
    private static final Pattern ufPattern = Pattern.compile(UF_PATTERN);
    
    public static boolean isValidCep(String cep) {
        if (cep == null || cep.isEmpty()) {
            return false;
        }
        return cepPattern.matcher(cep).matches();
    }
    
    public static boolean isValidUf(String uf) {
        if (uf == null || uf.isEmpty()) {
            return false;
        }
        return ufPattern.matcher(uf).matches();
    }
    
    public static boolean isValidAddress(String rua, String numero, String bairro, 
                                       String cidade, String estado, String cep) {
        return rua != null && !rua.trim().isEmpty() &&
               numero != null && !numero.trim().isEmpty() &&
               bairro != null && !bairro.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty() &&
               isValidUf(estado) &&
               isValidCep(cep);
    }
    
    public static String getValidationMessage() {
        return "Endereço inválido. Campos obrigatórios: rua, número, bairro, cidade, estado (UF) e CEP (00000-000)";
    }
}
