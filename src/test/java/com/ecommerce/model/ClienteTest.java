package com.ecommerce.model;

import com.ecommerce.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    void csvConversionRoundTrip() {
        Cliente original = new Cliente(10L, "Ana", "ana@example.com", "senha123", "11999999999", "São Paulo", new Date(), UserRole.CLIENTE);
        String[] csv = original.toCsvRow();
        Cliente parsed = Cliente.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getNome(), parsed.getNome());
        assertEquals(original.getEmail(), parsed.getEmail());
        assertEquals(original.getSenha(), parsed.getSenha());
        assertEquals(original.getTelefone(), parsed.getTelefone());
        assertEquals(original.getCidade(), parsed.getCidade());
        assertEquals(original.getRole(), parsed.getRole());
    }

    @Test
    void constructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(1L, "", "mail@test.com", "senha", "11999999999", "São Paulo", new Date(), UserRole.CLIENTE));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(1L, "Nome", "", "senha", "11999999999", "São Paulo", new Date(), UserRole.CLIENTE));
    }

    @Test
    void gettersAndSetters() {
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@test.com");
        cliente.setSenha("senha123");
        cliente.setTelefone("11999999999");
        cliente.setCidade("São Paulo");
        cliente.setDataNascimento(new Date());
        cliente.setRole(UserRole.CLIENTE);

        assertEquals(1L, cliente.getId());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("joao@test.com", cliente.getEmail());
        assertEquals("senha123", cliente.getSenha());
        assertEquals("11999999999", cliente.getTelefone());
        assertEquals("São Paulo", cliente.getCidade());
        assertNotNull(cliente.getDataNascimento());
        assertEquals(UserRole.CLIENTE, cliente.getRole());
    }

    @Test
    void csvHeader() {
        String[] header = Cliente.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "João", "joao@test.com", "", "", "", "", "0", "2023-01-01", "2023-01-01"};
        Cliente parsed = Cliente.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals("João", parsed.getNome());
        assertEquals("joao@test.com", parsed.getEmail());
        assertEquals(UserRole.CLIENTE, parsed.getRole());
    }
}
