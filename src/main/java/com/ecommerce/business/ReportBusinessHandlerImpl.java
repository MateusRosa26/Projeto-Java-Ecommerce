package com.ecommerce.business;

import com.ecommerce.dto.ReportDto;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ReportBusinessHandlerImpl implements BusinessHandler<ReportDto> {
    
    // Armazenamento temporário de relatórios (em produção seria um banco de dados)
    private static final Map<Long, ReportDto> reports = new ConcurrentHashMap<>();
    private static long nextReportId = 1L;
    
    // Dados simulados para relatórios
    private static final List<ProductDto> sampleProducts = createSampleProducts();
    private static final List<OrderDto> sampleOrders = createSampleOrders();

    @Override
    public ReportDto create(ReportDto object) {
        if (object != null) {
            ReportDto newReport = new ReportDto(
                nextReportId++,
                object.dataInicio(),
                object.dataFim(),
                object.produtos(),
                object.totalVendas()
            );
            reports.put(newReport.id(), newReport);
            System.out.println("📊 Relatório criado: " + newReport.id());
            return newReport;
        }
        return null;
    }

    @Override
    public ReportDto update(ReportDto object, long id) {
        if (reports.containsKey(id)) {
            reports.put(id, object);
            return object;
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return reports.remove(id) != null;
    }

    @Override
    public ReportDto find(long id) {
        return reports.get(id);
    }

    @Override
    public List<ReportDto> list() {
        return new ArrayList<>(reports.values());
    }

    @Override
    public List<ReportDto> list(String field, String filter, boolean exact) {
        return reports.values().stream()
            .filter(report -> {
                if ("produtos".equals(field.toLowerCase())) {
                    return exact ? report.produtos().equals(filter)
                               : report.produtos().toLowerCase().contains(filter.toLowerCase());
                }
                return false;
            })
            .toList();
    }

    /**
     * Gera relatório de vendas com filtros avançados
     */
    public BufferedReader generateReport(Date start, Date end, List<Integer> products) {
        LocalDateTime startDate = convertToLocalDateTime(start);
        LocalDateTime endDate = convertToLocalDateTime(end);
        
        // Filtrar pedidos por período
        List<OrderDto> filteredOrders = sampleOrders.stream()
            .filter(order -> isOrderInPeriod(order, startDate, endDate))
            .filter(order -> isOrderInProducts(order, products))
            .toList();
        
        // Gerar conteúdo do relatório
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("📊 RELATÓRIO DE VENDAS\n");
        reportContent.append("=" .repeat(50)).append("\n");
        reportContent.append("Período: ").append(formatDate(startDate)).append(" a ").append(formatDate(endDate)).append("\n");
        reportContent.append("Produtos filtrados: ").append(products.size()).append("\n");
        reportContent.append("Total de pedidos: ").append(filteredOrders.size()).append("\n\n");
        
        // Estatísticas por status
        Map<String, Long> statusCount = filteredOrders.stream()
            .collect(Collectors.groupingBy(OrderDto::status, Collectors.counting()));
        
        reportContent.append("📈 PEDIDOS POR STATUS:\n");
        statusCount.forEach((status, count) -> 
            reportContent.append("  ").append(status).append(": ").append(count).append("\n"));
        
        // Estatísticas por produto
        Map<String, Integer> productCount = new HashMap<>();
        Map<String, Double> productRevenue = new HashMap<>();
        
        for (OrderDto order : filteredOrders) {
            for (ProductDto product : order.products()) {
                String productName = product.name();
                productCount.merge(productName, 1, Integer::sum);
                productRevenue.merge(productName, (double) product.price(), Double::sum);
            }
        }
        
        reportContent.append("\n📦 PRODUTOS MAIS VENDIDOS:\n");
        productCount.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> 
                reportContent.append("  ").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append(" unidades\n"));
        
        reportContent.append("\n💰 RECEITA POR PRODUTO:\n");
        productRevenue.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> 
                reportContent.append("  ").append(entry.getKey())
                    .append(": R$ ").append(String.format("%.2f", entry.getValue())).append("\n"));
        
        // Total geral
        double totalRevenue = filteredOrders.stream()
            .mapToDouble(OrderDto::total)
            .sum();
        
        reportContent.append("\n💵 TOTAL GERAL: R$ ").append(String.format("%.2f", totalRevenue)).append("\n");
        
        // Salvar relatório
        ReportDto report = new ReportDto(
            nextReportId++,
            start,
            end,
            "Produtos: " + products.size(),
            (float) totalRevenue
        );
        reports.put(report.id(), report);
        
        return new BufferedReader(new StringReader(reportContent.toString()));
    }
    
    /**
     * Gera relatório de vendas por período
     */
    public ReportDto generateSalesReportByPeriod(Date start, Date end) {
        LocalDateTime startDate = convertToLocalDateTime(start);
        LocalDateTime endDate = convertToLocalDateTime(end);
        
        List<OrderDto> filteredOrders = sampleOrders.stream()
            .filter(order -> isOrderInPeriod(order, startDate, endDate))
            .toList();
        
        double totalRevenue = filteredOrders.stream()
            .mapToDouble(OrderDto::total)
            .sum();
        
        ReportDto report = new ReportDto(
            nextReportId++,
            start,
            end,
            "Relatório por período",
            (float) totalRevenue
        );
        
        reports.put(report.id(), report);
        System.out.println("📊 Relatório de vendas por período gerado: " + report.id());
        return report;
    }
    
    /**
     * Gera relatório de vendas por produto
     */
    public ReportDto generateSalesReportByProduct(String productBarcode) {
        List<OrderDto> filteredOrders = sampleOrders.stream()
            .filter(order -> order.products().stream()
                .anyMatch(product -> product.barcode() != null && product.barcode().equals(productBarcode)))
            .toList();
        
        double totalRevenue = filteredOrders.stream()
            .mapToDouble(OrderDto::total)
            .sum();
        
        ReportDto report = new ReportDto(
            nextReportId++,
            new Date(),
            new Date(),
            "Produto: " + productBarcode,
            (float) totalRevenue
        );
        
        reports.put(report.id(), report);
        System.out.println("📊 Relatório de vendas por produto gerado: " + report.id());
        return report;
    }
    
    /**
     * Gera relatório de performance de entregadores
     */
    public ReportDto generateDeliveryPerformanceReport() {
        // Simular dados de entregadores
        Map<String, Integer> deliveryCount = new HashMap<>();
        deliveryCount.put("Entregador A", 15);
        deliveryCount.put("Entregador B", 12);
        deliveryCount.put("Entregador C", 18);
        
        // Calcular total de entregas para uso futuro
        int totalDeliveries = deliveryCount.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total de entregas: " + totalDeliveries);
        
        ReportDto report = new ReportDto(
            nextReportId++,
            new Date(),
            new Date(),
            "Performance de entregadores",
            0.0f
        );
        
        reports.put(report.id(), report);
        System.out.println("📊 Relatório de performance de entregadores gerado: " + report.id());
        return report;
    }
    
    /**
     * Obtém relatórios por período
     */
    public List<ReportDto> getReportsByPeriod(Date start, Date end) {
        return reports.values().stream()
            .filter(report -> report.dataInicio().after(start) && report.dataFim().before(end))
            .toList();
    }
    
    // Métodos auxiliares
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }
    
    private boolean isOrderInPeriod(OrderDto order, LocalDateTime start, LocalDateTime end) {
        LocalDateTime orderDate = convertToLocalDateTime(order.dataPedido());
        return !orderDate.isBefore(start) && !orderDate.isAfter(end);
    }
    
    private boolean isOrderInProducts(OrderDto order, List<Integer> products) {
        if (products.isEmpty()) return true;
        return order.products().stream()
            .anyMatch(product -> products.contains((int) product.id()));
    }
    
    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    // Dados simulados
    private static List<OrderDto> createSampleOrders() {
        List<OrderDto> orders = new ArrayList<>();
        
        // Criar alguns pedidos de exemplo
        for (int i = 1; i <= 20; i++) {
            List<ProductDto> products = sampleProducts.subList(0, (i % 3) + 1);
            double total = products.stream().mapToDouble(ProductDto::price).sum();
            
            OrderDto order = new OrderDto(
                i,
                "ORD-" + String.format("%04d", i),
                OrderStatus.values()[i % OrderStatus.values().length].getDescription(),
                new Date(System.currentTimeMillis() - (i * 24 * 60 * 60 * 1000L)),
                (float) total,
                "Comentário do pedido " + i,
                (i % 5) + 1,
                (long) (i % 5) + 1,
                products
            );
            orders.add(order);
        }
        
        return orders;
    }
    
    private static List<ProductDto> createSampleProducts() {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "Produto A", 29.99f, "1234567890123", "Descrição do produto A", "image1.jpg"));
        products.add(new ProductDto(2L, "Produto B", 49.99f, "1234567890124", "Descrição do produto B", "image2.jpg"));
        products.add(new ProductDto(3L, "Produto C", 19.99f, "1234567890125", "Descrição do produto C", "image3.jpg"));
        products.add(new ProductDto(4L, "Produto D", 79.99f, "1234567890126", "Descrição do produto D", "image4.jpg"));
        products.add(new ProductDto(5L, "Produto E", 39.99f, "1234567890127", "Descrição do produto E", "image5.jpg"));
        return products;
    }
}
