package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void productDto_ShouldCreateCorrectly() {
        ProductDto product = new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        
        assertEquals(1L, product.id());
        assertEquals("Teclado", product.name());
        assertEquals(199.99f, product.price());
    }

    @Test
    void orderDto_ShouldCreateCorrectly() {
        Date now = new Date();
        ProductDto product = new ProductDto(1L, "Teclado", 199.99f, "Teclado mecânico", "teclado.jpg", "1234567890123", true, new Date(), new Date());
        OrderDto order = new OrderDto(1L, "PED-001", now, OrderStatus.PREPARANDO, 199.99f, "Comentário", 5, 1L, Arrays.asList(product), new ArrayList<>(), null, null, new Date(), new Date());
        
        assertEquals(1L, order.id());
        assertEquals("PED-001", order.numero());
        assertEquals(now, order.dataPedido());
        assertEquals(199.99f, order.total());
        assertEquals("Comentário", order.comment());
        assertEquals(1, order.products().size());
    }

    @Test
    void userDto_ShouldCreateCorrectly() {
        UserDto user = new UserDto(1L, "admin", "Administrador", "admin@example.com", "11999999999", "São Paulo", new Date(), UserRole.CLIENTE, null, new ArrayList<>(), null, new Date(), new Date());
        
        assertEquals(1L, user.id());
        assertEquals("admin", user.username());
        assertEquals("Administrador", user.nome());
        assertEquals("admin@example.com", user.email());
    }

    @Test
    void addressDto_ShouldCreateCorrectly() {
        AddressDto address = new AddressDto(1L, "Rua A", "123", "Centro", "São Paulo", "SP", "Apto 1", "01234-567", 1L, new Date(), new Date());
        
        assertEquals("Rua A", address.rua());
        assertEquals("123", address.numero());
        assertEquals("Centro", address.bairro());
        assertEquals("São Paulo", address.cidade());
        assertEquals("SP", address.estado());
        assertEquals("Apto 1", address.complemento());
        assertEquals("01234-567", address.cep());
    }

    @Test
    void paymentDto_ShouldCreateCorrectly() {
        Date now = new Date();
        PaymentDto payment = new PaymentDto(1L, 1L, PaymentMethod.CARTAO_CREDITO, 199.99, "Aprovado", "TXN123", now, new Date(), new Date());
        
        assertEquals(PaymentMethod.CARTAO_CREDITO, payment.metodoPagamento());
        assertEquals("Aprovado", payment.status());
        assertEquals(now, payment.dataPagamento());
        assertEquals(199.99, payment.valor());
    }

    @Test
    void cartDto_ShouldCreateCorrectly() {
        Date now = new Date();
        CartDto cart = new CartDto(1L, 1L, new ArrayList<>(), 199.99, 1, now, new Date());
        
        assertEquals(1L, cart.id());
        assertEquals(1L, cart.clienteId());
        assertEquals(199.99, cart.total());
        assertEquals(1, cart.quantidadeTotal());
    }

    @Test
    void reportDto_ShouldCreateCorrectly() {
        Date start = new Date();
        Date end = new Date();
        ReportDto report = new ReportDto(1L, start, end, "1,2,3", 1500.0, 10, 5, new ArrayList<>(), new ArrayList<>(), new Date());
        
        assertEquals(1L, report.id());
        assertEquals(start, report.dataInicio());
        assertEquals(end, report.dataFim());
        assertEquals("1,2,3", report.produtoBarcode());
        assertEquals(1500.0, report.totalVendas());
    }
}
