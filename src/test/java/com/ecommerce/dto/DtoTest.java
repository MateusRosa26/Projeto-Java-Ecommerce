package com.ecommerce.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void productDto_ShouldCreateCorrectly() {
        ProductDto product = new ProductDto(1L, "Teclado", 199.99f);
        
        assertEquals(1L, product.id());
        assertEquals("Teclado", product.name());
        assertEquals(199.99f, product.price());
    }

    @Test
    void orderDto_ShouldCreateCorrectly() {
        Date now = new Date();
        ProductDto product = new ProductDto(1L, "Teclado", 199.99f);
        OrderDto order = new OrderDto(1L, "PED-001", now, 199.99f, "Comentário", Arrays.asList(product));
        
        assertEquals(1L, order.id());
        assertEquals("PED-001", order.numero());
        assertEquals(now, order.dataPedido());
        assertEquals(199.99f, order.total());
        assertEquals("Comentário", order.comment());
        assertEquals(1, order.products().size());
    }

    @Test
    void userDto_ShouldCreateCorrectly() {
        UserDto user = new UserDto(1L, "admin", "Administrador", "admin@example.com", null, null, null);
        
        assertEquals(1L, user.id());
        assertEquals("admin", user.username());
        assertEquals("Administrador", user.nome());
        assertEquals("admin@example.com", user.email());
    }

    @Test
    void addressDto_ShouldCreateCorrectly() {
        AddressDto address = new AddressDto("Rua A", "123", "Centro", "São Paulo", "SP", "Apto 1", "01234-567");
        
        assertEquals("Rua A", address.rua());
        assertEquals("123", address.numero());
        assertEquals("Centro", address.bairro());
        assertEquals("São Paulo", address.cidade());
        assertEquals("SP", address.estado());
        assertEquals("Apto 1", address.complement());
        assertEquals("01234-567", address.cep());
    }

    @Test
    void paymentDto_ShouldCreateCorrectly() {
        Date now = new Date();
        PaymentDto payment = new PaymentDto("Cartão", "Aprovado", now, 1, "João Silva", 1234567890123456L, 12, 2025);
        
        assertEquals("Cartão", payment.metodo());
        assertEquals("Aprovado", payment.status());
        assertEquals(now, payment.dataPagamento());
        assertEquals(1, payment.tentativas());
        assertEquals("João Silva", payment.nomeCartao());
        assertEquals(1234567890123456L, payment.numeroCartao());
        assertEquals(12, payment.mesValidade());
        assertEquals(2025, payment.anoValidade());
    }

    @Test
    void cartDto_ShouldCreateCorrectly() {
        Date now = new Date();
        CartDto cart = new CartDto(1L, now, "Ativo");
        
        assertEquals(1L, cart.id());
        assertEquals(now, cart.dataCriacao());
        assertEquals("Ativo", cart.status());
    }

    @Test
    void reportDto_ShouldCreateCorrectly() {
        Date start = new Date();
        Date end = new Date();
        ReportDto report = new ReportDto(1L, start, end, "1,2,3", 1500.0f);
        
        assertEquals(1L, report.id());
        assertEquals(start, report.dataInicio());
        assertEquals(end, report.dataFim());
        assertEquals("1,2,3", report.produtos());
        assertEquals(1500.0f, report.totalVendas());
    }
}
