package com.ecommerce.dto;

import java.time.LocalDateTime;

public record EvaluationDto(
    Long id,
    int nota,
    String comentario,
    LocalDateTime dataAvaliacao,
    Long idCliente,
    Long idPedido,
    Long idEntregador,
    Long idProduto
) { }
