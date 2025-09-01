package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe abstrata que define operações CRUD genéricas para controllers REST
 * Baseada no diagrama de controllers do sistema
 */
public abstract class AbstractController<T> {

    /**
     * Obtém uma lista de entidades
     */
    @GetMapping
    public abstract ResponseEntity<List<T>> get();

    /**
     * Obtém uma entidade por ID
     */
    @GetMapping("/{id}")
    public abstract ResponseEntity<T> get(@PathVariable Long id);

    /**
     * Cria uma nova entidade
     */
    @PostMapping
    public abstract ResponseEntity<T> post(@RequestBody T object);

    /**
     * Atualiza uma entidade existente
     */
    @PutMapping("/{id}")
    public abstract ResponseEntity<T> put(@PathVariable Long id, @RequestBody T object);

    /**
     * Remove uma entidade
     */
    @DeleteMapping("/{id}")
    public abstract ResponseEntity<Boolean> delete(@PathVariable Long id);
}
