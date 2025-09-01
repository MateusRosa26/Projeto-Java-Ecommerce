package com.ecommerce.controller;

import com.ecommerce.business.PaymentBusinessHandlerImpl;
import com.ecommerce.dto.PaymentDto;
import com.ecommerce.enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações de pagamentos
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController extends AbstractController<PaymentDto> {

    private final PaymentBusinessHandlerImpl paymentHandler;

    @Autowired
    public PaymentController(PaymentBusinessHandlerImpl paymentHandler) {
        this.paymentHandler = paymentHandler;
    }

    /**
     * Processa pagamento de um pedido
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentDto> processPayment(
            @RequestParam String method,
            @RequestParam double amount,
            @RequestParam(required = false) String cardName,
            @RequestParam(required = false) Long cardNumber,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        
        PaymentMethod paymentMethod = PaymentMethod.fromString(method);
        if (paymentMethod == null) {
            return ResponseEntity.badRequest().build();
        }
        
        PaymentDto payment = paymentHandler.processPayment(paymentMethod, amount, cardName, cardNumber, month, year);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Reprocessa pagamento de um pedido
     */
    @PostMapping("/{id}/retry")
    public ResponseEntity<PaymentDto> retryPayment(
            @PathVariable Long id,
            @RequestParam String newMethod) {
        
        PaymentMethod paymentMethod = PaymentMethod.fromString(newMethod);
        if (paymentMethod == null) {
            return ResponseEntity.badRequest().build();
        }
        
        PaymentDto payment = paymentHandler.retryPayment(id, paymentMethod);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.badRequest().build();
    }
    
    /**
     * Obtém pagamentos por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatus(@PathVariable String status) {
        List<PaymentDto> payments = paymentHandler.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    
    /**
     * Obtém pagamentos por método
     */
    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByMethod(@PathVariable String method) {
        List<PaymentDto> payments = paymentHandler.getPaymentsByMethod(method);
        return ResponseEntity.ok(payments);
    }
    
    /**
     * Obtém estatísticas de pagamento
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getPaymentStatistics() {
        Map<String, Object> stats = paymentHandler.getPaymentStatistics();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Lista métodos de pagamento disponíveis
     */
    @GetMapping("/methods")
    public ResponseEntity<List<String>> getAvailableMethods() {
        List<String> methods = List.of(
            PaymentMethod.CARTAO_CREDITO.getDescription(),
            PaymentMethod.CARTAO_DEBITO.getDescription(),
            PaymentMethod.PIX.getDescription(),
            PaymentMethod.BOLETO.getDescription(),
            PaymentMethod.TICKET.getDescription(),
            PaymentMethod.DINHEIRO.getDescription()
        );
        return ResponseEntity.ok(methods);
    }

    // Implementações dos métodos abstratos
    @Override
    public ResponseEntity<List<PaymentDto>> get() {
        List<PaymentDto> payments = paymentHandler.list();
        return ResponseEntity.ok(payments);
    }

    @Override
    public ResponseEntity<PaymentDto> get(@PathVariable Long id) {
        PaymentDto payment = paymentHandler.find(id);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<PaymentDto> post(@RequestBody PaymentDto payment) {
        PaymentDto created = paymentHandler.create(payment);
        if (created != null) {
            return ResponseEntity.status(201).body(created);
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<PaymentDto> put(@PathVariable Long id, @RequestBody PaymentDto payment) {
        PaymentDto updated = paymentHandler.update(payment, id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = paymentHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
