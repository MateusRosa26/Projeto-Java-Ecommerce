package com.ecommerce.controller;

import com.ecommerce.business.NotificationBusinessHandlerImpl;
import com.ecommerce.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de notificações
 */
@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController extends AbstractController<NotificationDto> {

    private final NotificationBusinessHandlerImpl notificationHandler;

    @Autowired
    public NotificationController(NotificationBusinessHandlerImpl notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    /**
     * Obtém notificações de um cliente
     */
    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByClient(@PathVariable Long clienteId) {
        List<NotificationDto> notifications = notificationHandler.getNotificationsByClient(clienteId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Obtém notificações de um pedido
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByOrder(@PathVariable Long orderId) {
        List<NotificationDto> notifications = notificationHandler.getNotificationsByOrder(orderId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Marca notificação como lida
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Boolean> markAsRead(@PathVariable Long notificationId) {
        boolean marked = notificationHandler.markAsRead(notificationId);
        return ResponseEntity.ok(marked);
    }

    /**
     * Envia notificação de mudança de status
     */
    @PostMapping("/status-change")
    public ResponseEntity<Boolean> notifyStatusChange(
            @RequestParam Long orderId,
            @RequestParam Long clienteId,
            @RequestParam String oldStatus,
            @RequestParam String newStatus) {
        
        boolean sent = notificationHandler.notifyOrderStatusChange(orderId, clienteId, oldStatus, newStatus);
        return ResponseEntity.ok(sent);
    }

    /**
     * Envia notificação de cancelamento
     */
    @PostMapping("/cancelled")
    public ResponseEntity<Boolean> notifyCancelled(
            @RequestParam Long orderId,
            @RequestParam Long clienteId) {
        
        boolean sent = notificationHandler.notifyOrderCancelled(orderId, clienteId);
        return ResponseEntity.ok(sent);
    }

    /**
     * Envia notificação de entrega
     */
    @PostMapping("/delivered")
    public ResponseEntity<Boolean> notifyDelivered(
            @RequestParam Long orderId,
            @RequestParam Long clienteId) {
        
        boolean sent = notificationHandler.notifyOrderDelivered(orderId, clienteId);
        return ResponseEntity.ok(sent);
    }

    /**
     * Envia notificação de pagamento aprovado
     */
    @PostMapping("/payment-approved")
    public ResponseEntity<Boolean> notifyPaymentApproved(
            @RequestParam Long orderId,
            @RequestParam Long clienteId) {
        
        boolean sent = notificationHandler.notifyPaymentApproved(orderId, clienteId);
        return ResponseEntity.ok(sent);
    }

    /**
     * Envia notificação de pagamento rejeitado
     */
    @PostMapping("/payment-rejected")
    public ResponseEntity<Boolean> notifyPaymentRejected(
            @RequestParam Long orderId,
            @RequestParam Long clienteId) {
        
        boolean sent = notificationHandler.notifyPaymentRejected(orderId, clienteId);
        return ResponseEntity.ok(sent);
    }

    // Implementações dos métodos abstratos
    @Override
    public ResponseEntity<List<NotificationDto>> get() {
        List<NotificationDto> notifications = notificationHandler.list();
        return ResponseEntity.ok(notifications);
    }

    @Override
    public ResponseEntity<NotificationDto> get(@PathVariable Long id) {
        NotificationDto notification = notificationHandler.find(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<NotificationDto> post(@RequestBody NotificationDto notification) {
        NotificationDto created = notificationHandler.create(notification);
        if (created != null) {
            return ResponseEntity.status(201).body(created);
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<NotificationDto> put(@PathVariable Long id, @RequestBody NotificationDto notification) {
        NotificationDto updated = notificationHandler.update(notification, id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = notificationHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
