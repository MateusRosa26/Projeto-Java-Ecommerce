package com.ecommerce.service;

import com.ecommerce.enums.PaymentMethod;

import java.util.Random;

/**
 * Serviço para integração com sistemas externos de pagamento
 * Implementa RF06: Sistema deve se comunicar com um serviço externo para processar pagamentos
 */
public class ExternalPaymentService {
    
    private final Random random = new Random();
    
    public ExternalPaymentService() {
    }
    
    /**
     * Simula comunicação com sistema externo de pagamento
     */
    public PaymentResult processPayment(PaymentMethod method, double amount, String cardNumber, String cvv) {
        // Valida cartão se for método de cartão
        if (method == PaymentMethod.CARTAO_CREDITO || method == PaymentMethod.CARTAO_DEBITO) {
            if (!validateCard(cardNumber, cvv)) {
                throw new IllegalArgumentException("Cartão inválido: número ou CVV inválidos");
            }
        }
        
        // Simula delay de rede
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simula diferentes taxas de sucesso baseado no método
        double successRate = getSuccessRate(method);
        boolean isApproved = random.nextDouble() < successRate;
        
        String transactionId = generateTransactionId();
        String status = isApproved ? "APROVADO" : "REJEITADO";
        String message = isApproved ? "Pagamento aprovado com sucesso" : "Pagamento rejeitado";
        
        return new PaymentResult(transactionId, status, message, amount);
    }
    
    /**
     * Simula reprocessamento de pagamento
     */
    public PaymentResult retryPayment(String originalTransactionId, PaymentMethod method, double amount) {
        // Simula delay de rede
        try {
            Thread.sleep(1000 + random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Taxa de sucesso ligeiramente menor no reprocessamento
        double successRate = getSuccessRate(method) * 0.8;
        boolean isApproved = random.nextDouble() < successRate;
        
        String transactionId = generateTransactionId();
        String status = isApproved ? "APROVADO" : "REJEITADO";
        String message = isApproved ? "Repagamento aprovado com sucesso" : "Repagamento rejeitado";
        
        return new PaymentResult(transactionId, status, message, amount);
    }
    
    /**
     * Obtém taxa de sucesso baseada no método de pagamento
     */
    private double getSuccessRate(PaymentMethod method) {
        switch (method) {
            case PIX:
                return 0.95; // 95% de sucesso
            case CARTAO_CREDITO:
                return 0.90; // 90% de sucesso
            case CARTAO_DEBITO:
                return 0.85; // 85% de sucesso
            case BOLETO:
                return 0.99; // 99% de sucesso (boleto sempre é aceito)
            case TICKET:
                return 0.80; // 80% de sucesso
            case DINHEIRO:
                return 1.0; // 100% de sucesso (dinheiro sempre é aceito)
            default:
                return 0.70; // 70% de sucesso padrão
        }
    }
    
    /**
     * Gera ID de transação único
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + random.nextInt(1000);
    }
    
    /**
     * Valida dados do cartão
     */
    public boolean validateCard(String cardNumber, String cvv) {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        
        if (cvv == null || cvv.length() < 3 || cvv.length() > 4) {
            return false;
        }
        
        // Validação básica do algoritmo de Luhn
        return isValidLuhn(cardNumber);
    }
    
    /**
     * Implementa algoritmo de Luhn para validação de cartão
     */
    private boolean isValidLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        
        return (sum % 10) == 0;
    }
    
    /**
     * Classe para resultado do pagamento
     */
    public static class PaymentResult {
        private final String transactionId;
        private final String status;
        private final String message;
        private final double amount;
        
        public PaymentResult(String transactionId, String status, String message, double amount) {
            this.transactionId = transactionId;
            this.status = status;
            this.message = message;
            this.amount = amount;
        }
        
        public String getTransactionId() {
            return transactionId;
        }
        
        public String getStatus() {
            return status;
        }
        
        public String getMessage() {
            return message;
        }
        
        public double getAmount() {
            return amount;
        }
        
        public boolean isApproved() {
            return "APROVADO".equals(status);
        }
    }
}
