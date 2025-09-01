package com.ecommerce.dto;

import com.ecommerce.enums.UserRole;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

class DtoTest {

    @Test
    void testUserDto() {
        UserDto user = new UserDto(1L, "testuser", "Test User", "test@email.com", 
            "ValidPass@123", "11999999999", UserRole.CLIENTE.getDescription(), null, null, null);
        
        assertEquals(1L, user.id());
        assertEquals("testuser", user.username());
        assertEquals("Test User", user.nome());
        assertEquals("test@email.com", user.email());
        assertEquals("ValidPass@123", user.senha());
        assertEquals("11999999999", user.telefone());
        assertEquals(UserRole.CLIENTE.getDescription(), user.role());
    }

    @Test
    void testOrderDto() {
        OrderDto order = new OrderDto(1L, "PED123", OrderStatus.PREPARANDO.getDescription(), 
            new Date(), 100.0f, "Comentário", 5, 1L, List.of());
        
        assertEquals(1L, order.id());
        assertEquals("PED123", order.numero());
        assertEquals(OrderStatus.PREPARANDO.getDescription(), order.status());
        assertEquals(100.0f, order.total());
        assertEquals("Comentário", order.comment());
        assertEquals(5, order.rating());
        assertEquals(1L, order.idCliente());
    }

    @Test
    void testProductDto() {
        ProductDto product = new ProductDto(1L, "Produto Teste", 50.0f, "1234567890123", "Descrição do produto", "produto.jpg");
        
        assertEquals(1L, product.id());
        assertEquals("Produto Teste", product.name());
        assertEquals(50.0f, product.price());
        assertEquals("1234567890123", product.barcode());
        assertEquals("Descrição do produto", product.description());
        assertEquals("produto.jpg", product.imageUrl());
    }

    @Test
    void testAddressDto() {
        AddressDto address = new AddressDto("Rua das Flores", "123", "Centro", 
            "São Paulo", "SP", "Apto 1", "01234-567");
        
        assertEquals("Rua das Flores", address.rua());
        assertEquals("123", address.numero());
        assertEquals("Centro", address.bairro());
        assertEquals("São Paulo", address.cidade());
        assertEquals("SP", address.estado());
        assertEquals("Apto 1", address.complement());
        assertEquals("01234-567", address.cep());
    }

    @Test
    void testPaymentDto() {
        PaymentDto payment = new PaymentDto(1L, PaymentMethod.PIX.getDescription(), 
            "Sucesso", LocalDateTime.now(), 1, "João Silva", 1234567890123456L, 12, 2025);
        
        assertEquals(1L, payment.id());
        assertEquals(PaymentMethod.PIX.getDescription(), payment.metodo());
        assertEquals("Sucesso", payment.status());
        assertNotNull(payment.dataPagamento());
        assertEquals(1, payment.tentativas());
        assertEquals("João Silva", payment.nomeCartao());
        assertEquals(1234567890123456L, payment.numeroCartao());
        assertEquals(12, payment.mesValidade());
        assertEquals(2025, payment.anoValidade());
    }

    @Test
    void testEvaluationDto() {
        EvaluationDto evaluation = new EvaluationDto(1L, 5, "Excelente produto!", 
            LocalDateTime.now(), 1L, 1L, 1L, 1L);
        
        assertEquals(1L, evaluation.id());
        assertEquals(5, evaluation.nota());
        assertEquals("Excelente produto!", evaluation.comentario());
        assertNotNull(evaluation.dataAvaliacao());
        assertEquals(1L, evaluation.idCliente());
        assertEquals(1L, evaluation.idPedido());
        assertEquals(1L, evaluation.idEntregador());
        assertEquals(1L, evaluation.idProduto());
    }

    @Test
    void testNotificationDto() {
        NotificationDto notification = new NotificationDto(1L, "email", 
            "Seu pedido foi enviado!", LocalDateTime.now(), 1L, 1L);
        
        assertEquals(1L, notification.id());
        assertEquals("email", notification.tipo());
        assertEquals("Seu pedido foi enviado!", notification.mensagem());
        assertNotNull(notification.dataEnvio());
        assertEquals(1L, notification.idCliente());
        assertEquals(1L, notification.idPedido());
    }

    @Test
    void testReportDto() {
        ReportDto report = new ReportDto(1L, new Date(), new Date(), 
            "Produtos vendidos", 1000.0f);
        
        assertEquals(1L, report.id());
        assertNotNull(report.dataInicio());
        assertNotNull(report.dataFim());
        assertEquals("Produtos vendidos", report.produtos());
        assertEquals(1000.0f, report.totalVendas());
    }
}