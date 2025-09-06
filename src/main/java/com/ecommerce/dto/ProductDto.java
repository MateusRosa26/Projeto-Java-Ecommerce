package com.ecommerce.dto;

import java.util.Date;

public record ProductDto(long id, 
                        String name, 
                        float price, 
                        String description, 
                        String imageUrl, 
                        String codigoBarras, 
                        boolean disponivel, 
                        Date createdAt, 
                        Date updatedAt) { }
