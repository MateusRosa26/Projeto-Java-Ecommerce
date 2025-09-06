package com.ecommerce.dto;

import java.util.Date;
import java.util.List;

public record ReportDto(Long id,
                        Date dataInicio,
                        Date dataFim,
                        String produtoBarcode,
                        double totalVendas,
                        int quantidadePedidos,
                        int quantidadeProdutos,
                        List<OrderDto> pedidos,
                        List<ProductDto> produtos,
                        Date createdAt) { }
