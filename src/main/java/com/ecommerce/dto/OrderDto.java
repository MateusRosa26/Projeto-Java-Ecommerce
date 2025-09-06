package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;

import java.util.Date;
import java.util.List;

public record OrderDto(long id,
                       String numero,
                       Date dataPedido,
                       OrderStatus status,
                       float total,
                       String comment,
                       int rating,
                       long clienteId,
                       List<ProductDto> products,
                       List<ItemPedidoDto> itens,
                       AddressDto enderecoEntrega,
                       PaymentDto pagamento,
                       Date createdAt,
                       Date updatedAt) { }
