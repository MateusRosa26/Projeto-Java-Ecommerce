package com.ecommerce.dto;

import java.util.Date;
import java.util.List;

public record CartDto(Long id,
                      Long clienteId,
                      List<ItemPedidoDto> itens,
                      double total,
                      int quantidadeTotal,
                      Date createdAt,
                      Date updatedAt) { }
