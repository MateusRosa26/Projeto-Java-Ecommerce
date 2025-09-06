package com.ecommerce.controller;

import com.ecommerce.business.EvaluationBusinessHandlerImpl;
import com.ecommerce.dto.EvaluationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de avaliações
 */
@RestController
@RequestMapping("/evaluations")
@CrossOrigin(origins = "*")
public class EvaluationController extends AbstractController<EvaluationDto> {

    private final EvaluationBusinessHandlerImpl evaluationHandler;

    @Autowired
    public EvaluationController(EvaluationBusinessHandlerImpl evaluationHandler) {
        this.evaluationHandler = evaluationHandler;
    }

    /**
     * Avalia um pedido entregue
     */
    @PostMapping("/order/{orderId}")
    public ResponseEntity<Boolean> evaluateOrder(
            @PathVariable Long orderId,
            @RequestParam Long clienteId,
            @RequestParam int nota,
            @RequestParam(required = false) String comentario) {
        
        boolean evaluated = evaluationHandler.evaluateOrder(orderId, clienteId, nota, comentario);
        return ResponseEntity.ok(evaluated);
    }

    /**
     * Avalia um entregador
     */
    @PostMapping("/delivery/{orderId}")
    public ResponseEntity<Boolean> evaluateDelivery(
            @PathVariable Long orderId,
            @RequestParam Long entregadorId,
            @RequestParam int nota,
            @RequestParam(required = false) String comentario) {
        
        boolean evaluated = evaluationHandler.evaluateDelivery(orderId, entregadorId, nota, comentario);
        return ResponseEntity.ok(evaluated);
    }

    /**
     * Avalia um produto
     */
    @PostMapping("/product/{orderId}")
    public ResponseEntity<Boolean> evaluateProduct(
            @PathVariable Long orderId,
            @RequestParam Long produtoId,
            @RequestParam int nota,
            @RequestParam(required = false) String comentario) {
        
        boolean evaluated = evaluationHandler.evaluateProduct(orderId, produtoId, nota, comentario);
        return ResponseEntity.ok(evaluated);
    }

    /**
     * Obtém avaliações de um pedido
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsByOrder(@PathVariable Long orderId) {
        List<EvaluationDto> evaluations = evaluationHandler.getEvaluationsByOrder(orderId);
        return ResponseEntity.ok(evaluations);
    }

    /**
     * Obtém avaliações de um entregador
     */
    @GetMapping("/delivery/{entregadorId}")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsByDelivery(@PathVariable Long entregadorId) {
        List<EvaluationDto> evaluations = evaluationHandler.getEvaluationsByDelivery(entregadorId);
        return ResponseEntity.ok(evaluations);
    }

    /**
     * Obtém avaliações de um produto
     */
    @GetMapping("/product/{produtoId}")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsByProduct(@PathVariable Long produtoId) {
        List<EvaluationDto> evaluations = evaluationHandler.getEvaluationsByProduct(produtoId);
        return ResponseEntity.ok(evaluations);
    }

    /**
     * Calcula média de avaliações
     */
    @GetMapping("/average")
    public ResponseEntity<Double> getAverageRating(@RequestParam List<Long> evaluationIds) {
        List<EvaluationDto> evaluations = evaluationIds.stream()
            .map(evaluationHandler::find)
            .filter(eval -> eval != null)
            .toList();
        
        double average = evaluationHandler.getAverageRating(evaluations);
        return ResponseEntity.ok(average);
    }

    // Implementações dos métodos abstratos
    @Override
    public ResponseEntity<List<EvaluationDto>> get() {
        List<EvaluationDto> evaluations = evaluationHandler.list();
        return ResponseEntity.ok(evaluations);
    }

    @Override
    public ResponseEntity<EvaluationDto> get(@PathVariable Long id) {
        EvaluationDto evaluation = evaluationHandler.find(id);
        if (evaluation != null) {
            return ResponseEntity.ok(evaluation);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EvaluationDto> post(@RequestBody EvaluationDto evaluation) {
        EvaluationDto created = evaluationHandler.create(evaluation);
        if (created != null) {
            return ResponseEntity.status(201).body(created);
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<EvaluationDto> put(@PathVariable Long id, @RequestBody EvaluationDto evaluation) {
        EvaluationDto updated = evaluationHandler.update(evaluation, id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = evaluationHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
