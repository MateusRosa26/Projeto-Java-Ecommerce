package com.ecommerce.controller;

import com.ecommerce.business.NotificationBusinessHandlerImpl;
import com.ecommerce.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de notificações
 * Implementa RF08: Sistema deve notificar o cliente sobre o status do pedido
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
     * Lista todas as notificações
     */
    @Override
    @GetMapping
    public ResponseEntity<List<NotificationDto>> get() {
        List<NotificationDto> notifications = notificationHandler.list();
        return ResponseEntity.ok(notifications);
    }

    /**
     * Obtém uma notificação por ID
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> get(@PathVariable Long id) {
        NotificationDto notification = notificationHandler.find(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cria uma nova notificação
     */
    @Override
    @PostMapping
    public ResponseEntity<NotificationDto> post(@RequestBody NotificationDto notification) {
        NotificationDto created = notificationHandler.create(notification);
        return ResponseEntity.ok(created);
    }

    /**
     * Atualiza uma notificação existente
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> put(@PathVariable Long id, @RequestBody NotificationDto notification) {
        NotificationDto updated = notificationHandler.update(notification, id);
        return ResponseEntity.ok(updated);
    }

    /**
     * Remove uma notificação
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = notificationHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Lista notificações por cliente
     */
    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByClient(@PathVariable Long clienteId) {
        List<NotificationDto> notifications = notificationHandler.getNotificationsByClient(clienteId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Lista notificações não lidas por cliente
     */
    @GetMapping("/client/{clienteId}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@PathVariable Long clienteId) {
        List<NotificationDto> notifications = notificationHandler.getUnreadNotifications(clienteId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Marca uma notificação como lida
     */
    @PutMapping("/{id}/mark-read")
    public ResponseEntity<Boolean> markAsRead(@PathVariable Long id) {
        boolean success = notificationHandler.markAsRead(id);
        return ResponseEntity.ok(success);
    }

    /**
     * Marca uma notificação como não lida
     */
    @PutMapping("/{id}/mark-unread")
    public ResponseEntity<Boolean> markAsUnread(@PathVariable Long id) {
        boolean success = notificationHandler.markAsUnread(id);
        return ResponseEntity.ok(success);
    }

    /**
     * Envia notificação de status do pedido
     */
    @PostMapping("/send-order-status")
    public ResponseEntity<Boolean> sendOrderStatusNotification(
            @RequestParam Long clienteId,
            @RequestParam Long pedidoId,
            @RequestParam String status) {
        try {
            notificationHandler.sendOrderStatusNotification(clienteId, pedidoId, status);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * Envia notificação de pagamento
     */
    @PostMapping("/send-payment")
    public ResponseEntity<Boolean> sendPaymentNotification(
            @RequestParam Long clienteId,
            @RequestParam Long pedidoId,
            @RequestParam String status) {
        try {
            notificationHandler.sendPaymentNotification(clienteId, pedidoId, status);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
