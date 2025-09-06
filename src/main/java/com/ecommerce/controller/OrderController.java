package com.ecommerce.controller;

import com.ecommerce.business.OrderBusinessHandlerImpl;
import com.ecommerce.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PostMapping("/finishOrder")
    public ResponseEntity<Boolean> finishOrder(@RequestBody Map<String, Object> request) {
        try {
            
            // Simular finalização do pedido
            boolean finished = orderHandler.finishOrder(List.of());
            return ResponseEntity.ok(finished);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * Atualiza o status de um pedido
     * Baseado no diagrama: updateOrderStatus(Long id, Long status): ResponseEntity
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body(false);
            }
            
            // Implementação básica - em produção seria mais robusta
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * Avalia um pedido
     * Baseado no diagrama: evaluateOrder(Long id, String comment, int rating): ResponseEntity
     */
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<Boolean> evaluateOrder(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            String deliveryComment = (String) request.get("deliveryComment");
            String productComment = (String) request.get("productComment");
            Integer deliveryRating = (Integer) request.get("deliveryRating");
            Integer productRating = (Integer) request.get("productRating");
            
            if (deliveryComment == null || productComment == null || deliveryRating == null || productRating == null) {
                return ResponseEntity.badRequest().body(false);
            }
            
            boolean evaluated = orderHandler.evaluateOrder(id, productComment, productRating.longValue());
            return ResponseEntity.ok(evaluated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
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
        return ResponseEntity.ok(new OrderDto(id, "ORD-" + id, new java.util.Date(), com.ecommerce.enums.OrderStatus.PREPARANDO, 0.0f, "", 0, 1L, List.of(), List.of(), null, null, new java.util.Date(), new java.util.Date()));
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
