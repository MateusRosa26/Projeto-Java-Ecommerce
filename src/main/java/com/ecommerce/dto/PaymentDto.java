package com.ecommerce.dto;

import com.ecommerce.enums.PaymentMethod;

import java.util.Date;

public record PaymentDto(Long id,
                         Long pedidoId,
                         PaymentMethod metodoPagamento,
                         double valor,
                         String status,
                         String codigoTransacao,
                         Date dataPagamento,
                         Date createdAt,
                         Date updatedAt) { }
