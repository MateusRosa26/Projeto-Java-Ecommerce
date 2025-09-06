package com.ecommerce.business;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.PaymentDto;
import com.ecommerce.dto.ItemPedidoDto;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
            new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date()),
            new ProductDto(2L, "Mouse", 89.99f, "Mouse óptico", "mouse.jpg", "1234567890124", true, new Date(), new Date())
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
        // Primeiro criar um pedido entregue
        Date now = new Date();
        List<ProductDto> products = new ArrayList<>();
        List<ItemPedidoDto> itens = new ArrayList<>();
        AddressDto endereco = new AddressDto(1L, "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "01234-567", 1L, now, now);
        PaymentDto pagamento = new PaymentDto(1L, 1L, PaymentMethod.CARTAO_CREDITO, 299.98, "Aprovado", "1234567890123456", now, now, now);
        
        OrderDto order = new OrderDto(1L, "PED-001", now, OrderStatus.ENTREGUE, 299.98f, "Comentário", 5, 1L, products, itens, endereco, pagamento, now, now);
        handler.create(order);
        
        // Agora avaliar o pedido
        boolean result = handler.evaluateOrder(1L, "Ótimo produto!", 5L);
        assertTrue(result);
    }

    @Test
    void evaluateOrder_ShouldReturnTrue_WhenZeroRating() {
        // Primeiro criar um pedido entregue
        Date now = new Date();
        List<ProductDto> products = new ArrayList<>();
        List<ItemPedidoDto> itens = new ArrayList<>();
        AddressDto endereco = new AddressDto(1L, "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "01234-567", 1L, now, now);
        PaymentDto pagamento = new PaymentDto(1L, 1L, PaymentMethod.CARTAO_CREDITO, 299.98, "Aprovado", "1234567890123456", now, now, now);
        
        OrderDto order = new OrderDto(2L, "PED-002", now, OrderStatus.ENTREGUE, 299.98f, "Comentário", 5, 1L, products, itens, endereco, pagamento, now, now);
        handler.create(order);
        
        // Agora avaliar o pedido
        boolean result = handler.evaluateOrder(2L, "Produto ruim", 1L);
        assertTrue(result);
    }

    @Test
    void evaluateOrder_ShouldReturnFalse_WhenOrderNotFound() {
        // Tentar avaliar um pedido que não existe
        boolean result = handler.evaluateOrder(999L, "Comentário", 5L);
        assertFalse(result);
    }

    @Test
    void evaluateOrder_ShouldReturnFalse_WhenOrderNotDelivered() {
        // Criar um pedido que não está entregue
        Date now = new Date();
        List<ProductDto> products = new ArrayList<>();
        List<ItemPedidoDto> itens = new ArrayList<>();
        AddressDto endereco = new AddressDto(1L, "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "01234-567", 1L, now, now);
        PaymentDto pagamento = new PaymentDto(1L, 1L, PaymentMethod.CARTAO_CREDITO, 299.98, "Aprovado", "1234567890123456", now, now, now);
        
        OrderDto order = new OrderDto(3L, "PED-003", now, OrderStatus.PREPARANDO, 299.98f, "Comentário", 5, 1L, products, itens, endereco, pagamento, now, now);
        handler.create(order);
        
        // Tentar avaliar o pedido que não está entregue
        boolean result = handler.evaluateOrder(3L, "Comentário", 5L);
        assertFalse(result);
    }

    @Test
    void create_ShouldReturnSameObject() {
        // Arrange
        Date now = new Date();
        List<ProductDto> products = new ArrayList<>();
        List<ItemPedidoDto> itens = new ArrayList<>();
        AddressDto endereco = new AddressDto(1L, "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "01234-567", 1L, now, now);
        PaymentDto pagamento = new PaymentDto(1L, 1L, PaymentMethod.CARTAO_CREDITO, 299.98, "Aprovado", "1234567890123456", now, now, now);
        
        OrderDto order = new OrderDto(4L, "PED-004", now, OrderStatus.PREPARANDO, 299.98f, "Comentário", 5, 1L, products, itens, endereco, pagamento, now, now);
        
        // Act
        OrderDto created = handler.create(order);
        
        // Assert
        assertNotNull(created);
        assertEquals(order.id(), created.id());
        assertEquals(order.numero(), created.numero());
    }
}
