package com.ecommerce.business;

import com.ecommerce.dto.UserDto;
import com.ecommerce.enums.UserRole;
import com.ecommerce.util.PasswordValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class AuthBusinessHandlerImpl implements BusinessHandler<UserDto> {
    
    // Armazenamento temporário de usuários (em produção seria um banco de dados)
    private static final Map<String, UserDto> registeredUsers = new HashMap<>();
    private static long nextUserId = 1L;

    @Override
    public UserDto create(UserDto object) {
        // Registra um novo usuário
        if (object != null && object.username() != null && object.email() != null) {
            // Validação de senha
            if (object.senha() != null && !PasswordValidator.isValid(object.senha())) {
                System.out.println("❌ Senha inválida: " + PasswordValidator.getValidationMessage());
                return null;
            }
            
            UserDto newUser = new UserDto(
                nextUserId++,
                object.username(),
                object.nome(),
                object.email(),
                object.senha(),
                object.telefone(),
                object.role() != null ? object.role() : UserRole.CLIENTE.getDescription(),
                null, null, null
            );
            registeredUsers.put(object.username(), newUser);
            System.out.println("✅ Usuário registrado: " + object.username() + " (" + newUser.role() + ")");
            return newUser;
        }
        return null;
    }

    @Override
    public UserDto update(UserDto object, long id) {
        // Implementação básica
        return object;
    }

    @Override
    public boolean delete(long id) {
        // Implementação básica
        return true;
    }

    @Override
    public UserDto find(long id) {
        // Implementação básica
        return null;
    }

    @Override
    public List<UserDto> list() {
        return new ArrayList<>(registeredUsers.values());
    }

    @Override
    public List<UserDto> list(String field, String filter, boolean exact) {
        // Implementação básica
        return new ArrayList<>();
    }

    public UserDto login(String username, String password) {
        // Verifica se o usuário existe
        UserDto user = registeredUsers.get(username);
        if (user != null) {
            // Para simplificar, vamos aceitar qualquer senha para usuários registrados
            // Em produção, seria necessário verificar a senha hash
            System.out.println("Login bem-sucedido para: " + username);
            return user;
        }
        
        // Fallback para admin (para testes)
        if ("admin".equals(username) && "admin123".equals(password)) {
            return new UserDto(1L, username, "Administrador", username + "@example.com", 
                "admin123", "11999999999", UserRole.GERENTE.getDescription(), null, null, null);
        }
        
        System.out.println("Login falhou para: " + username);
        return null;
    }
    
    // Método para debug - listar usuários registrados
    public void listRegisteredUsers() {
        System.out.println("=== USUÁRIOS REGISTRADOS ===");
        for (Map.Entry<String, UserDto> entry : registeredUsers.entrySet()) {
            System.out.println("Username: " + entry.getKey() + " -> " + entry.getValue().email());
        }
        System.out.println("============================");
    }
}
