package com.ecommerce.business;

import com.ecommerce.dto.NotificationDto;
import com.ecommerce.model.Notificacao;
import com.ecommerce.repository.NotificacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class NotificationBusinessHandlerImplTest {

    private NotificationBusinessHandlerImpl handler;
    
    @Mock
    private NotificacaoRepository notificacaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new NotificationBusinessHandlerImpl();
        // Usar reflection para injetar o mock
        try {
            java.lang.reflect.Field field = NotificationBusinessHandlerImpl.class.getDeclaredField("notificacaoRepository");
            field.setAccessible(true);
            field.set(handler, notificacaoRepository);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao injetar mock", e);
        }
    }

    @Test
    void create_ShouldReturnNotification_WhenValidData() {
        // Arrange
        NotificationDto notification = new NotificationDto(
            null, 1L, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(notificacaoRepository).save(any(Notificacao.class));
        
        // Act
        NotificationDto created = handler.create(notification);
        
        // Assert
        assertNotNull(created);
        assertEquals(1L, created.clienteId());
        assertEquals(1L, created.pedidoId());
        assertEquals("Título", created.titulo());
        assertEquals("Mensagem", created.mensagem());
        assertEquals("STATUS_PEDIDO", created.tipo());
        assertFalse(created.lida());
        verify(notificacaoRepository).save(any(Notificacao.class));
    }

    @Test
    void create_ShouldThrowException_WhenDataIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            handler.create(null);
        });
    }

    @Test
    void create_ShouldThrowException_WhenClienteIdIsNull() {
        NotificationDto notification = new NotificationDto(
            null, null, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        
        assertThrows(IllegalArgumentException.class, () -> {
            handler.create(notification);
        });
    }

    @Test
    void update_ShouldReturnUpdatedNotification() {
        // Arrange
        Notificacao notificacaoExistente = new Notificacao();
        notificacaoExistente.setId(1L);
        notificacaoExistente.setClienteId(1L);
        notificacaoExistente.setPedidoId(1L);
        notificacaoExistente.setTitulo("Título");
        notificacaoExistente.setMensagem("Mensagem");
        notificacaoExistente.setTipo("STATUS_PEDIDO");
        notificacaoExistente.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacaoExistente);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        doNothing().when(notificacaoRepository).saveAll(anyList());
        
        NotificationDto updatedNotification = new NotificationDto(
            1L, 1L, 1L, "Título Atualizado", "Mensagem Atualizada", "STATUS_PEDIDO", true, new Date(), new Date(), new Date());
        
        // Act
        NotificationDto result = handler.update(updatedNotification, 1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("Título Atualizado", result.titulo());
        assertEquals("Mensagem Atualizada", result.mensagem());
        assertTrue(result.lida());
        verify(notificacaoRepository).saveAll(anyList());
    }

    @Test
    void update_ShouldThrowException_WhenNotificationNotFound() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        
        NotificationDto notification = new NotificationDto(
            999L, 1L, 1L, "Título", "Mensagem", "STATUS_PEDIDO", false, new Date(), new Date(), new Date());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            handler.update(notification, 999L);
        });
    }

    @Test
    void delete_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Notificacao notificacaoExistente = new Notificacao();
        notificacaoExistente.setId(1L);
        notificacaoExistente.setClienteId(1L);
        notificacaoExistente.setPedidoId(1L);
        notificacaoExistente.setTitulo("Título");
        notificacaoExistente.setMensagem("Mensagem");
        notificacaoExistente.setTipo("STATUS_PEDIDO");
        notificacaoExistente.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacaoExistente);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        doNothing().when(notificacaoRepository).saveAll(anyList());
        
        // Act
        boolean deleted = handler.delete(1L);
        
        // Assert
        assertTrue(deleted);
        verify(notificacaoRepository).saveAll(anyList());
    }

    @Test
    void delete_ShouldReturnFalse_WhenNotificationNotFound() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        
        // Act
        boolean deleted = handler.delete(999L);
        
        // Assert
        assertFalse(deleted);
    }

    @Test
    void find_ShouldReturnNotification_WhenNotificationExists() {
        // Arrange
        Notificacao notificacaoExistente = new Notificacao();
        notificacaoExistente.setId(1L);
        notificacaoExistente.setClienteId(1L);
        notificacaoExistente.setPedidoId(1L);
        notificacaoExistente.setTitulo("Título");
        notificacaoExistente.setMensagem("Mensagem");
        notificacaoExistente.setTipo("STATUS_PEDIDO");
        notificacaoExistente.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacaoExistente);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        
        // Act
        NotificationDto found = handler.find(1L);
        
        // Assert
        assertNotNull(found);
        assertEquals(1L, found.id());
        assertEquals("Título", found.titulo());
    }

    @Test
    void find_ShouldReturnNull_WhenNotificationNotFound() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        
        // Act
        NotificationDto found = handler.find(999L);
        
        // Assert
        assertNull(found);
    }

    @Test
    void list_ShouldReturnAllNotifications() {
        // Arrange
        Notificacao notificacao1 = new Notificacao();
        notificacao1.setId(1L);
        notificacao1.setClienteId(1L);
        notificacao1.setPedidoId(1L);
        notificacao1.setTitulo("Título 1");
        notificacao1.setMensagem("Mensagem 1");
        notificacao1.setTipo("STATUS_PEDIDO");
        notificacao1.setLida(false);
        
        Notificacao notificacao2 = new Notificacao();
        notificacao2.setId(2L);
        notificacao2.setClienteId(2L);
        notificacao2.setPedidoId(2L);
        notificacao2.setTitulo("Título 2");
        notificacao2.setMensagem("Mensagem 2");
        notificacao2.setTipo("PAGAMENTO");
        notificacao2.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacao1);
        notificacoes.add(notificacao2);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        
        // Act
        List<NotificationDto> notifications = handler.list();
        
        // Assert
        assertNotNull(notifications);
        assertEquals(2, notifications.size());
    }

    @Test
    void getNotificationsByClient_ShouldReturnClientNotifications() {
        // Arrange
        Notificacao notificacao1 = new Notificacao();
        notificacao1.setId(1L);
        notificacao1.setClienteId(1L);
        notificacao1.setPedidoId(1L);
        notificacao1.setTitulo("Título 1");
        notificacao1.setMensagem("Mensagem 1");
        notificacao1.setTipo("STATUS_PEDIDO");
        notificacao1.setLida(false);
        
        Notificacao notificacao2 = new Notificacao();
        notificacao2.setId(2L);
        notificacao2.setClienteId(2L);
        notificacao2.setPedidoId(2L);
        notificacao2.setTitulo("Título 2");
        notificacao2.setMensagem("Mensagem 2");
        notificacao2.setTipo("PAGAMENTO");
        notificacao2.setLida(false);
        
        Notificacao notificacao3 = new Notificacao();
        notificacao3.setId(3L);
        notificacao3.setClienteId(1L);
        notificacao3.setPedidoId(3L);
        notificacao3.setTitulo("Título 3");
        notificacao3.setMensagem("Mensagem 3");
        notificacao3.setTipo("STATUS_PEDIDO");
        notificacao3.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacao1);
        notificacoes.add(notificacao2);
        notificacoes.add(notificacao3);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        
        // Act
        List<NotificationDto> clientNotifications = handler.getNotificationsByClient(1L);
        
        // Assert
        assertNotNull(clientNotifications);
        assertEquals(2, clientNotifications.size());
        
        // Verificar que todas as notificações são do cliente 1
        for (NotificationDto notification : clientNotifications) {
            assertEquals(1L, notification.clienteId());
        }
    }

    @Test
    void getUnreadNotifications_ShouldReturnUnreadNotifications() {
        // Arrange
        Notificacao notificacao1 = new Notificacao();
        notificacao1.setId(1L);
        notificacao1.setClienteId(1L);
        notificacao1.setPedidoId(1L);
        notificacao1.setTitulo("Título 1");
        notificacao1.setMensagem("Mensagem 1");
        notificacao1.setTipo("STATUS_PEDIDO");
        notificacao1.setLida(false);
        
        Notificacao notificacao2 = new Notificacao();
        notificacao2.setId(2L);
        notificacao2.setClienteId(1L);
        notificacao2.setPedidoId(2L);
        notificacao2.setTitulo("Título 2");
        notificacao2.setMensagem("Mensagem 2");
        notificacao2.setTipo("PAGAMENTO");
        notificacao2.setLida(true);
        
        Notificacao notificacao3 = new Notificacao();
        notificacao3.setId(3L);
        notificacao3.setClienteId(1L);
        notificacao3.setPedidoId(3L);
        notificacao3.setTitulo("Título 3");
        notificacao3.setMensagem("Mensagem 3");
        notificacao3.setTipo("STATUS_PEDIDO");
        notificacao3.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacao1);
        notificacoes.add(notificacao2);
        notificacoes.add(notificacao3);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        
        // Act
        List<NotificationDto> unreadNotifications = handler.getUnreadNotifications(1L);
        
        // Assert
        assertNotNull(unreadNotifications);
        assertEquals(2, unreadNotifications.size());
        
        // Verificar que todas as notificações não foram lidas
        for (NotificationDto notification : unreadNotifications) {
            assertFalse(notification.lida());
        }
    }

    @Test
    void markAsRead_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Notificacao notificacaoExistente = new Notificacao();
        notificacaoExistente.setId(1L);
        notificacaoExistente.setClienteId(1L);
        notificacaoExistente.setPedidoId(1L);
        notificacaoExistente.setTitulo("Título");
        notificacaoExistente.setMensagem("Mensagem");
        notificacaoExistente.setTipo("STATUS_PEDIDO");
        notificacaoExistente.setLida(false);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacaoExistente);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        doNothing().when(notificacaoRepository).saveAll(anyList());
        
        // Act
        boolean marked = handler.markAsRead(1L);
        
        // Assert
        assertTrue(marked);
        verify(notificacaoRepository).saveAll(anyList());
    }

    @Test
    void markAsRead_ShouldReturnFalse_WhenNotificationNotFound() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        
        // Act
        boolean marked = handler.markAsRead(999L);
        
        // Assert
        assertFalse(marked);
    }

    @Test
    void markAsUnread_ShouldReturnTrue_WhenNotificationExists() {
        // Arrange
        Notificacao notificacaoExistente = new Notificacao();
        notificacaoExistente.setId(1L);
        notificacaoExistente.setClienteId(1L);
        notificacaoExistente.setPedidoId(1L);
        notificacaoExistente.setTitulo("Título");
        notificacaoExistente.setMensagem("Mensagem");
        notificacaoExistente.setTipo("STATUS_PEDIDO");
        notificacaoExistente.setLida(true);
        
        List<Notificacao> notificacoes = new ArrayList<>();
        notificacoes.add(notificacaoExistente);
        
        when(notificacaoRepository.findAll()).thenReturn(notificacoes);
        doNothing().when(notificacaoRepository).saveAll(anyList());
        
        // Act
        boolean marked = handler.markAsUnread(1L);
        
        // Assert
        assertTrue(marked);
        verify(notificacaoRepository).saveAll(anyList());
    }

    @Test
    void markAsUnread_ShouldReturnFalse_WhenNotificationNotFound() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        
        // Act
        boolean marked = handler.markAsUnread(999L);
        
        // Assert
        assertFalse(marked);
    }

    @Test
    void sendOrderStatusNotification_ShouldCreateNotification() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(notificacaoRepository).save(any(Notificacao.class));
        
        // Act
        handler.sendOrderStatusNotification(1L, 1L, "CONFIRMADO");
        
        // Assert
        verify(notificacaoRepository).save(any(Notificacao.class));
    }

    @Test
    void sendPaymentNotification_ShouldCreateNotification() {
        // Arrange
        when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(notificacaoRepository).save(any(Notificacao.class));
        
        // Act
        handler.sendPaymentNotification(1L, 1L, "APROVADO");
        
        // Assert
        verify(notificacaoRepository).save(any(Notificacao.class));
    }
}
