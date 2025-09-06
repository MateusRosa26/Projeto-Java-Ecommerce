package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
    }

    @Test
    void csvConversionRoundTrip() {
        Produto original = new Produto(1L, "Teclado", 199.99f, "Teclado mecânico", "url", "1234567890123", true);
        String[] csv = original.toCsvRow();
        Produto parsed = Produto.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getNome(), parsed.getNome());
        assertEquals(original.getPreco(), parsed.getPreco());
        assertEquals(original.getDescricao(), parsed.getDescricao());
        assertEquals(original.getImagemUrl(), parsed.getImagemUrl());
        assertEquals(original.getCodigoBarras(), parsed.getCodigoBarras());
        assertEquals(original.isDisponivel(), parsed.isDisponivel());
    }

    @Test
    void constructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Produto(1L, "", 10.0f, "Desc", "img", "123", true));
        assertThrows(IllegalArgumentException.class, () -> new Produto(1L, "Nome", -1.0f, "Desc", "img", "123", true));
    }

    @Test
    void gettersAndSetters() {
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(25.99f);
        produto.setDescricao("Descrição do produto");
        produto.setImagemUrl("imagem.jpg");
        produto.setCodigoBarras("1234567890123");
        produto.setDisponivel(true);
        produto.setCreatedAt(new Date());
        produto.setUpdatedAt(new Date());

        assertEquals(1L, produto.getId());
        assertEquals("Produto Teste", produto.getNome());
        assertEquals(25.99f, produto.getPreco(), 0.01f);
        assertEquals("Descrição do produto", produto.getDescricao());
        assertEquals("imagem.jpg", produto.getImagemUrl());
        assertEquals("1234567890123", produto.getCodigoBarras());
        assertTrue(produto.isDisponivel());
        assertNotNull(produto.getCreatedAt());
        assertNotNull(produto.getUpdatedAt());
    }

    @Test
    void csvHeader() {
        String[] header = Produto.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "Produto", "10.0", "Descrição", "", "1234567890123", "true", "2023-01-01", "2023-01-01"};
        Produto parsed = Produto.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals("Produto", parsed.getNome());
        assertEquals(10.0f, parsed.getPreco(), 0.01f);
        assertEquals("1234567890123", parsed.getCodigoBarras());
        assertTrue(parsed.isDisponivel());
    }

    @Test
    void defaultConstructor() {
        Produto produto = new Produto();
        assertNotNull(produto.getCreatedAt());
        assertNotNull(produto.getUpdatedAt());
    }
}