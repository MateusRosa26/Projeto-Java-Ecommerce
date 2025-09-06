package com.ecommerce.service;

import com.ecommerce.enums.PaymentMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Serviço para comunicação com gateway de pagamento externo
 * Simula integração com serviços como PagSeguro, Mercado Pago, etc.
 */
@Service
public class ExternalPaymentService {
    
    private final Random random = new Random();
    
    /**
     * Processa pagamento via gateway externo
     */
    public PaymentResult processPayment(PaymentMethod method, double amount, PaymentCardInfo cardInfo) {
        System.out.println("🌐 Comunicando com gateway de pagamento externo...");
        System.out.println("💳 Método: " + method.getDescription());
        System.out.println("💰 Valor: R$ " + String.format("%.2f", amount));
        
        // Simular latência de rede
        simulateNetworkLatency();
        
        // Simular diferentes taxas de sucesso por gateway
        boolean success = simulateGatewayResponse(method, amount);
        
        if (success) {
            String transactionId = generateTransactionId();
            System.out.println("✅ Pagamento aprovado pelo gateway externo");
            System.out.println("🆔 ID da transação: " + transactionId);
            
            return new PaymentResult(
                true,
                "Aprovado",
                transactionId,
                "Pagamento processado com sucesso",
                LocalDateTime.now()
            );
        } else {
            String errorCode = generateErrorCode();
            System.out.println("❌ Pagamento rejeitado pelo gateway externo");
            System.out.println("🚫 Código do erro: " + errorCode);
            
            return new PaymentResult(
                false,
                "Rejeitado",
                null,
                "Pagamento rejeitado: " + getErrorMessage(errorCode),
                LocalDateTime.now()
            );
        }
    }
    
    /**
     * Verifica status de uma transação
     */
    public PaymentResult checkTransactionStatus(String transactionId) {
        System.out.println("🔍 Verificando status da transação: " + transactionId);
        
        simulateNetworkLatency();
        
        // Simular diferentes status
        String[] statuses = {"Aprovado", "Processando", "Rejeitado", "Cancelado"};
        String status = statuses[random.nextInt(statuses.length)];
        
        return new PaymentResult(
            "Aprovado".equals(status),
            status,
            transactionId,
            "Status verificado com sucesso",
            LocalDateTime.now()
        );
    }
    
    /**
     * Cancela uma transação
     */
    public PaymentResult cancelTransaction(String transactionId) {
        System.out.println("❌ Cancelando transação: " + transactionId);
        
        simulateNetworkLatency();
        
        boolean success = random.nextDouble() < 0.9; // 90% de chance de sucesso
        
        if (success) {
            System.out.println("✅ Transação cancelada com sucesso");
            return new PaymentResult(
                true,
                "Cancelado",
                transactionId,
                "Transação cancelada com sucesso",
                LocalDateTime.now()
            );
        } else {
            System.out.println("❌ Falha ao cancelar transação");
            return new PaymentResult(
                false,
                "Erro",
                transactionId,
                "Não foi possível cancelar a transação",
                LocalDateTime.now()
            );
        }
    }
    
    /**
     * Simula latência de rede
     */
    private void simulateNetworkLatency() {
        try {
            // Simular latência de 500ms a 2s
            Thread.sleep(500 + random.nextInt(1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Simula resposta do gateway baseada no método e valor
     */
    private boolean simulateGatewayResponse(PaymentMethod method, double amount) {
        double baseSuccessRate = switch (method) {
            case PIX -> 0.98; // PIX tem alta taxa de sucesso
            case CARTAO_CREDITO -> 0.92; // Cartão de crédito
            case CARTAO_DEBITO -> 0.90; // Cartão de débito
            case BOLETO -> 0.85; // Boleto
            case TICKET -> 0.80; // Ticket
            case DINHEIRO -> 0.99; // Dinheiro sempre funciona
        };
        
        // Reduzir taxa de sucesso para valores muito altos
        if (amount > 5000) {
            baseSuccessRate *= 0.8;
        }
        
        // Reduzir taxa de sucesso para valores muito baixos (possível fraude)
        if (amount < 1) {
            baseSuccessRate *= 0.5;
        }
        
        return random.nextDouble() < baseSuccessRate;
    }
    
    /**
     * Gera ID único de transação
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + random.nextInt(1000);
    }
    
    /**
     * Gera código de erro
     */
    private String generateErrorCode() {
        String[] errorCodes = {
            "INSUFFICIENT_FUNDS",
            "INVALID_CARD",
            "EXPIRED_CARD",
            "BLOCKED_CARD",
            "GATEWAY_TIMEOUT",
            "INVALID_AMOUNT",
            "FRAUD_DETECTED"
        };
        return errorCodes[random.nextInt(errorCodes.length)];
    }
    
    /**
     * Retorna mensagem de erro baseada no código
     */
    private String getErrorMessage(String errorCode) {
        return switch (errorCode) {
            case "INSUFFICIENT_FUNDS" -> "Saldo insuficiente";
            case "INVALID_CARD" -> "Cartão inválido";
            case "EXPIRED_CARD" -> "Cartão expirado";
            case "BLOCKED_CARD" -> "Cartão bloqueado";
            case "GATEWAY_TIMEOUT" -> "Timeout do gateway";
            case "INVALID_AMOUNT" -> "Valor inválido";
            case "FRAUD_DETECTED" -> "Transação suspeita detectada";
            default -> "Erro desconhecido";
        };
    }
    
    /**
     * Classe para resultado do pagamento
     */
    public static class PaymentResult {
        private final boolean success;
        private final String status;
        private final String transactionId;
        private final String message;
        private final LocalDateTime timestamp;
        
        public PaymentResult(boolean success, String status, String transactionId, String message, LocalDateTime timestamp) {
            this.success = success;
            this.status = status;
            this.transactionId = transactionId;
            this.message = message;
            this.timestamp = timestamp;
        }
        
        // Getters
        public boolean isSuccess() { return success; }
        public String getStatus() { return status; }
        public String getTransactionId() { return transactionId; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    /**
     * Classe para informações do cartão
     */
    public static class PaymentCardInfo {
        private final String cardName;
        private final Long cardNumber;
        private final Integer month;
        private final Integer year;
        private final String cvv;
        
        public PaymentCardInfo(String cardName, Long cardNumber, Integer month, Integer year, String cvv) {
            this.cardName = cardName;
            this.cardNumber = cardNumber;
            this.month = month;
            this.year = year;
            this.cvv = cvv;
        }
        
        // Getters
        public String getCardName() { return cardName; }
        public Long getCardNumber() { return cardNumber; }
        public Integer getMonth() { return month; }
        public Integer getYear() { return year; }
        public String getCvv() { return cvv; }
    }
}
