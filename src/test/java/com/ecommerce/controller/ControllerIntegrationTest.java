package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.enums.UserRole;
import com.ecommerce.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ControllerIntegrationTest {

    @Autowired
    private UserController userController;
    
    @Autowired
    private OrderController orderController;
    
    @Autowired
    private AddressController addressController;

    @Test
    void testUserRegistration() {
        UserDto user = new UserDto(0L, "testuser", "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        var response = userController.register(user);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void testUserLogin() {
        // Primeiro registra um usuário
        UserDto user = new UserDto(0L, "testuser", "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        userController.register(user);
        
        // Depois tenta fazer login
        UserDto loginUser = new UserDto(0L, "testuser", null, null, 
            "ValidPass@123", null, null, null, null, null);
        
        var response = userController.login(loginUser);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddressValidation() {
        AddressDto validAddress = new AddressDto("Rua das Flores", "123", "Centro", 
            "São Paulo", "SP", "Apto 1", "01234-567");
        
        var response = addressController.createAddress(1L, validAddress);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddressValidationWithInvalidData() {
        AddressDto invalidAddress = new AddressDto("", "123", "Centro", 
            "São Paulo", "SP", "Apto 1", "01234-567");
        
        var response = addressController.createAddress(1L, invalidAddress);
        
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testOrderCreation() {
        OrderDto order = new OrderDto(1L, "PED123", OrderStatus.PREPARANDO.getDescription(), 
            new Date(), 100.0f, "Comentário", 0, 1L, List.of());
        
        var response = orderController.post(order);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void testOrderStatusUpdate() {
        var response = orderController.updateOrderStatus(1L, OrderStatus.SAINDO_PARA_ENTREGA.getDescription());
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody());
    }

    @Test
    void testOrderCancellation() {
        var response = orderController.cancelOrder(1L);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody());
    }

    @Test
    void testOrderEvaluation() {
        var response = orderController.evaluateOrder(1L, "Excelente entrega!", 5);
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody());
    }
}
