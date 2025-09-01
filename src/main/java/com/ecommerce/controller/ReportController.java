package com.ecommerce.controller;

import com.ecommerce.business.ReportBusinessHandlerImpl;
import com.ecommerce.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Controller REST para operações de relatórios
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController extends AbstractController<ReportDto> {

    private final ReportBusinessHandlerImpl reportHandler;

    @Autowired
    public ReportController(ReportBusinessHandlerImpl reportHandler) {
        this.reportHandler = reportHandler;
    }

    /**
     * Gera relatório de vendas
     * Baseado no diagrama: generate(Date start, Date end, List<Long> products): ResponseEntity<BufferedReader>
     */
    @GetMapping("/generate")
    public ResponseEntity<String> generateReport(
            @RequestParam Date start,
            @RequestParam Date end,
            @RequestParam List<Long> products) {
        try {
            BufferedReader reader = reportHandler.generateReport(start, end, products.stream().map(Long::intValue).toList());
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return ResponseEntity.ok(content.toString());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    /**
     * Gera relatório de vendas com parâmetros opcionais
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateReportPost(@RequestBody ReportRequest request) {
        try {
            BufferedReader reader = reportHandler.generateReport(request.start, request.end, request.products);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return ResponseEntity.ok(content.toString());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    /**
     * Gera relatório de vendas por período
     */
    @GetMapping("/period")
    public ResponseEntity<ReportDto> generateSalesReportByPeriod(
            @RequestParam Date start,
            @RequestParam Date end) {
        ReportDto report = reportHandler.generateSalesReportByPeriod(start, end);
        return ResponseEntity.ok(report);
    }
    
    /**
     * Gera relatório de vendas por produto (código de barras)
     */
    @GetMapping("/product/{barcode}")
    public ResponseEntity<ReportDto> generateSalesReportByProduct(@PathVariable String barcode) {
        ReportDto report = reportHandler.generateSalesReportByProduct(barcode);
        return ResponseEntity.ok(report);
    }
    
    /**
     * Gera relatório de performance de entregadores
     */
    @GetMapping("/delivery-performance")
    public ResponseEntity<ReportDto> generateDeliveryPerformanceReport() {
        ReportDto report = reportHandler.generateDeliveryPerformanceReport();
        return ResponseEntity.ok(report);
    }
    
    /**
     * Obtém relatórios por período
     */
    @GetMapping("/by-period")
    public ResponseEntity<List<ReportDto>> getReportsByPeriod(
            @RequestParam Date start,
            @RequestParam Date end) {
        List<ReportDto> reports = reportHandler.getReportsByPeriod(start, end);
        return ResponseEntity.ok(reports);
    }
    
    /**
     * Lista todos os relatórios disponíveis
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReportDto>> listReports() {
        List<ReportDto> reports = reportHandler.list();
        return ResponseEntity.ok(reports);
    }

    // Classe interna para request body
    public static class ReportRequest {
        public Date start;
        public Date end;
        public List<Integer> products;
    }

    // Implementações dos métodos abstratos
    @Override
    public ResponseEntity<List<ReportDto>> get() {
        List<ReportDto> reports = reportHandler.list();
        return ResponseEntity.ok(reports);
    }

    @Override
    public ResponseEntity<ReportDto> get(@PathVariable Long id) {
        ReportDto report = reportHandler.find(id);
        if (report != null) {
            return ResponseEntity.ok(report);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ReportDto> post(@RequestBody ReportDto report) {
        ReportDto created = reportHandler.create(report);
        if (created != null) {
            return ResponseEntity.status(201).body(created);
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<ReportDto> put(@PathVariable Long id, @RequestBody ReportDto report) {
        ReportDto updated = reportHandler.update(report, id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = reportHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
