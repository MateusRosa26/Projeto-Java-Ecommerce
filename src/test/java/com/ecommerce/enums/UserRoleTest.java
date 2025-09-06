package com.ecommerce.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class UserRoleTest {

    @Test
    void values_ShouldReturnAllRoles() {
        UserRole[] roles = UserRole.values();
        
        assertEquals(4, roles.length);
        assertTrue(List.of(roles).contains(UserRole.CLIENTE));
        assertTrue(List.of(roles).contains(UserRole.ADMIN));
        assertTrue(List.of(roles).contains(UserRole.GERENTE));
        assertTrue(List.of(roles).contains(UserRole.ENTREGADOR));
    }

    @Test
    void valueOf_ShouldReturnCorrectRole() {
        assertEquals(UserRole.CLIENTE, UserRole.valueOf("CLIENTE"));
        assertEquals(UserRole.ADMIN, UserRole.valueOf("ADMIN"));
        assertEquals(UserRole.GERENTE, UserRole.valueOf("GERENTE"));
        assertEquals(UserRole.ENTREGADOR, UserRole.valueOf("ENTREGADOR"));
    }

    @Test
    void valueOf_ShouldThrowException_WhenInvalidRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserRole.valueOf("INVALID_ROLE");
        });
    }

    @Test
    void ordinal_ShouldReturnCorrectIndex() {
        assertEquals(0, UserRole.CLIENTE.ordinal());
        assertEquals(1, UserRole.ADMIN.ordinal());
        assertEquals(2, UserRole.GERENTE.ordinal());
        assertEquals(3, UserRole.ENTREGADOR.ordinal());
    }

    @Test
    void name_ShouldReturnCorrectName() {
        assertEquals("CLIENTE", UserRole.CLIENTE.name());
        assertEquals("ADMIN", UserRole.ADMIN.name());
        assertEquals("GERENTE", UserRole.GERENTE.name());
        assertEquals("ENTREGADOR", UserRole.ENTREGADOR.name());
    }

    @Test
    void toString_ShouldReturnCorrectString() {
        assertEquals("CLIENTE", UserRole.CLIENTE.toString());
        assertEquals("ADMIN", UserRole.ADMIN.toString());
        assertEquals("GERENTE", UserRole.GERENTE.toString());
        assertEquals("ENTREGADOR", UserRole.ENTREGADOR.toString());
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertEquals("Cliente", UserRole.CLIENTE.getDescription());
        assertEquals("Admin", UserRole.ADMIN.getDescription());
        assertEquals("Gerente", UserRole.GERENTE.getDescription());
        assertEquals("Entregador", UserRole.ENTREGADOR.getDescription());
    }
}
