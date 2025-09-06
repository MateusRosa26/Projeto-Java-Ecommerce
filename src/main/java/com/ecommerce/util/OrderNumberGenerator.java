package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class OrderNumberGenerator {
    private static final AtomicLong counter = new AtomicLong(1);
    
    public static String generate() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long sequence = counter.getAndIncrement();
        return "PED" + timestamp + String.format("%03d", sequence % 1000);
    }
    
    public static String generateOrderNumber() {
        return generate();
    }
}
