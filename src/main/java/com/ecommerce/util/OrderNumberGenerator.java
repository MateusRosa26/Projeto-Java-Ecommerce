package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class OrderNumberGenerator {
    
    private static final AtomicLong counter = new AtomicLong(1);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    /**
     * Gera um número único para o pedido no formato: PED{YYYYMMDD}{0001}
     * Exemplo: PED202501010001
     */
    public static String generateOrderNumber() {
        String datePrefix = LocalDateTime.now().format(DATE_FORMAT);
        long sequence = counter.getAndIncrement();
        return String.format("PED%s%04d", datePrefix, sequence);
    }
    
    /**
     * Gera um número único para o pedido com prefixo customizado
     */
    public static String generateOrderNumber(String prefix) {
        String datePrefix = LocalDateTime.now().format(DATE_FORMAT);
        long sequence = counter.getAndIncrement();
        return String.format("%s%s%04d", prefix, datePrefix, sequence);
    }
    
    /**
     * Reset do contador (útil para testes)
     */
    public static void resetCounter() {
        counter.set(1);
    }
}
