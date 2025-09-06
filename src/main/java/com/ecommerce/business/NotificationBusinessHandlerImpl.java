package com.ecommerce.business;

import com.ecommerce.dto.NotificationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationBusinessHandlerImpl implements BusinessHandler<NotificationDto> {
    
    // Armazenamento temporário de notificações (em produção seria um banco de dados)
    private static final Map<Long, NotificationDto> notifications = new ConcurrentHashMap<>();
    private static long nextNotificationId = 1L;
    
    @Override
    public NotificationDto create(NotificationDto object) {
        if (object != null) {
            NotificationDto newNotification = new NotificationDto(
                nextNotificationId++,
                object.tipo(),
                object.mensagem(),
                LocalDateTime.now(),
                object.idCliente(),
                object.idPedido()
            );
            notifications.put(newNotification.id(), newNotification);
            System.out.println("📧 Notificação enviada: " + newNotification.mensagem());
            return newNotification;
        }
        return null;
    }

    @Override
    public NotificationDto update(NotificationDto object, long id) {
        if (notifications.containsKey(id)) {
            notifications.put(id, object);
            return object;
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return notifications.remove(id) != null;
    }

    @Override
    public NotificationDto find(long id) {
        return notifications.get(id);
    }

    @Override
    public List<NotificationDto> list() {
        return new ArrayList<>(notifications.values());
    }

    @Override
    public List<NotificationDto> list(String field, String filter, boolean exact) {
        return notifications.values().stream()
            .filter(notif -> {
                switch (field.toLowerCase()) {
                    case "tipo":
                        return exact ? notif.tipo().equals(filter)
                                   : notif.tipo().toLowerCase().contains(filter.toLowerCase());
                    case "mensagem":
                        return exact ? notif.mensagem().equals(filter)
                                   : notif.mensagem().toLowerCase().contains(filter.toLowerCase());
                    case "idcliente":
                        return notif.idCliente().equals(Long.parseLong(filter));
                    case "idpedido":
                        return notif.idPedido().equals(Long.parseLong(filter));
                    default:
                        return false;
                }
            })
            .toList();
    }
    
    /**
     * Envia notificação de mudança de status do pedido
     */
    public boolean notifyOrderStatusChange(Long orderId, Long clienteId, String oldStatus, String newStatus) {
        String message = String.format("Seu pedido #%d mudou de status: %s → %s", 
            orderId, oldStatus, newStatus);
        
        NotificationDto notification = new NotificationDto(
            nextNotificationId++,
            "email",
            message,
            LocalDateTime.now(),
            clienteId,
            orderId
        );
        
        notifications.put(notification.id(), notification);
        System.out.println("📧 Notificação de status enviada: " + message);
        return true;
    }
    
    /**
     * Envia notificação de pedido cancelado
     */
    public boolean notifyOrderCancelled(Long orderId, Long clienteId) {
        String message = String.format("Seu pedido #%d foi cancelado", orderId);
        
        NotificationDto notification = new NotificationDto(
            nextNotificationId++,
            "email",
            message,
            LocalDateTime.now(),
            clienteId,
            orderId
        );
        
        notifications.put(notification.id(), notification);
        System.out.println("📧 Notificação de cancelamento enviada: " + message);
        return true;
    }
    
    /**
     * Envia notificação de pedido entregue
     */
    public boolean notifyOrderDelivered(Long orderId, Long clienteId) {
        String message = String.format("Seu pedido #%d foi entregue! Por favor, avalie sua experiência.", orderId);
        
        NotificationDto notification = new NotificationDto(
            nextNotificationId++,
            "email",
            message,
            LocalDateTime.now(),
            clienteId,
            orderId
        );
        
        notifications.put(notification.id(), notification);
        System.out.println("📧 Notificação de entrega enviada: " + message);
        return true;
    }
    
    /**
     * Envia notificação de pagamento aprovado
     */
    public boolean notifyPaymentApproved(Long orderId, Long clienteId) {
        String message = String.format("Pagamento do pedido #%d foi aprovado! Seu pedido está sendo preparado.", orderId);
        
        NotificationDto notification = new NotificationDto(
            nextNotificationId++,
            "email",
            message,
            LocalDateTime.now(),
            clienteId,
            orderId
        );
        
        notifications.put(notification.id(), notification);
        System.out.println("📧 Notificação de pagamento enviada: " + message);
        return true;
    }
    
    /**
     * Envia notificação de pagamento rejeitado
     */
    public boolean notifyPaymentRejected(Long orderId, Long clienteId) {
        String message = String.format("Pagamento do pedido #%d foi rejeitado. Tente novamente com outro método.", orderId);
        
        NotificationDto notification = new NotificationDto(
            nextNotificationId++,
            "email",
            message,
            LocalDateTime.now(),
            clienteId,
            orderId
        );
        
        notifications.put(notification.id(), notification);
        System.out.println("📧 Notificação de pagamento rejeitado enviada: " + message);
        return true;
    }
    
    /**
     * Obtém notificações de um cliente
     */
    public List<NotificationDto> getNotificationsByClient(Long clienteId) {
        return notifications.values().stream()
            .filter(notif -> notif.idCliente().equals(clienteId))
            .sorted((a, b) -> b.dataEnvio().compareTo(a.dataEnvio())) // Mais recentes primeiro
            .toList();
    }
    
    /**
     * Obtém notificações de um pedido
     */
    public List<NotificationDto> getNotificationsByOrder(Long orderId) {
        return notifications.values().stream()
            .filter(notif -> notif.idPedido().equals(orderId))
            .sorted((a, b) -> b.dataEnvio().compareTo(a.dataEnvio()))
            .toList();
    }
    
    /**
     * Marca notificação como lida (implementação básica)
     */
    public boolean markAsRead(Long notificationId) {
        NotificationDto notification = notifications.get(notificationId);
        if (notification != null) {
            System.out.println("📧 Notificação " + notificationId + " marcada como lida");
            return true;
        }
        return false;
    }
}
