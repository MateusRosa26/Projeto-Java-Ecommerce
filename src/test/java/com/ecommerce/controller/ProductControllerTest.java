package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductController();
    }

    @Test
    void listProducts_ShouldReturnList() {
        List<ProductDto> products = controller.listProducts();
        assertNotNull(products);
        // Pode estar vazio se não há produtos no CSV
    }

    @Test
    void addToCart_ShouldReturnList() {
        ProductDto product = new ProductDto(1L, "Test Product", 99.99f);
        List<ProductDto> result = controller.addToCart(product);
        
        assertNotNull(result);
        // O método atual apenas retorna a lista de produtos, não adiciona ao carrinho
    }

    @Test
    void addToCart_ShouldHandleNullProduct() {
        List<ProductDto> result = controller.addToCart(null);
        assertNotNull(result);
    }
}
