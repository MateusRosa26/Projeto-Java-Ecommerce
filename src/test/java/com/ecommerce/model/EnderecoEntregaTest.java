package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class EnderecoEntregaTest {

    private EnderecoEntrega endereco;

    @BeforeEach
    void setUp() {
        endereco = new EnderecoEntrega();
    }

    @Test
    void csvConversionRoundTrip() {
        EnderecoEntrega original = new EnderecoEntrega(1L, "Rua das Flores", "123", "Centro", "São Paulo", "SP", "Apto 45", "01234567", 1L);
        String[] csv = original.toCsvRow();
        EnderecoEntrega parsed = EnderecoEntrega.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getRua(), parsed.getRua());
        assertEquals(original.getNumero(), parsed.getNumero());
        assertEquals(original.getBairro(), parsed.getBairro());
        assertEquals(original.getCidade(), parsed.getCidade());
        assertEquals(original.getEstado(), parsed.getEstado());
        assertEquals(original.getComplemento(), parsed.getComplemento());
        assertEquals(original.getCep(), parsed.getCep());
        assertEquals(original.getClienteId(), parsed.getClienteId());
    }

    @Test
    void gettersAndSetters() {
        endereco.setId(1L);
        endereco.setRua("Rua das Palmeiras");
        endereco.setNumero("456");
        endereco.setBairro("Jardins");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setComplemento("Casa 2");
        endereco.setCep("04567890");
        endereco.setClienteId(1L);
        endereco.setCreatedAt(new Date());
        endereco.setUpdatedAt(new Date());

        assertEquals(1L, endereco.getId());
        assertEquals("Rua das Palmeiras", endereco.getRua());
        assertEquals("456", endereco.getNumero());
        assertEquals("Jardins", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("SP", endereco.getEstado());
        assertEquals("Casa 2", endereco.getComplemento());
        assertEquals("04567890", endereco.getCep());
        assertEquals(1L, endereco.getClienteId());
        assertNotNull(endereco.getCreatedAt());
        assertNotNull(endereco.getUpdatedAt());
    }

    @Test
    void getEnderecoCompleto() {
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setComplemento("Apto 45");
        endereco.setCep("01234567");

        String enderecoCompleto = endereco.getEnderecoCompleto();
        assertTrue(enderecoCompleto.contains("Rua das Flores"));
        assertTrue(enderecoCompleto.contains("123"));
        assertTrue(enderecoCompleto.contains("Centro"));
        assertTrue(enderecoCompleto.contains("São Paulo"));
        assertTrue(enderecoCompleto.contains("SP"));
        assertTrue(enderecoCompleto.contains("Apto 45"));
        assertTrue(enderecoCompleto.contains("01234567"));
    }

    @Test
    void csvHeader() {
        String[] header = EnderecoEntrega.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "Rua Teste", "123", "Centro", "São Paulo", "SP", "", "01234567", "1", "2023-01-01", "2023-01-01"};
        EnderecoEntrega parsed = EnderecoEntrega.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals("Rua Teste", parsed.getRua());
        assertEquals("123", parsed.getNumero());
        assertEquals("Centro", parsed.getBairro());
        assertEquals("São Paulo", parsed.getCidade());
        assertEquals("SP", parsed.getEstado());
        assertEquals("", parsed.getComplemento());
        assertEquals("01234567", parsed.getCep());
        assertEquals(1L, parsed.getClienteId());
    }

    @Test
    void defaultConstructor() {
        EnderecoEntrega endereco = new EnderecoEntrega();
        assertNotNull(endereco.getCreatedAt());
        assertNotNull(endereco.getUpdatedAt());
    }
}
