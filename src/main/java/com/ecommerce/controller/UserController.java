package com.ecommerce.controller;

import com.ecommerce.business.AuthBusinessHandlerImpl;
import com.ecommerce.dto.UserDto;
import com.ecommerce.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de usuários
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController extends AbstractController<UserDto> {

    private final AuthBusinessHandlerImpl authHandler;

    @Autowired
    public UserController(AuthBusinessHandlerImpl authHandler) {
        this.authHandler = authHandler;
    }

    /**
     * Registra um novo usuário
     * Baseado no diagrama: register(String user, String pwd): ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        // Usa o AuthBusinessHandler para registrar o usuário
        UserDto newUser = authHandler.create(userDto);
        if (newUser != null) {
            System.out.println("✅ Usuário registrado com sucesso: " + userDto.username());
            return ResponseEntity.status(201).body(newUser);
        } else {
            System.out.println("❌ Falha ao registrar usuário: " + userDto.username());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Realiza login do usuário
     * Baseado no diagrama: login(String user, String pwd): ResponseEntity<UserDto>
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        UserDto loggedUser = authHandler.login(userDto.email(), userDto.username()); // usando username como senha temporariamente
        if (loggedUser != null) {
            return ResponseEntity.ok(loggedUser);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Cria endereço para um usuário
     * Baseado no diagrama: createAddress(Long id_user, AddressDto address): ResponseEntity
     */
    @PostMapping("/{id}/address")
    public ResponseEntity<AddressDto> createAddress(@PathVariable Long id, @RequestBody AddressDto address) {
        // Implementação básica - em produção seria mais robusta
        return ResponseEntity.ok(address);
    }

    /**
     * Endpoint para debug - lista usuários registrados
     */
    @GetMapping("/debug/list")
    public ResponseEntity<String> listRegisteredUsers() {
        authHandler.listRegisteredUsers();
        return ResponseEntity.ok("Usuários listados no console da API");
    }



    // Implementações dos métodos abstratos (básicas para este exemplo)
    @Override
    public ResponseEntity<List<UserDto>> get() {
        // Implementação básica
        return ResponseEntity.ok(List.of());
    }

    @Override
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(new UserDto(id, "user" + id, "User " + id, "user" + id + "@email.com", null, null, null, null, null, null, null, new java.util.Date(), new java.util.Date()));
    }

    @Override
    public ResponseEntity<UserDto> post(@RequestBody UserDto user) {
        // Implementação básica
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDto> put(@PathVariable Long id, @RequestBody UserDto user) {
        // Implementação básica
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(true);
    }
}
