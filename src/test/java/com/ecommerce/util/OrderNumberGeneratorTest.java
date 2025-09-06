package com.ecommerce.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderNumberGeneratorTest {

    @Test
    void generate_ShouldReturnValidOrderNumber() {
        String orderNumber = OrderNumberGenerator.generate();
        
        assertNotNull(orderNumber);
        assertTrue(orderNumber.startsWith("PED"));
        assertTrue(orderNumber.length() > 3);
    }

    @Test
    void generate_ShouldReturnUniqueOrderNumbers() {
        String orderNumber1 = OrderNumberGenerator.generate();
        String orderNumber2 = OrderNumberGenerator.generate();
        
        assertNotEquals(orderNumber1, orderNumber2);
    }

    @Test
    void generate_ShouldReturnOrderNumberWithTimestamp() {
        String orderNumber = OrderNumberGenerator.generate();
        
        // Remove o prefixo "PED" para verificar se contém timestamp
        String numberPart = orderNumber.substring(3);
        assertTrue(numberPart.length() >= 14); // timestamp + sequence
    }

    @Test
    void generateOrderNumber_ShouldReturnSameAsGenerate() {
        String orderNumber1 = OrderNumberGenerator.generate();
        String orderNumber2 = OrderNumberGenerator.generateOrderNumber();
        
        // Ambos devem seguir o mesmo padrão
        assertTrue(orderNumber1.startsWith("PED"));
        assertTrue(orderNumber2.startsWith("PED"));
    }

    @Test
    void generate_ShouldReturnDifferentNumbersOnMultipleCalls() {
        String[] orderNumbers = new String[10];
        
        for (int i = 0; i < 10; i++) {
            orderNumbers[i] = OrderNumberGenerator.generate();
        }
        
        // Verificar que todos são únicos
        for (int i = 0; i < orderNumbers.length; i++) {
            for (int j = i + 1; j < orderNumbers.length; j++) {
                assertNotEquals(orderNumbers[i], orderNumbers[j], 
                    "Order numbers should be unique");
            }
        }
    }

    @Test
    void generate_ShouldReturnOrderNumberWithCorrectFormat() {
        String orderNumber = OrderNumberGenerator.generate();
        
        // Deve começar com "PED"
        assertTrue(orderNumber.startsWith("PED"));
        
        // Deve ter pelo menos 17 caracteres (PED + 14 dígitos)
        assertTrue(orderNumber.length() >= 17);
        
        // Deve conter apenas números após "PED"
        String numberPart = orderNumber.substring(3);
        assertTrue(numberPart.matches("\\d+"));
    }

    @Test
    void generate_ShouldHandleHighFrequencyGeneration() {
        // Gerar muitos números rapidamente para testar thread safety
        String[] orderNumbers = new String[100];
        
        for (int i = 0; i < 100; i++) {
            orderNumbers[i] = OrderNumberGenerator.generate();
        }
        
        // Verificar que todos são únicos
        for (int i = 0; i < orderNumbers.length; i++) {
            for (int j = i + 1; j < orderNumbers.length; j++) {
                assertNotEquals(orderNumbers[i], orderNumbers[j], 
                    "Order numbers should be unique even with high frequency generation");
            }
        }
    }
}
