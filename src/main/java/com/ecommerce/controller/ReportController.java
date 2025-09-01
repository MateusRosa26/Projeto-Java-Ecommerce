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

    // Classe interna para request body
    public static class ReportRequest {
        public Date start;
        public Date end;
        public List<Integer> products;
    }

    // Implementações dos métodos abstratos (básicas para este exemplo)
    @Override
    public ResponseEntity<List<ReportDto>> get() {
        // Implementação básica
        return ResponseEntity.ok(List.of());
    }

    @Override
    public ResponseEntity<ReportDto> get(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(new ReportDto(id, new Date(), new Date(), "1,2,3", 1500.0f));
    }

    @Override
    public ResponseEntity<ReportDto> post(@RequestBody ReportDto report) {
        // Implementação básica
        return ResponseEntity.ok(report);
    }

    @Override
    public ResponseEntity<ReportDto> put(@PathVariable Long id, @RequestBody ReportDto report) {
        // Implementação básica
        return ResponseEntity.ok(report);
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        // Implementação básica
        return ResponseEntity.ok(true);
    }
}
