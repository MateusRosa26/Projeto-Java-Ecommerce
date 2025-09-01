package com.ecommerce.dto;

public record AddressDto(String rua,
                         String numero,
                         String bairro,
                         String cidade,
                         String estado,
                         String complement,
                         String cep) { }
