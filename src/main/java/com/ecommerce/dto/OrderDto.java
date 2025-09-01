package com.ecommerce.dto;

import java.util.Date;
import java.util.List;

public record OrderDto(long id,
                       String numero,
                       String status,
                       Date dataPedido,
                       float total,
                       String comment,
                       int rating,
                       Long idCliente,
                       List<ProductDto> products) { }
