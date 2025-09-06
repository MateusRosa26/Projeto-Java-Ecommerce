package com.ecommerce.dto;

import com.ecommerce.enums.UserRole;

import java.util.Date;
import java.util.List;

public record UserDto(long id,
                      String username,
                      String nome,
                      String email,
                      String telefone,
                      String cidade,
                      Date dataNascimento,
                      UserRole role,
                      AddressDto address,
                      List<OrderDto> orders,
                      PaymentDto payment,
                      Date createdAt,
                      Date updatedAt) { }
