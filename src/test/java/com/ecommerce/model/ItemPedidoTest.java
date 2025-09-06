package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private ItemPedido itemPedido;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido();
    }

    @Test
    void csvConversionRoundTrip() {
        ItemPedido original = new ItemPedido();
        original.setId(1L);
        original.setPedidoId(1L);
        original.setQuantidade(2);
        original.setPrecoUnitario(25.50);
        original.setProdutoId(1L);
        
        String[] csv = original.toCsvRow();
        ItemPedido parsed = ItemPedido.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getPedidoId(), parsed.getPedidoId());
        assertEquals(original.getQuantidade(), parsed.getQuantidade());
        assertEquals(original.getPrecoUnitario(), parsed.getPrecoUnitario(), 0.01);
        assertEquals(original.getProdutoId(), parsed.getProdutoId());
    }

    @Test
    void gettersAndSetters() {
        itemPedido.setId(1L);
        itemPedido.setPedidoId(1L);
        itemPedido.setQuantidade(3);
        itemPedido.setPrecoUnitario(15.99);
        itemPedido.setProdutoId(1L);
        assertEquals(1L, itemPedido.getId());
        assertEquals(1L, itemPedido.getPedidoId());
        assertEquals(3, itemPedido.getQuantidade());
        assertEquals(15.99, itemPedido.getPrecoUnitario(), 0.01);
        assertEquals(1L, itemPedido.getProdutoId());
    }

    @Test
    void getSubtotal() {
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(25.50);
        
        double expectedSubtotal = 2 * 25.50;
        assertEquals(expectedSubtotal, itemPedido.getSubtotal(), 0.01);
    }

    @Test
    void atualizarQuantidade() {
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(10.0);
        
        itemPedido.atualizarQuantidade(5);
        
        assertEquals(5, itemPedido.getQuantidade());
    }

    @Test
    void csvHeader() {
        String[] header = ItemPedido.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "1", "2", "2", "25.50"};
        ItemPedido parsed = ItemPedido.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals(1L, parsed.getProdutoId());
        assertEquals(2L, parsed.getPedidoId());
        assertEquals(2, parsed.getQuantidade());
        assertEquals(25.50, parsed.getPrecoUnitario(), 0.01);
    }

    @Test
    void defaultConstructor() {
        ItemPedido item = new ItemPedido();
        assertNotNull(item);
    }
}
