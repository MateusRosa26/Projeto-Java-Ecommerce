package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void csvConversionRoundTrip() {
        Produto original = new Produto(1L, "Teclado", 199.99f, "Teclado mecânico", "url", true);
        String[] csv = original.toCsvRow();
        Produto parsed = Produto.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getNome(), parsed.getNome());
        assertEquals(original.getPreco(), parsed.getPreco());
        assertEquals(original.getDescricao(), parsed.getDescricao());
        assertEquals(original.getImagemUrl(), parsed.getImagemUrl());
        assertEquals(original.isDisponivel(), parsed.isDisponivel());
    }
}
