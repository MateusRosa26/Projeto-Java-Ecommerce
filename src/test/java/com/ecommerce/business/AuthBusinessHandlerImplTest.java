package com.ecommerce.business;

import com.ecommerce.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthBusinessHandlerImplTest {

    private AuthBusinessHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new AuthBusinessHandlerImpl();
    }

    @Test
    void login_ShouldReturnUser_WhenValidCredentials() {
        UserDto user = handler.login("admin", "admin123");
        
        assertNotNull(user);
        assertEquals("admin", user.username());
        assertEquals("Administrador", user.nome());
        assertEquals("admin@example.com", user.email());
    }

    @Test
    void login_ShouldReturnNull_WhenInvalidCredentials() {
        UserDto user = handler.login("admin", "wrongpassword");
        assertNull(user);
    }

    @Test
    void login_ShouldReturnNull_WhenUserNotFound() {
        UserDto user = handler.login("nonexistent", "admin123");
        assertNull(user);
    }

    @Test
    void create_ShouldReturnSameObject() {
        UserDto user = new UserDto(1L, "test", "Test User", "test@example.com", null, null, null);
        UserDto created = handler.create(user);
        
        assertNotNull(created);
        assertEquals(user.id(), created.id());
        assertEquals(user.username(), created.username());
    }
}
