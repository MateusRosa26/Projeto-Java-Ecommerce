package com.ecommerce.dto;

import java.util.List;

public record UserDto(long id,
                      String username,
                      String nome,
                      String email,
                      String senha,
                      String telefone,
                      String role,
                      AddressDto address,
                      List<OrderDto> orders,
                      PaymentDto payment) { }
