package com.ecommerce.business;

import com.ecommerce.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductBusinessHandlerImplTest {

    private ProductBusinessHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new ProductBusinessHandlerImpl();
    }

    @Test
    void listProducts_ShouldReturnList() {
        List<ProductDto> products = handler.listProducts();
        assertNotNull(products);
        // The list may or may not be empty depending on the CSV file content
        // This test just ensures the method returns a valid list
    }

    @Test
    void create_ShouldCreateProduct() {
        ProductDto product = new ProductDto(1L, "Test Product", 99.99f, "Test Description", "test.jpg", "1234567890123", true, new Date(), new Date());
        ProductDto created = handler.create(product);
        
        assertNotNull(created);
        assertEquals(product.id(), created.id());
        assertEquals(product.name(), created.name());
        assertEquals(product.price(), created.price());
    }

    @Test
    void find_ShouldReturnNull_WhenProductNotFound() {
        ProductDto found = handler.find(999L);
        assertNull(found);
    }

    @Test
    void listWithFilter_ShouldReturnFilteredResults() {
        // Criar alguns produtos primeiro
        handler.create(new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date()));
        handler.create(new ProductDto(2L, "Mouse", 89.99f, "Mouse óptico", "mouse.jpg", "1234567890124", true, new Date(), new Date()));
        handler.create(new ProductDto(3L, "Monitor", 599.99f, "Monitor LED", "monitor.jpg", "1234567890125", true, new Date(), new Date()));

        List<ProductDto> filtered = handler.list("nome", "teclado", false);
        assertNotNull(filtered);
        assertFalse(filtered.isEmpty());
        assertTrue(filtered.stream().anyMatch(p -> p.name().toLowerCase().contains("teclado")));
    }
}
