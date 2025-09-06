package com.ecommerce.controller;

import com.ecommerce.business.NotificationBusinessHandlerImpl;
import com.ecommerce.dto.NotificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

class NotificationControllerTest {

    private NotificationController controller;
    private NotificationBusinessHandlerImpl mockHandler;

    @BeforeEach
    void setUp() {
        mockHandler = mock(NotificationBusinessHandlerImpl.class);
        controller = new NotificationController(mockHandler);
    }

    @Test
    void get_ShouldReturnAllNotifications() {
        // Arrange
        List<NotificationDto> notifications = List.of(
            new NotificationDto(1L, 1L, 1L, "Título 1", "Mensagem 1", "STATUS_PEDIDO", false, new Date(), new Date(), new Date()),
            new NotificationDto(2L, 2L, 2L, "Título 2", "Mensagem 2", "PAGAMENTO", false, new Date(), new Date(), new Date())
        );
        when(mockHandler.list()).thenReturn(notifications);

        // Act
        ResponseEntity<List<NotificationDto>> response = controller.get();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(mockHandler).list();
    }

    @Test
    void get_ShouldReturnNotification_WhenIdExists() {
        // Arrange
        Long id = 1L;
        NotificationDto notification = new NotificationDto(
            id, 1L, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        when(mockHandler.find(id)).thenReturn(notification);

        // Act
        ResponseEntity<NotificationDto> response = controller.get(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        verify(mockHandler).find(id);
    }

    @Test
    void get_ShouldReturnNotFound_WhenIdDoesNotExist() {
        // Arrange
        Long id = 999L;
        when(mockHandler.find(id)).thenReturn(null);

        // Act
        ResponseEntity<NotificationDto> response = controller.get(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(mockHandler).find(id);
    }

    @Test
    void post_ShouldReturnCreatedNotification() {
        // Arrange
        NotificationDto notification = new NotificationDto(
            null, 1L, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        NotificationDto created = new NotificationDto(
            1L, 1L, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        when(mockHandler.create(notification)).thenReturn(created);

        // Act
        ResponseEntity<NotificationDto> response = controller.post(notification);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        verify(mockHandler).create(notification);
    }

    @Test
    void put_ShouldReturnUpdatedNotification() {
        // Arrange
        Long id = 1L;
        NotificationDto notification = new NotificationDto(
            id, 1L, 1L, "Título Atualizado", "Mensagem Atualizada", "STATUS_PEDIDO", true, new Date(), new Date(), new Date());
        when(mockHandler.update(notification, id)).thenReturn(notification);

        // Act
        ResponseEntity<NotificationDto> response = controller.put(id, notification);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Título Atualizado", response.getBody().titulo());
        verify(mockHandler).update(notification, id);
    }

    @Test
    void delete_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Long id = 1L;
        when(mockHandler.delete(id)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).delete(id);
    }

    @Test
    void delete_ShouldReturnFalse_WhenNotificationDoesNotExist() {
        // Arrange
        Long id = 999L;
        when(mockHandler.delete(id)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(mockHandler).delete(id);
    }

    @Test
    void getNotificationsByClient_ShouldReturnClientNotifications() {
        // Arrange
        Long clienteId = 1L;
        List<NotificationDto> notifications = List.of(
            new NotificationDto(1L, clienteId, 1L, "Título 1", "Mensagem 1", "STATUS_PEDIDO", false, new Date(), new Date(), new Date()),
            new NotificationDto(2L, clienteId, 2L, "Título 2", "Mensagem 2", "PAGAMENTO", false, new Date(), new Date(), new Date())
        );
        when(mockHandler.getNotificationsByClient(clienteId)).thenReturn(notifications);

        // Act
        ResponseEntity<List<NotificationDto>> response = controller.getNotificationsByClient(clienteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(mockHandler).getNotificationsByClient(clienteId);
    }

    @Test
    void getUnreadNotifications_ShouldReturnUnreadNotifications() {
        // Arrange
        Long clienteId = 1L;
        List<NotificationDto> notifications = List.of(
            new NotificationDto(1L, clienteId, 1L, "Título 1", "Mensagem 1", "STATUS_PEDIDO", false, new Date(), new Date(), new Date())
        );
        when(mockHandler.getUnreadNotifications(clienteId)).thenReturn(notifications);

        // Act
        ResponseEntity<List<NotificationDto>> response = controller.getUnreadNotifications(clienteId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertFalse(response.getBody().get(0).lida());
        verify(mockHandler).getUnreadNotifications(clienteId);
    }

    @Test
    void markAsRead_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Long id = 1L;
        when(mockHandler.markAsRead(id)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = controller.markAsRead(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).markAsRead(id);
    }

    @Test
    void markAsUnread_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Long id = 1L;
        when(mockHandler.markAsUnread(id)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = controller.markAsUnread(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).markAsUnread(id);
    }

    @Test
    void sendOrderStatusNotification_ShouldReturnTrue_WhenSuccessful() {
        // Arrange
        Long clienteId = 1L;
        Long pedidoId = 1L;
        String status = "CONFIRMADO";

        // Act
        ResponseEntity<Boolean> response = controller.sendOrderStatusNotification(clienteId, pedidoId, status);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).sendOrderStatusNotification(clienteId, pedidoId, status);
    }

    @Test
    void sendPaymentNotification_ShouldReturnTrue_WhenSuccessful() {
        // Arrange
        Long clienteId = 1L;
        Long pedidoId = 1L;
        String status = "APROVADO";

        // Act
        ResponseEntity<Boolean> response = controller.sendPaymentNotification(clienteId, pedidoId, status);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mockHandler).sendPaymentNotification(clienteId, pedidoId, status);
    }
}
