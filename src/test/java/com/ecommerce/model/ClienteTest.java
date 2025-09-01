package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void csvConversionRoundTrip() {
        Cliente original = new Cliente(10L, "Ana", "ana@example.com");
        String[] csv = original.toCsvRow();
        Cliente parsed = Cliente.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getNome(), parsed.getNome());
        assertEquals(original.getEmail(), parsed.getEmail());
    }

    @Test
    void constructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(1L, "", "mail"));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(1L, "Nome", " "));
    }
}
