package com.ecommerce.dto;

import java.util.Date;

public record AddressDto(Long id,
                         String rua,
                         String numero,
                         String bairro,
                         String cidade,
                         String estado,
                         String complemento,
                         String cep,
                         Long clienteId,
                         Date createdAt,
                         Date updatedAt) { }
