package com.ecommerce.service;

import com.ecommerce.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ExternalPaymentServiceTest {

    private ExternalPaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new ExternalPaymentService();
    }

    @Test
    void processPayment_ShouldReturnPaymentResult() {
        ExternalPaymentService.PaymentResult result = paymentService.processPayment(
            PaymentMethod.PIX, 100.0, null, null);
        
        assertNotNull(result);
        assertNotNull(result.getTransactionId());
        assertNotNull(result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals(100.0, result.getAmount(), 0.01);
        assertTrue(result.getTransactionId().startsWith("TXN"));
    }

    @Test
    void processPayment_ShouldReturnDifferentResultsForDifferentMethods() {
        ExternalPaymentService.PaymentResult pixResult = paymentService.processPayment(
            PaymentMethod.PIX, 100.0, null, null);
        ExternalPaymentService.PaymentResult cardResult = paymentService.processPayment(
            PaymentMethod.CARTAO_CREDITO, 100.0, "4111111111111111", "123");
        
        assertNotEquals(pixResult.getTransactionId(), cardResult.getTransactionId());
    }

    @Test
    void retryPayment_ShouldReturnPaymentResult() {
        ExternalPaymentService.PaymentResult result = paymentService.retryPayment(
            "TXN123", PaymentMethod.PIX, 100.0);
        
        assertNotNull(result);
        assertNotNull(result.getTransactionId());
        assertNotNull(result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals(100.0, result.getAmount(), 0.01);
        assertTrue(result.getTransactionId().startsWith("TXN"));
    }

    @Test
    void validateCard_ShouldReturnTrue_WhenCardIsValid() {
        assertTrue(paymentService.validateCard("4111111111111111", "123")); // Visa test card
        assertTrue(paymentService.validateCard("5555555555554444", "123")); // Mastercard test card
        assertTrue(paymentService.validateCard("4532015112830366", "123")); // Valid Visa card
    }

    @Test
    void validateCard_ShouldReturnFalse_WhenCardNumberIsInvalid() {
        assertFalse(paymentService.validateCard("123", "123")); // Too short
        assertFalse(paymentService.validateCard("12345678901234567890", "123")); // Too long
        assertFalse(paymentService.validateCard("", "123")); // Empty
        assertFalse(paymentService.validateCard(null, "123")); // Null
    }

    @Test
    void validateCard_ShouldReturnFalse_WhenCvvIsInvalid() {
        assertFalse(paymentService.validateCard("1234567890123456", "12")); // Too short
        assertFalse(paymentService.validateCard("1234567890123456", "12345")); // Too long
        assertFalse(paymentService.validateCard("1234567890123456", "")); // Empty
        assertFalse(paymentService.validateCard("1234567890123456", null)); // Null
    }

    @Test
    void validateCard_ShouldReturnFalse_WhenCardFailsLuhnCheck() {
        assertFalse(paymentService.validateCard("1234567890123456", "123")); // Invalid Luhn
        assertFalse(paymentService.validateCard("1111111111111111", "123")); // Invalid Luhn
    }

    @Test
    void processPayment_ShouldValidateCard_WhenUsingCardPayment() {
        // Test with invalid card
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(PaymentMethod.CARTAO_CREDITO, 100.0, "123", "123");
        });
        
        // Test with valid card (should not throw exception)
        assertDoesNotThrow(() -> {
            paymentService.processPayment(PaymentMethod.CARTAO_CREDITO, 100.0, "4111111111111111", "123");
        });
    }

    @Test
    void processPayment_ShouldNotValidateCard_WhenUsingNonCardPayment() {
        // PIX should not require card validation
        assertDoesNotThrow(() -> {
            paymentService.processPayment(PaymentMethod.PIX, 100.0, null, null);
        });
        
        // Boleto should not require card validation
        assertDoesNotThrow(() -> {
            paymentService.processPayment(PaymentMethod.BOLETO, 100.0, null, null);
        });
        
        // Dinheiro should not require card validation
        assertDoesNotThrow(() -> {
            paymentService.processPayment(PaymentMethod.DINHEIRO, 100.0, null, null);
        });
    }

    @Test
    void paymentResult_ShouldHaveCorrectProperties() {
        ExternalPaymentService.PaymentResult result = new ExternalPaymentService.PaymentResult(
            "TXN123", "APROVADO", "Pagamento aprovado", 100.0);
        
        assertEquals("TXN123", result.getTransactionId());
        assertEquals("APROVADO", result.getStatus());
        assertEquals("Pagamento aprovado", result.getMessage());
        assertEquals(100.0, result.getAmount(), 0.01);
        assertTrue(result.isApproved());
    }

    @Test
    void paymentResult_ShouldReturnCorrectApprovalStatus() {
        ExternalPaymentService.PaymentResult approvedResult = new ExternalPaymentService.PaymentResult(
            "TXN123", "APROVADO", "Pagamento aprovado", 100.0);
        ExternalPaymentService.PaymentResult rejectedResult = new ExternalPaymentService.PaymentResult(
            "TXN124", "REJEITADO", "Pagamento rejeitado", 100.0);
        
        assertTrue(approvedResult.isApproved());
        assertFalse(rejectedResult.isApproved());
    }

    @Test
    void processPayment_ShouldHandleDifferentPaymentMethods() {
        PaymentMethod[] methods = {
            PaymentMethod.PIX,
            PaymentMethod.CARTAO_CREDITO,
            PaymentMethod.CARTAO_DEBITO,
            PaymentMethod.BOLETO,
            PaymentMethod.TICKET,
            PaymentMethod.DINHEIRO
        };
        
        for (PaymentMethod method : methods) {
            ExternalPaymentService.PaymentResult result = paymentService.processPayment(
                method, 100.0, "4111111111111111", "123");
            
            assertNotNull(result);
            assertNotNull(result.getTransactionId());
            assertNotNull(result.getStatus());
            assertTrue(result.getTransactionId().startsWith("TXN"));
        }
    }
}
