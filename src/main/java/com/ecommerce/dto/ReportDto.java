package com.ecommerce.dto;

import java.util.Date;

public record ReportDto(long id,
                        Date dataInicio,
                        Date dataFim,
                        String produtos,
                        float totalVendas) { }
