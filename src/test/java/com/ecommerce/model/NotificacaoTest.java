package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class NotificacaoTest {

    private Notificacao notificacao;

    @BeforeEach
    void setUp() {
        notificacao = new Notificacao();
    }

    @Test
    void csvConversionRoundTrip() {
        Notificacao original = new Notificacao();
        original.setId(1L);
        original.setClienteId(1L);
        original.setPedidoId(1L);
        original.setTitulo("Título");
        original.setMensagem("Mensagem");
        original.setTipo("STATUS_PEDIDO");
        original.setLida(false);
        
        String[] csv = original.toCsvRow();
        Notificacao parsed = Notificacao.fromCsv(csv);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getClienteId(), parsed.getClienteId());
        assertEquals(original.getPedidoId(), parsed.getPedidoId());
        assertEquals(original.getTitulo(), parsed.getTitulo());
        assertEquals(original.getMensagem(), parsed.getMensagem());
        assertEquals(original.getTipo(), parsed.getTipo());
        assertEquals(original.isLida(), parsed.isLida());
    }

    @Test
    void gettersAndSetters() {
        notificacao.setId(1L);
        notificacao.setClienteId(1L);
        notificacao.setPedidoId(1L);
        notificacao.setTitulo("Título Teste");
        notificacao.setMensagem("Mensagem de teste");
        notificacao.setTipo("PAGAMENTO");
        notificacao.setLida(false);
        notificacao.setDataEnvio(new Date());
        notificacao.setCreatedAt(new Date());
        notificacao.setUpdatedAt(new Date());

        assertEquals(1L, notificacao.getId());
        assertEquals(1L, notificacao.getClienteId());
        assertEquals(1L, notificacao.getPedidoId());
        assertEquals("Título Teste", notificacao.getTitulo());
        assertEquals("Mensagem de teste", notificacao.getMensagem());
        assertEquals("PAGAMENTO", notificacao.getTipo());
        assertFalse(notificacao.isLida());
        assertNotNull(notificacao.getDataEnvio());
        assertNotNull(notificacao.getCreatedAt());
        assertNotNull(notificacao.getUpdatedAt());
    }

    @Test
    void marcarComoLida() {
        notificacao.setLida(false);
        notificacao.marcarComoLida();
        
        assertTrue(notificacao.isLida());
        assertNotNull(notificacao.getUpdatedAt());
    }

    @Test
    void marcarComoNaoLida() {
        notificacao.setLida(true);
        notificacao.marcarComoNaoLida();
        
        assertFalse(notificacao.isLida());
        assertNotNull(notificacao.getUpdatedAt());
    }

    @Test
    void getResumo() {
        notificacao.setTitulo("Pedido Atualizado");
        notificacao.setMensagem("Seu pedido foi confirmado");
        
        String resumo = notificacao.getResumo();
        assertEquals("Pedido Atualizado: Seu pedido foi confirmado", resumo);
    }

    @Test
    void csvHeader() {
        String[] header = Notificacao.csvHeader();
        assertNotNull(header);
        assertTrue(header.length > 0);
        assertEquals("id", header[0]);
    }

    @Test
    void fromCsvWithNullValues() {
        String[] csv = {"1", "1", "1", "Título", "Mensagem", "TIPO", "false", "1234567890", "1234567890", "1234567890"};
        Notificacao parsed = Notificacao.fromCsv(csv);
        
        assertNotNull(parsed);
        assertEquals(1L, parsed.getId());
        assertEquals(1L, parsed.getClienteId());
        assertEquals(1L, parsed.getPedidoId());
        assertEquals("Título", parsed.getTitulo());
        assertEquals("Mensagem", parsed.getMensagem());
        assertEquals("TIPO", parsed.getTipo());
        assertFalse(parsed.isLida());
    }

    @Test
    void defaultConstructor() {
        Notificacao notificacao = new Notificacao();
        assertNotNull(notificacao.getCreatedAt());
        assertNotNull(notificacao.getUpdatedAt());
        assertNotNull(notificacao.getDataEnvio());
        assertFalse(notificacao.isLida());
    }

    @Test
    void constructorWithParameters() {
        Notificacao notificacao = new Notificacao(1L, 1L, "Título", "Mensagem", "TIPO");
        
        assertEquals(1L, notificacao.getClienteId());
        assertEquals(1L, notificacao.getPedidoId());
        assertEquals("Título", notificacao.getTitulo());
        assertEquals("Mensagem", notificacao.getMensagem());
        assertEquals("TIPO", notificacao.getTipo());
        assertFalse(notificacao.isLida());
        assertNotNull(notificacao.getDataEnvio());
        assertNotNull(notificacao.getCreatedAt());
        assertNotNull(notificacao.getUpdatedAt());
    }
}
