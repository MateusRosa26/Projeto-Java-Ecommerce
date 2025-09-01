package com.ecommerce.controller;

import com.ecommerce.business.OrderBusinessHandlerImpl;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de pedidos
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController extends AbstractController<OrderDto> {

    private final OrderBusinessHandlerImpl orderHandler;

    @Autowired
    public OrderController(OrderBusinessHandlerImpl orderHandler) {
        this.orderHandler = orderHandler;
    }

    /**
     * Finaliza um pedido
     * Baseado no diagrama: finishOrder(List<ProductDto> products): ResponseEntity
     */
    @PostMapping("/finish")
    public ResponseEntity<Boolean> finishOrder(@RequestBody List<ProductDto> products) {
        boolean finished = orderHandler.finishOrder(products);
        return ResponseEntity.ok(finished);
    }

    /**
     * Atualiza o status de um pedido
     * Baseado no diagrama: updateOrderStatus(Long id, Long status): ResponseEntity
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        boolean updated = orderHandler.updateOrderStatus(id, status);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * Cancela um pedido
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long id) {
        boolean cancelled = orderHandler.cancelOrder(id);
        return ResponseEntity.ok(cancelled);
    }
    
    /**
     * Busca pedidos por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDto> orders = orderHandler.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Avalia um pedido
     * Baseado no diagrama: evaluateOrder(Long id, String comment, int rating): ResponseEntity
     */
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<Boolean> evaluateOrder(
            @PathVariable Long id,
            @RequestParam String comment,
            @RequestParam int rating) {
        boolean evaluated = orderHandler.evaluateOrder(id, comment, rating);
        return ResponseEntity.ok(evaluated);
    }

    // Implementações dos métodos abstratos (básicas para este exemplo)
    @Override
    public ResponseEntity<List<OrderDto>> get() {
        // Implementação básica
        return ResponseEntity.ok(List.of());
    }

    @Override
    public ResponseEntity<OrderDto> get(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(new OrderDto(id, "ORD-" + id, "Preparando", new java.util.Date(), 0.0f, "", 0, 1L, List.of()));
    }

    @Override
    public ResponseEntity<OrderDto> post(@RequestBody OrderDto order) {
        // Implementação básica
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<OrderDto> put(@PathVariable Long id, @RequestBody OrderDto order) {
        // Implementação básica
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(true);
    }
}
