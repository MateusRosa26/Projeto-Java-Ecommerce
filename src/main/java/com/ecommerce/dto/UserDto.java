package com.ecommerce.dto;

import java.util.List;

public record UserDto(long id,
                      String username,
                      String nome,
                      String email,
                      AddressDto address,
                      List<OrderDto> orders,
                      PaymentDto payment) { }
