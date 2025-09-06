package com.ecommerce.controller;

import com.ecommerce.business.ProductBusinessHandlerImpl;
import com.ecommerce.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductController controller;
    private ProductBusinessHandlerImpl mockHandler;

    @BeforeEach
    void setUp() {
        mockHandler = mock(ProductBusinessHandlerImpl.class);
        controller = new ProductController(mockHandler);
    }

    @Test
    void get_ShouldReturnAllProducts() {
        // Arrange
        List<ProductDto> products = List.of(
            new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date()),
            new ProductDto(2L, "Mouse", 89.99f, "Mouse óptico", "mouse.jpg", "1234567890124", true, new Date(), new Date())
        );
        when(mockHandler.listProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDto>> response = controller.get();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(mockHandler).listProducts();
    }

    @Test
    void get_ShouldReturnProduct_WhenIdExists() {
        // Arrange
        Long id = 1L;
        ProductDto product = new ProductDto(id, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        when(mockHandler.find(id)).thenReturn(product);

        // Act
        ResponseEntity<ProductDto> response = controller.get(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        verify(mockHandler).find(id);
    }

    @Test
    void get_ShouldReturnNotFound_WhenIdDoesNotExist() {
        // Arrange
        Long id = 999L;
        when(mockHandler.find(id)).thenReturn(null);

        // Act
        ResponseEntity<ProductDto> response = controller.get(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(mockHandler).find(id);
    }

    @Test
    void post_ShouldReturnCreatedProduct() {
        // Arrange
        ProductDto product = new ProductDto(0L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        ProductDto created = new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        when(mockHandler.create(product)).thenReturn(created);

        // Act
        ResponseEntity<ProductDto> response = controller.post(product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        verify(mockHandler).create(product);
    }

    @Test
    void put_ShouldReturnUpdatedProduct() {
        // Arrange
        Long id = 1L;
        ProductDto product = new ProductDto(id, "Teclado Atualizado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        when(mockHandler.update(product, id)).thenReturn(product);

        // Act
        ResponseEntity<ProductDto> response = controller.put(id, product);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Teclado Atualizado", response.getBody().name());
        verify(mockHandler).update(product, id);
    }

    @Test
    void delete_ShouldReturnTrue_WhenProductExists() {
        // Arrange
        Long id = 1L;
        when(mockHandler.delete(id)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).delete(id);
    }

    @Test
    void delete_ShouldReturnFalse_WhenProductDoesNotExist() {
        // Arrange
        Long id = 999L;
        when(mockHandler.delete(id)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(mockHandler).delete(id);
    }

    @Test
    void addToCart_ShouldReturnSuccess_WhenValidRequest() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("productCode", "1234567890123");
        request.put("quantity", 2);
        
        ProductDto product = new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        when(mockHandler.findByBarcode("1234567890123")).thenReturn(product);

        // Act
        ResponseEntity<String> response = controller.addToCart(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto adicionado ao carrinho com sucesso", response.getBody());
        verify(mockHandler).findByBarcode("1234567890123");
    }

    @Test
    void addToCart_ShouldReturnBadRequest_WhenInvalidRequest() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("productCode", "1234567890123");
        // quantity is missing

        // Act
        ResponseEntity<String> response = controller.addToCart(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Código do produto e quantidade são obrigatórios"));
    }

    @Test
    void addToCart_ShouldReturnNotFound_WhenProductNotFound() {
        // Arrange
        Map<String, Object> request = new HashMap<>();
        request.put("productCode", "9999999999999");
        request.put("quantity", 1);
        
        when(mockHandler.findByBarcode("9999999999999")).thenReturn(null);

        // Act
        ResponseEntity<String> response = controller.addToCart(request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(mockHandler).findByBarcode("9999999999999");
    }

    @Test
    void search_ShouldReturnFilteredProducts() {
        // Arrange
        List<ProductDto> products = List.of(
            new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date())
        );
        when(mockHandler.list("name", "teclado", false)).thenReturn(products);

        // Act
        ResponseEntity<List<ProductDto>> response = controller.search("name", "teclado", false);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mockHandler).list("name", "teclado", false);
    }
}
