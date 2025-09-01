package com.ecommerce.controller;

import com.ecommerce.business.ProductBusinessHandlerImpl;
import com.ecommerce.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController controller;

    @BeforeEach
    void setUp() {
        ProductBusinessHandlerImpl businessHandler = new ProductBusinessHandlerImpl();
        controller = new ProductController(businessHandler);
    }

    @Test
    void get_ShouldReturnResponseEntity() {
        ResponseEntity<List<ProductDto>> response = controller.get();
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void getById_ShouldReturnResponseEntity() {
        ResponseEntity<ProductDto> response = controller.get(1L);
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void post_ShouldReturnResponseEntity() {
        ProductDto product = new ProductDto(1L, "Test Product", 99.99f, "1234567890123", "Test Description", "test.jpg");
        ResponseEntity<ProductDto> response = controller.post(product);
        
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void put_ShouldReturnResponseEntity() {
        ProductDto product = new ProductDto(1L, "Updated Product", 149.99f, "1234567890124", "Updated Description", "updated.jpg");
        ResponseEntity<ProductDto> response = controller.put(1L, product);
        
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void delete_ShouldReturnResponseEntity() {
        ResponseEntity<Boolean> response = controller.delete(1L);
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody() != null && response.getBody());
    }

    @Test
    void addToCart_ShouldReturnResponseEntity() {
        ProductDto product = new ProductDto(1L, "Test Product", 99.99f, "1234567890125", "Test Description", "test.jpg");
        ResponseEntity<List<ProductDto>> response = controller.addToCart(product);
        
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void addToCart_ShouldHandleNullProduct() {
        ResponseEntity<List<ProductDto>> response = controller.addToCart(null);
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void search_ShouldReturnResponseEntity() {
        ResponseEntity<List<ProductDto>> response = controller.search("name", "test", false);
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }
}
