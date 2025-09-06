package com.ecommerce.dto;

import java.time.LocalDateTime;

public record NotificationDto(
    Long id,
    String tipo,
    String mensagem,
    LocalDateTime dataEnvio,
    Long idCliente,
    Long idPedido
) { }
