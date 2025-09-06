package com.ecommerce.business;

import com.ecommerce.dto.EvaluationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EvaluationBusinessHandlerImpl implements BusinessHandler<EvaluationDto> {
    
    // Armazenamento temporário de avaliações (em produção seria um banco de dados)
    private static final Map<Long, EvaluationDto> evaluations = new ConcurrentHashMap<>();
    private static long nextEvaluationId = 1L;
    
    @Override
    public EvaluationDto create(EvaluationDto object) {
        if (object != null && object.nota() >= 1 && object.nota() <= 5) {
            EvaluationDto newEvaluation = new EvaluationDto(
                nextEvaluationId++,
                object.nota(),
                object.comentario(),
                LocalDateTime.now(),
                object.idCliente(),
                object.idPedido(),
                object.idEntregador(),
                object.idProduto()
            );
            evaluations.put(newEvaluation.id(), newEvaluation);
            System.out.println("✅ Avaliação criada: " + newEvaluation.nota() + " estrelas");
            return newEvaluation;
        }
        return null;
    }

    @Override
    public EvaluationDto update(EvaluationDto object, long id) {
        if (evaluations.containsKey(id)) {
            evaluations.put(id, object);
            return object;
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return evaluations.remove(id) != null;
    }

    @Override
    public EvaluationDto find(long id) {
        return evaluations.get(id);
    }

    @Override
    public List<EvaluationDto> list() {
        return new ArrayList<>(evaluations.values());
    }

    @Override
    public List<EvaluationDto> list(String field, String filter, boolean exact) {
        return evaluations.values().stream()
            .filter(eval -> {
                switch (field.toLowerCase()) {
                    case "nota":
                        return exact ? eval.nota() == Integer.parseInt(filter) 
                                   : String.valueOf(eval.nota()).contains(filter);
                    case "comentario":
                        return exact ? eval.comentario().equals(filter)
                                   : eval.comentario().toLowerCase().contains(filter.toLowerCase());
                    case "idcliente":
                        return eval.idCliente().equals(Long.parseLong(filter));
                    case "idpedido":
                        return eval.idPedido().equals(Long.parseLong(filter));
                    default:
                        return false;
                }
            })
            .toList();
    }
    
    /**
     * Avalia um pedido entregue
     */
    public boolean evaluateOrder(Long orderId, Long clienteId, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("❌ Nota deve estar entre 1 e 5");
            return false;
        }
        
        EvaluationDto evaluation = new EvaluationDto(
            nextEvaluationId++,
            nota,
            comentario,
            LocalDateTime.now(),
            clienteId,
            orderId,
            null, // idEntregador será preenchido depois
            null  // idProduto será preenchido depois
        );
        
        evaluations.put(evaluation.id(), evaluation);
        System.out.println("✅ Pedido " + orderId + " avaliado com " + nota + " estrelas");
        return true;
    }
    
    /**
     * Avalia um entregador
     */
    public boolean evaluateDelivery(Long orderId, Long entregadorId, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("❌ Nota deve estar entre 1 e 5");
            return false;
        }
        
        EvaluationDto evaluation = new EvaluationDto(
            nextEvaluationId++,
            nota,
            comentario,
            LocalDateTime.now(),
            null, // idCliente será preenchido depois
            orderId,
            entregadorId,
            null  // idProduto será preenchido depois
        );
        
        evaluations.put(evaluation.id(), evaluation);
        System.out.println("✅ Entregador " + entregadorId + " avaliado com " + nota + " estrelas");
        return true;
    }
    
    /**
     * Avalia um produto
     */
    public boolean evaluateProduct(Long orderId, Long produtoId, int nota, String comentario) {
        if (nota < 1 || nota > 5) {
            System.out.println("❌ Nota deve estar entre 1 e 5");
            return false;
        }
        
        EvaluationDto evaluation = new EvaluationDto(
            nextEvaluationId++,
            nota,
            comentario,
            LocalDateTime.now(),
            null, // idCliente será preenchido depois
            orderId,
            null, // idEntregador será preenchido depois
            produtoId
        );
        
        evaluations.put(evaluation.id(), evaluation);
        System.out.println("✅ Produto " + produtoId + " avaliado com " + nota + " estrelas");
        return true;
    }
    
    /**
     * Obtém avaliações de um pedido
     */
    public List<EvaluationDto> getEvaluationsByOrder(Long orderId) {
        return evaluations.values().stream()
            .filter(eval -> eval.idPedido().equals(orderId))
            .toList();
    }
    
    /**
     * Obtém avaliações de um entregador
     */
    public List<EvaluationDto> getEvaluationsByDelivery(Long entregadorId) {
        return evaluations.values().stream()
            .filter(eval -> eval.idEntregador() != null && eval.idEntregador().equals(entregadorId))
            .toList();
    }
    
    /**
     * Obtém avaliações de um produto
     */
    public List<EvaluationDto> getEvaluationsByProduct(Long produtoId) {
        return evaluations.values().stream()
            .filter(eval -> eval.idProduto() != null && eval.idProduto().equals(produtoId))
            .toList();
    }
    
    /**
     * Calcula média de avaliações
     */
    public double getAverageRating(List<EvaluationDto> evaluations) {
        if (evaluations.isEmpty()) return 0.0;
        
        return evaluations.stream()
            .mapToInt(EvaluationDto::nota)
            .average()
            .orElse(0.0);
    }
}
