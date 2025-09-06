package com.ecommerce.business;

import com.ecommerce.dto.PaymentDto;
import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.service.ExternalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentBusinessHandlerImpl implements BusinessHandler<PaymentDto> {
    
    private final ExternalPaymentService externalPaymentService;
    
    // Armazenamento temporário de pagamentos (em produção seria um banco de dados)
    private static final Map<Long, PaymentDto> payments = new ConcurrentHashMap<>();
    private static long nextPaymentId = 1L;
    
    @Autowired
    public PaymentBusinessHandlerImpl(ExternalPaymentService externalPaymentService) {
        this.externalPaymentService = externalPaymentService;
    }
    
    @Override
    public PaymentDto create(PaymentDto object) {
        if (object != null) {
            PaymentDto newPayment = new PaymentDto(
                nextPaymentId++,
                object.metodo(),
                "Processando",
                LocalDateTime.now(),
                1,
                object.nomeCartao(),
                object.numeroCartao(),
                object.mesValidade(),
                object.anoValidade()
            );
            payments.put(newPayment.id(), newPayment);
            System.out.println("💳 Pagamento criado: " + newPayment.metodo());
            return newPayment;
        }
        return null;
    }

    @Override
    public PaymentDto update(PaymentDto object, long id) {
        if (payments.containsKey(id)) {
            payments.put(id, object);
            return object;
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return payments.remove(id) != null;
    }

    @Override
    public PaymentDto find(long id) {
        return payments.get(id);
    }

    @Override
    public List<PaymentDto> list() {
        return new ArrayList<>(payments.values());
    }

    @Override
    public List<PaymentDto> list(String field, String filter, boolean exact) {
        return payments.values().stream()
            .filter(payment -> {
                switch (field.toLowerCase()) {
                    case "metodo":
                        return exact ? payment.metodo().equals(filter)
                                   : payment.metodo().toLowerCase().contains(filter.toLowerCase());
                    case "status":
                        return exact ? payment.status().equals(filter)
                                   : payment.status().toLowerCase().contains(filter.toLowerCase());
                    case "nomecartao":
                        return exact ? payment.nomeCartao().equals(filter)
                                   : payment.nomeCartao().toLowerCase().contains(filter.toLowerCase());
                    default:
                        return false;
                }
            })
            .toList();
    }
    
    /**
     * Processa um pagamento
     */
    public PaymentDto processPayment(PaymentMethod method, double amount, String cardName, 
                                   Long cardNumber, Integer month, Integer year) {
        
        // Validações básicas
        if (amount <= 0) {
            System.out.println("❌ Valor do pagamento deve ser maior que zero");
            return null;
        }
        
        if (method == PaymentMethod.CARTAO_CREDITO || method == PaymentMethod.CARTAO_DEBITO) {
            if (cardName == null || cardName.trim().isEmpty()) {
                System.out.println("❌ Nome do cartão é obrigatório");
                return null;
            }
            if (cardNumber == null || cardNumber.toString().length() < 13) {
                System.out.println("❌ Número do cartão inválido");
                return null;
            }
            if (month == null || month < 1 || month > 12) {
                System.out.println("❌ Mês de validade inválido");
                return null;
            }
            if (year == null || year < LocalDateTime.now().getYear()) {
                System.out.println("❌ Ano de validade inválido");
                return null;
            }
        }
        
        PaymentDto payment = new PaymentDto(
            nextPaymentId++,
            method.getDescription(),
            "Processando",
            LocalDateTime.now(),
            1,
            cardName,
            cardNumber,
            month,
            year
        );
        
        payments.put(payment.id(), payment);
        
        // Processar via serviço externo
        ExternalPaymentService.PaymentCardInfo cardInfo = null;
        if (method == PaymentMethod.CARTAO_CREDITO || method == PaymentMethod.CARTAO_DEBITO) {
            cardInfo = new ExternalPaymentService.PaymentCardInfo(
                cardName, cardNumber, month, year, "***"
            );
        }
        
        ExternalPaymentService.PaymentResult result = externalPaymentService.processPayment(method, amount, cardInfo);
        
        if (result.isSuccess()) {
            PaymentDto approvedPayment = new PaymentDto(
                payment.id(),
                payment.metodo(),
                "Aprovado",
                payment.dataPagamento(),
                payment.tentativas(),
                payment.nomeCartao(),
                payment.numeroCartao(),
                payment.mesValidade(),
                payment.anoValidade()
            );
            payments.put(payment.id(), approvedPayment);
            System.out.println("✅ Pagamento aprovado: " + method.getDescription() + " - R$ " + String.format("%.2f", amount));
            System.out.println("🆔 ID da transação: " + result.getTransactionId());
            return approvedPayment;
        } else {
            PaymentDto rejectedPayment = new PaymentDto(
                payment.id(),
                payment.metodo(),
                "Rejeitado",
                payment.dataPagamento(),
                payment.tentativas(),
                payment.nomeCartao(),
                payment.numeroCartao(),
                payment.mesValidade(),
                payment.anoValidade()
            );
            payments.put(payment.id(), rejectedPayment);
            System.out.println("❌ Pagamento rejeitado: " + method.getDescription() + " - R$ " + String.format("%.2f", amount));
            System.out.println("🚫 Motivo: " + result.getMessage());
            return rejectedPayment;
        }
    }
    
    
    /**
     * Reprocessa um pagamento falhado
     */
    public PaymentDto retryPayment(Long paymentId, PaymentMethod newMethod) {
        PaymentDto originalPayment = payments.get(paymentId);
        if (originalPayment == null) {
            System.out.println("❌ Pagamento não encontrado");
            return null;
        }
        
        if (!"Rejeitado".equals(originalPayment.status())) {
            System.out.println("❌ Pagamento não pode ser reprocessado (status: " + originalPayment.status() + ")");
            return null;
        }
        
        // Incrementar tentativas
        PaymentDto retryPayment = new PaymentDto(
            originalPayment.id(),
            newMethod.getDescription(),
            "Processando",
            LocalDateTime.now(),
            originalPayment.tentativas() + 1,
            originalPayment.nomeCartao(),
            originalPayment.numeroCartao(),
            originalPayment.mesValidade(),
            originalPayment.anoValidade()
        );
        
        payments.put(paymentId, retryPayment);
        
        // Reprocessar via serviço externo
        ExternalPaymentService.PaymentCardInfo cardInfo = null;
        if (newMethod == PaymentMethod.CARTAO_CREDITO || newMethod == PaymentMethod.CARTAO_DEBITO) {
            cardInfo = new ExternalPaymentService.PaymentCardInfo(
                retryPayment.nomeCartao(), retryPayment.numeroCartao(), 
                retryPayment.mesValidade(), retryPayment.anoValidade(), "***"
            );
        }
        
        ExternalPaymentService.PaymentResult result = externalPaymentService.processPayment(newMethod, 100.0, cardInfo);
        
        if (result.isSuccess()) {
            PaymentDto approvedPayment = new PaymentDto(
                retryPayment.id(),
                retryPayment.metodo(),
                "Aprovado",
                retryPayment.dataPagamento(),
                retryPayment.tentativas(),
                retryPayment.nomeCartao(),
                retryPayment.numeroCartao(),
                retryPayment.mesValidade(),
                retryPayment.anoValidade()
            );
            payments.put(paymentId, approvedPayment);
            System.out.println("✅ Pagamento reprocessado com sucesso: " + newMethod.getDescription());
            System.out.println("🆔 ID da transação: " + result.getTransactionId());
            return approvedPayment;
        } else {
            PaymentDto rejectedPayment = new PaymentDto(
                retryPayment.id(),
                retryPayment.metodo(),
                "Rejeitado",
                retryPayment.dataPagamento(),
                retryPayment.tentativas(),
                retryPayment.nomeCartao(),
                retryPayment.numeroCartao(),
                retryPayment.mesValidade(),
                retryPayment.anoValidade()
            );
            payments.put(paymentId, rejectedPayment);
            System.out.println("❌ Pagamento reprocessado falhou: " + newMethod.getDescription());
            System.out.println("🚫 Motivo: " + result.getMessage());
            return rejectedPayment;
        }
    }
    
    /**
     * Obtém pagamentos por status
     */
    public List<PaymentDto> getPaymentsByStatus(String status) {
        return payments.values().stream()
            .filter(payment -> payment.status().equals(status))
            .toList();
    }
    
    /**
     * Obtém pagamentos por método
     */
    public List<PaymentDto> getPaymentsByMethod(String method) {
        return payments.values().stream()
            .filter(payment -> payment.metodo().equals(method))
            .toList();
    }
    
    /**
     * Calcula estatísticas de pagamento
     */
    public Map<String, Object> getPaymentStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalPayments = payments.size();
        long approvedPayments = payments.values().stream()
            .mapToLong(p -> "Aprovado".equals(p.status()) ? 1 : 0)
            .sum();
        long rejectedPayments = payments.values().stream()
            .mapToLong(p -> "Rejeitado".equals(p.status()) ? 1 : 0)
            .sum();
        
        stats.put("total", totalPayments);
        stats.put("approved", approvedPayments);
        stats.put("rejected", rejectedPayments);
        stats.put("approvalRate", totalPayments > 0 ? (double) approvedPayments / totalPayments : 0.0);
        
        return stats;
    }
}
