package com.ecommerce.model;

import com.ecommerce.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class PagamentoTest {

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
    }

    @Test
    void csvConversionRoundTrip() {
        Pagamento original = new Pagamento(1L, 1L, PaymentMethod.PIX, 100.0, "PENDENTE", "TXN123", new Date());
        String[] csv = original.toCsvRow();
        Pagamento parsed = Pagamento.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getPedidoId(), parsed.getPedidoId());
        assertEquals(original.getMetodoPagamento(), parsed.getMetodoPagamento());
        assertEquals(original.getValor(), parsed.getValor(), 0.01);
        assertEquals(original.getStatus(), parsed.getStatus());
        assertEquals(original.getCodigoTransacao(), parsed.getCodigoTransacao());
    }

    @Test
    void gettersAndSetters() {
        pagamento.setId(1L);
        pagamento.setPedidoId(1L);
        pagamento.setMetodoPagamento(PaymentMethod.CARTAO_CREDITO);
        pagamento.setValor(150.0);
        pagamento.setStatus("APROVADO");
        pagamento.setCodigoTransacao("TXN456");
        pagamento.setDataPagamento(new Date());
        pagamento.setCreatedAt(new Date());
        pagamento.setUpdatedAt(new Date());

        assertEquals(1L, pagamento.getId());
        assertEquals(1L, pagamento.getPedidoId());
        assertEquals(PaymentMethod.CARTAO_CREDITO, pagamento.getMetodoPagamento());
        assertEquals(150.0, pagamento.getValor(), 0.01);
        assertEquals("APROVADO", pagamento.getStatus());
        assertEquals("TXN456", pagamento.getCodigoTransacao());
        assertNotNull(pagamento.getDataPagamento());
        assertNotNull(pagamento.getCreatedAt());
        assertNotNull(pagamento.getUpdatedAt());
    }

    @Test
    void processarPagamento() {
        pagamento.setStatus("PENDENTE");
        pagamento.processarPagamento();
        
        assertTrue("PROCESSANDO".equals(pagamento.getStatus()) || "APROVADO".equals(pagamento.getStatus()) || "REJEITADO".equals(pagamento.getStatus()));
        assertNotNull(pagamento.getUpdatedAt());
    }

    @Test
    void confirmarPagamento() {
        pagamento.setStatus("PROCESSANDO");
        pagamento.confirmarPagamento("TXN789");
        
        assertEquals("APROVADO", pagamento.getStatus());
        assertEquals("TXN789", pagamento.getCodigoTransacao());
        assertNotNull(pagamento.getDataPagamento());
        assertNotNull(pagamento.getUpdatedAt());
    }

    @Test
    void rejeitarPagamento() {
        pagamento.setStatus("PROCESSANDO");
        pagamento.rejeitarPagamento();
        
        assertEquals("REJEITADO", pagamento.getStatus());
        assertNotNull(pagamento.getUpdatedAt());
    }

    @Test
    void isAprovado() {
        pagamento.setStatus("APROVADO");
        assertTrue(pagamento.isAprovado());

        pagamento.setStatus("REJEITADO");
        assertFalse(pagamento.isAprovado());

        pagamento.setStatus("PENDENTE");
        assertFalse(pagamento.isAprovado());
    }

    @Test
    void csvHeader() {
        String[] header = Pagamento.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "1", "0", "100.0", "PENDENTE", "", "", "2023-01-01 10:00:00", "2023-01-01 10:00:00"};
        Pagamento parsed = Pagamento.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals(1L, parsed.getPedidoId());
        assertEquals(PaymentMethod.PIX, parsed.getMetodoPagamento());
        assertEquals(100.0, parsed.getValor(), 0.01);
        assertEquals("PENDENTE", parsed.getStatus());
        assertEquals("", parsed.getCodigoTransacao());
    }

    @Test
    void defaultConstructor() {
        Pagamento pagamento = new Pagamento();
        assertNotNull(pagamento.getCreatedAt());
        assertNotNull(pagamento.getUpdatedAt());
    }
}
