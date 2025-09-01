package com.ecommerce.business;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBusinessHandlerImplTest {

    private OrderBusinessHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new OrderBusinessHandlerImpl();
    }

    @Test
    void finishOrder_ShouldReturnTrue_WhenValidProducts() {
        List<ProductDto> products = Arrays.asList(
            new ProductDto(1L, "Teclado", 199.99f),
            new ProductDto(2L, "Mouse", 89.99f)
        );

        boolean result = handler.finishOrder(products);
        assertTrue(result);
    }

    @Test
    void finishOrder_ShouldReturnTrue_WhenEmptyList() {
        List<ProductDto> products = Arrays.asList();
        boolean result = handler.finishOrder(products);
        assertTrue(result);
    }

    @Test
    void evaluateOrder_ShouldReturnTrue_WhenValidParameters() {
        boolean result = handler.evaluateOrder(1L, "Ótimo produto!", 5L);
        assertTrue(result);
    }

    @Test
    void evaluateOrder_ShouldReturnTrue_WhenZeroRating() {
        boolean result = handler.evaluateOrder(1L, "Produto ruim", 1L);
        assertTrue(result);
    }

    @Test
    void create_ShouldReturnSameObject() {
        OrderDto order = new OrderDto(1L, "PED-001", new Date(), 299.98f, "Comentário", Arrays.asList());
        OrderDto created = handler.create(order);
        
        assertNotNull(created);
        assertEquals(order.id(), created.id());
        assertEquals(order.numero(), created.numero());
    }
}
