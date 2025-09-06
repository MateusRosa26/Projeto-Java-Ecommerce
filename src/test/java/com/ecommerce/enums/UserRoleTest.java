package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void testUserRoleValues() {
        // Verifica se todos os roles existem
        assertEquals(3, UserRole.values().length);
        assertTrue(UserRole.CLIENTE != null);
        assertTrue(UserRole.GERENTE != null);
        assertTrue(UserRole.ENTREGADOR != null);
    }

    @Test
    void testUserRoleDescriptions() {
        assertEquals("Cliente", UserRole.CLIENTE.getDescription());
        assertEquals("Gerente", UserRole.GERENTE.getDescription());
        assertEquals("Entregador", UserRole.ENTREGADOR.getDescription());
    }

    @Test
    void testUserRoleCodes() {
        assertEquals(1, UserRole.CLIENTE.getCode());
        assertEquals(2, UserRole.GERENTE.getCode());
        assertEquals(3, UserRole.ENTREGADOR.getCode());
    }

    @Test
    void testFromCode() {
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(1));
        assertEquals(UserRole.GERENTE, UserRole.fromCode(2));
        assertEquals(UserRole.ENTREGADOR, UserRole.fromCode(3));
        
        // Código inválido deve retornar CLIENTE como default
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(999));
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(0));
        assertEquals(UserRole.CLIENTE, UserRole.fromCode(-1));
    }

    @Test
    void testFromString() {
        assertEquals(UserRole.CLIENTE, UserRole.fromString("Cliente"));
        assertEquals(UserRole.GERENTE, UserRole.fromString("Gerente"));
        assertEquals(UserRole.ENTREGADOR, UserRole.fromString("Entregador"));
        
        // Case insensitive
        assertEquals(UserRole.CLIENTE, UserRole.fromString("cliente"));
        assertEquals(UserRole.GERENTE, UserRole.fromString("GERENTE"));
        assertEquals(UserRole.ENTREGADOR, UserRole.fromString("Entregador"));
        
        // String inválida deve retornar CLIENTE como default
        assertEquals(UserRole.CLIENTE, UserRole.fromString("Admin"));
        assertEquals(UserRole.CLIENTE, UserRole.fromString(""));
        assertEquals(UserRole.CLIENTE, UserRole.fromString(null));
    }
}
