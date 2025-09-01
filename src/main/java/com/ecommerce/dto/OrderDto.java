package com.ecommerce.dto;

import java.util.Date;
import java.util.List;

public record OrderDto(long id,
                       String numero,
                       Date dataPedido,
                       float total,
                       String comment,
                       List<ProductDto> products) { }
