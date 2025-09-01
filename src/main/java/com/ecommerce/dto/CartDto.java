package com.ecommerce.dto;

import java.util.Date;

public record CartDto(long id,
                      Date dataCriacao,
                      String status) { }
