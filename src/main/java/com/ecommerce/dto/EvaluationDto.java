package com.ecommerce.dto;

import java.util.Date;

public record EvaluationDto(Long id,
                           Long pedidoId,
                           Long produtoId,
                           Long clienteId,
                           Long entregadorId,
                           int notaProduto,
                           String comentarioProduto,
                           int notaEntrega,
                           String comentarioEntrega,
                           Date dataAvaliacao,
                           Date createdAt,
                           Date updatedAt) { }
