package com.ecommerce.config;

import com.ecommerce.business.AuthBusinessHandlerImpl;
import com.ecommerce.business.OrderBusinessHandlerImpl;
import com.ecommerce.business.ProductBusinessHandlerImpl;
import com.ecommerce.business.ReportBusinessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Spring para injeção de dependência
 */
@Configuration
public class AppConfig {

    @Bean
    public ProductBusinessHandlerImpl productBusinessHandler() {
        return new ProductBusinessHandlerImpl();
    }

    @Bean
    public AuthBusinessHandlerImpl authBusinessHandler() {
        return new AuthBusinessHandlerImpl();
    }

    @Bean
    public OrderBusinessHandlerImpl orderBusinessHandler() {
        return new OrderBusinessHandlerImpl();
    }

    @Bean
    public ReportBusinessHandlerImpl reportBusinessHandler() {
        return new ReportBusinessHandlerImpl();
    }
}
