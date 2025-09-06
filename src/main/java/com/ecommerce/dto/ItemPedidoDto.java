package com.ecommerce.dto;

import java.util.Date;

public record ItemPedidoDto(Long id,
                           Long produtoId,
                           Long pedidoId,
                           int quantidade,
                           double precoUnitario,
                           double subtotal,
                           ProductDto produto,
                           Date createdAt,
                           Date updatedAt) { }
