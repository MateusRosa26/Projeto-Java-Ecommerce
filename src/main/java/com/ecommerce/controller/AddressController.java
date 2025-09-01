package com.ecommerce.controller;

import com.ecommerce.dto.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de endereços
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "*")
public class AddressController extends AbstractController<AddressDto> {

    /**
     * Cria endereço para um usuário
     * Baseado no diagrama: createAddress(Long id_user, AddressDto address): ResponseEntity
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressDto> createAddress(@PathVariable Long userId, @RequestBody AddressDto address) {
        // Implementação básica - em produção seria mais robusta
        return ResponseEntity.ok(address);
    }

    /**
     * Obtém endereços de um usuário
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDto>> getAddressesByUser(@PathVariable Long userId) {
        // Implementação básica - em produção seria mais robusta
        AddressDto address = new AddressDto("Rua Teste", "123", "Centro", "São Paulo", "SP", "Apto 1", "01234-567");
        return ResponseEntity.ok(List.of(address));
    }

    // Implementações dos métodos abstratos (básicas para este exemplo)
    @Override
    public ResponseEntity<List<AddressDto>> get() {
        // Implementação básica
        AddressDto address = new AddressDto("Rua Teste", "123", "Centro", "São Paulo", "SP", "Apto 1", "01234-567");
        return ResponseEntity.ok(List.of(address));
    }

    @Override
    public ResponseEntity<AddressDto> get(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(new AddressDto("Rua Teste", "123", "Centro", "São Paulo", "SP", "Apto 1", "01234-567"));
    }

    @Override
    public ResponseEntity<AddressDto> post(@RequestBody AddressDto address) {
        // Implementação básica
        return ResponseEntity.ok(address);
    }

    @Override
    public ResponseEntity<AddressDto> put(@PathVariable Long id, @RequestBody AddressDto address) {
        // Implementação básica
        return ResponseEntity.ok(address);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(true);
    }
}
