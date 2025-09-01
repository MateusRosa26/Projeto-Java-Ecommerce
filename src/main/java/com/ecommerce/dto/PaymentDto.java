package com.ecommerce.dto;

import java.time.LocalDateTime;

public record PaymentDto(long id,
                         String metodo,
                         String status,
                         LocalDateTime dataPagamento,
                         int tentativas,
                         String nomeCartao,
                         Long numeroCartao,
                         Integer mesValidade,
                         Integer anoValidade) { }
