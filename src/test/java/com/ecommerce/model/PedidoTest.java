package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void csvConversionRoundTrip() throws ParseException {
        Date now = DF.parse("2024-01-01");
        Pedido original = new Pedido(1L, "PED-001", now, 1, 150.0f, "", 0, now, now);
        String[] csv = original.toCsvRow();
        Pedido parsed = Pedido.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals("PED-001", parsed.getNumero());
        assertEquals(original.getStatus(), parsed.getStatus());
        assertEquals(original.getClienteId(), parsed.getClienteId());
    }
}
