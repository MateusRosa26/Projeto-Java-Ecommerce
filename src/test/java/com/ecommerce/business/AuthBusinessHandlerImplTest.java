package com.ecommerce.business;

import com.ecommerce.dto.UserDto;
import com.ecommerce.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthBusinessHandlerImplTest {

    private AuthBusinessHandlerImpl authHandler;

    @BeforeEach
    void setUp() {
        authHandler = new AuthBusinessHandlerImpl();
    }

    @Test
    void testCreateUserWithValidData() {
        UserDto user = new UserDto(0L, "testuser", "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        UserDto createdUser = authHandler.create(user);
        
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.username());
        assertEquals("Test User", createdUser.nome());
        assertEquals("test@email.com", createdUser.email());
        assertEquals(UserRole.CLIENTE.getDescription(), createdUser.role());
    }

    @Test
    void testCreateUserWithInvalidPassword() {
        UserDto user = new UserDto(0L, "testuser", "Test User", "test@email.com", 
            "weak", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        UserDto createdUser = authHandler.create(user);
        
        assertNull(createdUser);
    }

    @Test
    void testCreateUserWithNullData() {
        UserDto createdUser = authHandler.create(null);
        assertNull(createdUser);
    }

    @Test
    void testCreateUserWithNullUsername() {
        UserDto user = new UserDto(0L, null, "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        UserDto createdUser = authHandler.create(user);
        assertNull(createdUser);
    }

    @Test
    void testCreateUserWithNullEmail() {
        UserDto user = new UserDto(0L, "testuser", "Test User", null, 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        UserDto createdUser = authHandler.create(user);
        assertNull(createdUser);
    }

    @Test
    void testLoginWithValidUser() {
        // Primeiro cria um usuário
        UserDto user = new UserDto(0L, "testuser", "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        authHandler.create(user);
        
        // Depois tenta fazer login
        UserDto loggedUser = authHandler.login("testuser", "ValidPass@123");
        
        assertNotNull(loggedUser);
        assertEquals("testuser", loggedUser.username());
    }

    @Test
    void testLoginWithInvalidUser() {
        UserDto loggedUser = authHandler.login("nonexistent", "password");
        assertNull(loggedUser);
    }

    @Test
    void testLoginWithAdminFallback() {
        UserDto loggedUser = authHandler.login("admin", "admin123");
        
        assertNotNull(loggedUser);
        assertEquals("admin", loggedUser.username());
        assertEquals("Administrador", loggedUser.nome());
        assertEquals(UserRole.GERENTE.getDescription(), loggedUser.role());
    }

    @Test
    void testListUsers() {
        // Cria alguns usuários
        UserDto user1 = new UserDto(0L, "user1", "User 1", "user1@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        UserDto user2 = new UserDto(0L, "user2", "User 2", "user2@email.com", 
            "ValidPass@123", "11999999999", UserRole.GERENTE.getDescription(), null, null, null);
        
        authHandler.create(user1);
        authHandler.create(user2);
        
        var users = authHandler.list();
        
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testUserRoleAssignment() {
        UserDto cliente = new UserDto(0L, "cliente", "Cliente", "cliente@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        UserDto gerente = new UserDto(0L, "gerente", "Gerente", "gerente@email.com", 
            "ValidPass@123", "11999999999", UserRole.GERENTE.getDescription(), null, null, null);
        
        UserDto createdCliente = authHandler.create(cliente);
        UserDto createdGerente = authHandler.create(gerente);
        
        assertEquals(UserRole.CLIENTE.getDescription(), createdCliente.role());
        assertEquals(UserRole.GERENTE.getDescription(), createdGerente.role());
    }
}