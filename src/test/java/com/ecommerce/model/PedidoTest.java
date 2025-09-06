package com.ecommerce.model;

import com.ecommerce.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    void csvConversionRoundTrip() {
        Pedido original = new Pedido(1L, "PED123", new Date(), OrderStatus.PREPARANDO, 100.0f, "Comentário", 5, 1L);
        String[] csv = original.toCsvRow();
        Pedido parsed = Pedido.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getNumero(), parsed.getNumero());
        assertEquals(original.getStatus(), parsed.getStatus());
        assertEquals(original.getTotal(), parsed.getTotal(), 0.01f);
        assertEquals(original.getComment(), parsed.getComment());
        assertEquals(original.getRating(), parsed.getRating());
        assertEquals(original.getClienteId(), parsed.getClienteId());
    }

    @Test
    void gettersAndSetters() {
        pedido.setId(1L);
        pedido.setNumero("PED123");
        pedido.setDataPedido(new Date());
        pedido.setStatus(OrderStatus.SAINDO_PARA_ENTREGA);
        pedido.setTotal(150.0f);
        pedido.setComment("Pedido teste");
        pedido.setRating(4);
        pedido.setClienteId(1L);
        pedido.setCreatedAt(new Date());
        pedido.setUpdatedAt(new Date());

        assertEquals(1L, pedido.getId());
        assertEquals("PED123", pedido.getNumero());
        assertNotNull(pedido.getDataPedido());
        assertEquals(OrderStatus.SAINDO_PARA_ENTREGA, pedido.getStatus());
        assertEquals(150.0f, pedido.getTotal(), 0.01f);
        assertEquals("Pedido teste", pedido.getComment());
        assertEquals(4, pedido.getRating());
        assertEquals(1L, pedido.getClienteId());
        assertNotNull(pedido.getCreatedAt());
        assertNotNull(pedido.getUpdatedAt());
    }

    @Test
    void podeSerCancelado() {
        pedido.setStatus(OrderStatus.PREPARANDO);
        assertTrue(pedido.podeSerCancelado());

        pedido.setStatus(OrderStatus.SAINDO_PARA_ENTREGA);
        assertTrue(pedido.podeSerCancelado());

        pedido.setStatus(OrderStatus.ENTREGUE);
        assertFalse(pedido.podeSerCancelado());

        pedido.setStatus(OrderStatus.CANCELADO);
        assertFalse(pedido.podeSerCancelado());
    }

    @Test
    void csvHeader() {
        String[] header = Pedido.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "PED123", "2023-01-01", "0", "100.0", "Comentário", "5", "1", "2023-01-01", "2023-01-01"};
        Pedido parsed = Pedido.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals("PED123", parsed.getNumero());
        assertEquals(OrderStatus.PREPARANDO, parsed.getStatus());
        assertEquals(100.0f, parsed.getTotal(), 0.01f);
        assertEquals(1L, parsed.getClienteId());
    }

    @Test
    void defaultConstructor() {
        Pedido pedido = new Pedido();
        assertNotNull(pedido.getCreatedAt());
        assertNotNull(pedido.getUpdatedAt());
        assertNotNull(pedido.getDataPedido());
    }
}