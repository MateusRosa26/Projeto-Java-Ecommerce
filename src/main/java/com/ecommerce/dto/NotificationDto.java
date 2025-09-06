package com.ecommerce.dto;

import java.util.Date;

public record NotificationDto(Long id,
                             Long clienteId,
                             Long pedidoId,
                             String titulo,
                             String mensagem,
                             String tipo,
                             boolean lida,
                             Date dataEnvio,
                             Date createdAt,
                             Date updatedAt) { }
