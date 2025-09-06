package com.ecommerce.business;

import com.ecommerce.dto.PaymentDto;
import com.ecommerce.model.Pagamento;
import com.ecommerce.repository.PagamentoRepository;
import com.ecommerce.enums.PaymentMethod;
import com.ecommerce.service.ExternalPaymentService;

import java.util.*;
import java.util.stream.Collectors;

public class PaymentBusinessHandlerImpl implements BusinessHandler<PaymentDto> {
    
    private final PagamentoRepository pagamentoRepository;
    private final ExternalPaymentService externalPaymentService;

    public PaymentBusinessHandlerImpl() {
        this.pagamentoRepository = new PagamentoRepository();
        this.externalPaymentService = new ExternalPaymentService();
    }
    
    @Override
    public PaymentDto create(PaymentDto object) {
        if (object == null || object.pedidoId() == null || object.metodoPagamento() == null) {
            throw new IllegalArgumentException("Dados do pagamento são obrigatórios");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(object.pedidoId());
        pagamento.setMetodoPagamento(object.metodoPagamento());
        pagamento.setValor(object.valor());
        pagamento.setStatus(object.status() != null ? object.status() : "PENDENTE");
        pagamento.setCodigoTransacao(object.codigoTransacao());

        pagamentoRepository.save(pagamento);
        return toDto(pagamento);
    }

    @Override
    public PaymentDto update(PaymentDto object, long id) {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        Pagamento pagamento = pagamentos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }

        if (object.status() != null) {
            pagamento.setStatus(object.status());
        }
        if (object.codigoTransacao() != null) {
            pagamento.setCodigoTransacao(object.codigoTransacao());
        }
        if (object.dataPagamento() != null) {
            pagamento.setDataPagamento(object.dataPagamento());
        }

        // Atualizar no repositório
        List<Pagamento> allPagamentos = pagamentoRepository.findAll();
        allPagamentos.removeIf(p -> p.getId() == id);
        allPagamentos.add(pagamento);
        pagamentoRepository.saveAll(allPagamentos);

        return toDto(pagamento);
    }

    @Override
    public boolean delete(long id) {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        boolean removed = pagamentos.removeIf(p -> p.getId() == id);
        if (removed) {
            pagamentoRepository.saveAll(pagamentos);
        }
        return removed;
    }

    @Override
    public PaymentDto find(long id) {
        return pagamentoRepository.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<PaymentDto> list() {
        return pagamentoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> list(String field, String filter, boolean exact) {
        return pagamentoRepository.findAll().stream()
                .filter(payment -> {
                    switch (field.toLowerCase()) {
                        case "status":
                            return exact ? payment.getStatus().equals(filter)
                                       : payment.getStatus().toLowerCase().contains(filter.toLowerCase());
                        case "metodopagamento":
                            return exact ? payment.getMetodoPagamento().name().equals(filter)
                                       : payment.getMetodoPagamento().name().toLowerCase().contains(filter.toLowerCase());
                        case "pedidoid":
                            return String.valueOf(payment.getPedidoId()).equals(filter);
                        default:
                            return false;
                    }
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Processa um pagamento usando serviço externo
     */
    public PaymentDto processPayment(PaymentMethod method, double amount, Long pedidoId, String cardNumber, String cvv) {
        // Valida dados do cartão se necessário
        if (method == PaymentMethod.CARTAO_CREDITO || method == PaymentMethod.CARTAO_DEBITO) {
            if (!externalPaymentService.validateCard(cardNumber, cvv)) {
                throw new IllegalArgumentException("Dados do cartão inválidos");
            }
        }
        
        // Processa pagamento via serviço externo
        ExternalPaymentService.PaymentResult result = externalPaymentService.processPayment(method, amount, cardNumber, cvv);
        
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedidoId);
        pagamento.setMetodoPagamento(method);
        pagamento.setValor(amount);
        pagamento.setStatus(result.getStatus());
        pagamento.setCodigoTransacao(result.getTransactionId());
        
        if (result.isApproved()) {
            pagamento.confirmarPagamento(result.getTransactionId());
        } else {
            pagamento.rejeitarPagamento();
        }

        pagamentoRepository.save(pagamento);
        System.out.println("💳 Pagamento processado: " + pagamento.getId() + " - " + method + " - R$ " + amount + " - " + result.getStatus());
        return toDto(pagamento);
    }
    
    /**
     * Reprocessa um pagamento
     */
    public PaymentDto retryPayment(long id, PaymentMethod newMethod, String cardNumber, String cvv) {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        Pagamento existingPayment = pagamentos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingPayment == null) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }
        
        // Valida dados do cartão se necessário
        if (newMethod == PaymentMethod.CARTAO_CREDITO || newMethod == PaymentMethod.CARTAO_DEBITO) {
            if (!externalPaymentService.validateCard(cardNumber, cvv)) {
                throw new IllegalArgumentException("Dados do cartão inválidos");
            }
        }
        
        // Reprocessa pagamento via serviço externo
        ExternalPaymentService.PaymentResult result = externalPaymentService.retryPayment(
            existingPayment.getCodigoTransacao(), newMethod, existingPayment.getValor());
        
        existingPayment.setMetodoPagamento(newMethod);
        existingPayment.setStatus(result.getStatus());
        existingPayment.setCodigoTransacao(result.getTransactionId());
        
        if (result.isApproved()) {
            existingPayment.confirmarPagamento(result.getTransactionId());
        } else {
            existingPayment.rejeitarPagamento();
        }
        
        // Atualizar no repositório
        pagamentos.removeIf(p -> p.getId() == id);
        pagamentos.add(existingPayment);
        pagamentoRepository.saveAll(pagamentos);
        System.out.println("🔄 Pagamento reprocessado: " + id + " - " + newMethod + " - " + result.getStatus());
        return toDto(existingPayment);
    }
    
    /**
     * Obtém pagamentos por status
     */
    public List<PaymentDto> getPaymentsByStatus(String status) {
        return pagamentoRepository.findAll().stream()
            .filter(payment -> payment.getStatus().equalsIgnoreCase(status))
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtém pagamentos por método
     */
    public List<PaymentDto> getPaymentsByMethod(PaymentMethod method) {
        return pagamentoRepository.findAll().stream()
            .filter(payment -> payment.getMetodoPagamento() == method)
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtém estatísticas de pagamento
     */
    public Map<String, Object> getPaymentStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        long totalPayments = pagamentos.size();
        long successfulPayments = pagamentos.stream()
            .filter(Pagamento::isAprovado)
            .count();
        
        Map<PaymentMethod, Long> methodCount = pagamentos.stream()
            .collect(Collectors.groupingBy(Pagamento::getMetodoPagamento, Collectors.counting()));
        
        stats.put("totalPayments", totalPayments);
        stats.put("successfulPayments", successfulPayments);
        stats.put("successRate", totalPayments > 0 ? (double) successfulPayments / totalPayments : 0.0);
        stats.put("methodDistribution", methodCount);
        
        return stats;
    }
    

    private PaymentDto toDto(Pagamento pagamento) {
        return new PaymentDto(
            pagamento.getId(),
            pagamento.getPedidoId(),
            pagamento.getMetodoPagamento(),
            pagamento.getValor(),
            pagamento.getStatus(),
            pagamento.getCodigoTransacao(),
            pagamento.getDataPagamento(),
            pagamento.getCreatedAt(),
            pagamento.getUpdatedAt()
        );
    }
}
