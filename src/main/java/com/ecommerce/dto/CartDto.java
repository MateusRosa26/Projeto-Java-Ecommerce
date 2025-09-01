package com.ecommerce.dto;

import java.time.LocalDateTime;

public record CartDto(long id,
                      LocalDateTime dataCriacao,
                      String status,
                      Long idCliente) { }
