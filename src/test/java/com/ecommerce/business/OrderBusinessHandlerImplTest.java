package com.ecommerce.business;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

class OrderBusinessHandlerImplTest {

    private OrderBusinessHandlerImpl orderHandler;

    @BeforeEach
    void setUp() {
        orderHandler = new OrderBusinessHandlerImpl();
    }

    @Test
    void testCreateOrder() {
        OrderDto order = new OrderDto(1L, "PED123", "Preparando", new Date(), 100.0f, 
            "Comentário", 0, 1L, List.of());
        
        OrderDto createdOrder = orderHandler.create(order);
        
        assertNotNull(createdOrder);
        assertTrue(createdOrder.numero().startsWith("PED"));
        assertEquals(OrderStatus.PREPARANDO.getDescription(), createdOrder.status());
        assertEquals(100.0f, createdOrder.total());
        assertEquals(1L, createdOrder.idCliente());
    }

    @Test
    void testFinishOrder() {
        ProductDto product1 = new ProductDto(1L, "Produto 1", 50.0f, "1234567890123", "Descrição 1", "produto1.jpg");
        ProductDto product2 = new ProductDto(2L, "Produto 2", 30.0f, "1234567890124", "Descrição 2", "produto2.jpg");
        
        List<ProductDto> products = List.of(product1, product2);
        
        boolean finished = orderHandler.finishOrder(products);
        
        assertTrue(finished);
    }

    @Test
    void testEvaluateOrder() {
        boolean evaluated = orderHandler.evaluateOrder(1L, "Excelente entrega!", 5L);
        
        assertTrue(evaluated);
    }

    @Test
    void testUpdateOrderStatus() {
        boolean updated = orderHandler.updateOrderStatus(1L, OrderStatus.SAINDO_PARA_ENTREGA.getDescription());
        
        assertTrue(updated);
    }

    @Test
    void testCancelOrder() {
        boolean cancelled = orderHandler.cancelOrder(1L);
        
        assertTrue(cancelled);
    }

    @Test
    void testGetOrdersByStatus() {
        List<OrderDto> orders = orderHandler.getOrdersByStatus(OrderStatus.PREPARANDO.getDescription());
        
        assertNotNull(orders);
        // Como é implementação básica, retorna lista vazia
        assertTrue(orders.isEmpty());
    }

    @Test
    void testOrderNumberGeneration() {
        OrderDto order1 = new OrderDto(1L, "PED123", "Preparando", new Date(), 100.0f, 
            "Comentário", 0, 1L, List.of());
        OrderDto order2 = new OrderDto(2L, "PED124", "Preparando", new Date(), 200.0f, 
            "Comentário", 0, 2L, List.of());
        
        OrderDto createdOrder1 = orderHandler.create(order1);
        OrderDto createdOrder2 = orderHandler.create(order2);
        
        // Verifica se os números são diferentes
        assertNotEquals(createdOrder1.numero(), createdOrder2.numero());
        
        // Verifica se começam com PED
        assertTrue(createdOrder1.numero().startsWith("PED"));
        assertTrue(createdOrder2.numero().startsWith("PED"));
    }

    @Test
    void testOrderStatusTransitions() {
        // Testa transições de status
        assertTrue(orderHandler.updateOrderStatus(1L, OrderStatus.PREPARANDO.getDescription()));
        assertTrue(orderHandler.updateOrderStatus(1L, OrderStatus.SAINDO_PARA_ENTREGA.getDescription()));
        assertTrue(orderHandler.updateOrderStatus(1L, OrderStatus.ENTREGUE.getDescription()));
        assertTrue(orderHandler.updateOrderStatus(1L, OrderStatus.CANCELADO.getDescription()));
    }
}