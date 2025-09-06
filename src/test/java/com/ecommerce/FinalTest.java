package com.ecommerce;

import com.ecommerce.util.PasswordValidator;
import com.ecommerce.util.AddressValidator;
import com.ecommerce.util.OrderNumberGenerator;
import com.ecommerce.enums.UserRole;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.business.OrderBusinessHandlerImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Teste final para verificar se todas as funcionalidades estão funcionando
 */
class FinalTest {

    @Test
    void testAllValidations() {
        // Testa validação de senhas
        assertTrue(PasswordValidator.isValid("MinhaSenh@123"));
        assertFalse(PasswordValidator.isValid("123456"));
        
        // Testa validação de endereços
        assertTrue(AddressValidator.isValidAddress("Rua Teste", "123", "Centro", "São Paulo", "SP", "01234-567"));
        assertFalse(AddressValidator.isValidAddress("", "123", "Centro", "São Paulo", "SP", "01234-567"));
    }

    @Test
    void testOrderNumberGeneration() {
        String order1 = OrderNumberGenerator.generateOrderNumber();
        String order2 = OrderNumberGenerator.generateOrderNumber();
        
        assertNotEquals(order1, order2);
        assertTrue(order1.startsWith("PED"));
        assertTrue(order2.startsWith("PED"));
    }

    @Test
    void testEnums() {
        // Testa UserRole
        assertEquals("Cliente", UserRole.CLIENTE.getDescription());
        assertEquals(1, UserRole.CLIENTE.getCode());
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(1));
        assertEquals(UserRole.CLIENTE, UserRole.fromString("Cliente"));
        
        // Testa OrderStatus
        assertEquals("Preparando", OrderStatus.PREPARANDO.getDescription());
        assertEquals(1, OrderStatus.PREPARANDO.getCode());
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromCode(1));
        assertEquals(OrderStatus.PREPARANDO, OrderStatus.fromString("Preparando"));
        
        // Testa PaymentMethod
        assertEquals("PIX", PaymentMethod.PIX.getDescription());
        assertEquals(4, PaymentMethod.PIX.getCode());
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromCode(4));
        assertEquals(PaymentMethod.PIX, PaymentMethod.fromString("PIX"));
    }

    @Test
    void testProductDto() {
        ProductDto product = new ProductDto(1L, "Produto Teste", 99.99f, "1234567890123", "Descrição do produto", "produto.jpg");
        
        assertEquals(1L, product.id());
        assertEquals("Produto Teste", product.name());
        assertEquals(99.99f, product.price());
    }

    @Test
    void testOrderBusinessHandler() {
        OrderBusinessHandlerImpl orderHandler = new OrderBusinessHandlerImpl();
        
        // Testa criação de pedido
        ProductDto product = new ProductDto(1L, "Produto Teste", 50.0f, "1234567890124", "Descrição do produto", "produto2.jpg");
        List<ProductDto> products = List.of(product);
        
        assertTrue(orderHandler.finishOrder(products));
        assertTrue(orderHandler.evaluateOrder(1L, "Excelente!", 5L));
        assertTrue(orderHandler.updateOrderStatus(1L, "Entregue"));
        assertTrue(orderHandler.cancelOrder(1L));
    }

    @Test
    void testOrderNumberUniqueness() {
        // Gera 10 números e verifica se são únicos
        String[] orders = new String[10];
        for (int i = 0; i < 10; i++) {
            orders[i] = OrderNumberGenerator.generateOrderNumber();
        }
        
        // Verifica se todos são únicos
        for (int i = 0; i < orders.length; i++) {
            for (int j = i + 1; j < orders.length; j++) {
                assertNotEquals(orders[i], orders[j], 
                    "Números de pedido devem ser únicos: " + orders[i] + " == " + orders[j]);
            }
        }
    }

    @Test
    void testPasswordValidationEdgeCases() {
        // Senhas válidas
        assertTrue(PasswordValidator.isValid("MinhaSenh@123"));
        assertTrue(PasswordValidator.isValid("Teste123!"));
        assertTrue(PasswordValidator.isValid("Admin@2024"));
        
        // Senhas inválidas
        assertFalse(PasswordValidator.isValid("1234567")); // 7 caracteres
        assertFalse(PasswordValidator.isValid("minhasenha@123")); // sem maiúscula
        assertFalse(PasswordValidator.isValid("MINHASENHA@123")); // sem minúscula
        assertFalse(PasswordValidator.isValid("MinhaSenha@")); // sem número
        assertFalse(PasswordValidator.isValid("MinhaSenha123")); // sem especial
        assertFalse(PasswordValidator.isValid("")); // vazia
        assertFalse(PasswordValidator.isValid(null)); // nula
    }

    @Test
    void testAddressValidationEdgeCases() {
        // Endereços válidos
        assertTrue(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567"));
        assertTrue(AddressValidator.isValidAddress("Avenida Paulista", "1000", "Bela Vista", "São Paulo", "SP", "01310-100"));
        
        // Endereços inválidos
        assertFalse(AddressValidator.isValidAddress("", "123", "Centro", "São Paulo", "SP", "01234-567")); // rua vazia
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "", "Centro", "São Paulo", "SP", "01234-567")); // número vazio
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "", "São Paulo", "SP", "01234-567")); // bairro vazio
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "", "SP", "01234-567")); // cidade vazia
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "São Paulo", "01234-567")); // UF inválida
        assertFalse(AddressValidator.isValidAddress("Rua das Flores", "123", "Centro", "São Paulo", "SP", "12345678")); // CEP inválido
    }
}
