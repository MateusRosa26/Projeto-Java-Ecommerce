package com.ecommerce.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportBusinessHandlerImplTest {

    private ReportBusinessHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new ReportBusinessHandlerImpl();
        // Reset do contador de relatórios para testes consistentes
        resetReportCounter();
    }
    
    private void resetReportCounter() {
        try {
            java.lang.reflect.Field field = ReportBusinessHandlerImpl.class.getDeclaredField("nextReportId");
            field.setAccessible(true);
            field.set(null, 1L);
        } catch (Exception e) {
            // Ignore reflection errors
        }
    }

    @Test
    void generateReport_ShouldReturnBufferedReader_WhenValidParameters() throws IOException {
        Date start = new Date();
        Date end = new Date();
        List<Integer> products = Arrays.asList(1, 2, 3);

        BufferedReader reader = handler.generateReport(start, end, products);
        
        assertNotNull(reader);
        String content = reader.readLine();
        assertNotNull(content);
        assertTrue(content.contains("RELATÓRIO DE VENDAS"));
        
        // Ler as linhas na ordem correta
        reader.readLine(); // Linha de separação "====="
        reader.readLine(); // Linha do período
        String productsLine = reader.readLine(); // Linha dos produtos
        assertNotNull(productsLine);
        assertTrue(productsLine.contains("Produtos filtrados: 3"));
        
        reader.close();
    }

    @Test
    void generateReport_ShouldReturnBufferedReader_WhenEmptyProducts() throws IOException {
        Date start = new Date();
        Date end = new Date();
        List<Integer> products = new ArrayList<>();

        BufferedReader reader = handler.generateReport(start, end, products);
        
        assertNotNull(reader);
        String content = reader.readLine();
        assertNotNull(content);
        assertTrue(content.contains("RELATÓRIO DE VENDAS"));
        
        // Ler as linhas na ordem correta
        reader.readLine(); // Linha de separação "====="
        reader.readLine(); // Linha do período
        String productsLine = reader.readLine(); // Linha dos produtos
        assertNotNull(productsLine);
        assertTrue(productsLine.contains("Produtos filtrados: 0"));
        
        reader.close();
    }

    @Test
    void create_ShouldReturnSameObject() {
        Date start = new Date();
        Date end = new Date();
        var report = new com.ecommerce.dto.ReportDto(1L, start, end, "1,2,3", 1500.0f);
        
        var created = handler.create(report);
        
        assertNotNull(created);
        assertEquals(report.id(), created.id());
        assertEquals(report.totalVendas(), created.totalVendas());
    }
}
