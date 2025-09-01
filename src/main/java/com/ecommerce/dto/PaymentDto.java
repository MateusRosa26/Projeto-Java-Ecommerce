package com.ecommerce.dto;

import java.util.Date;

public record PaymentDto(String metodo,
                         String status,
                         Date dataPagamento,
                         int tentativas,
                         String nomeCartao,
                         long numeroCartao,
                         int mesValidade,
                         int anoValidade) { }
