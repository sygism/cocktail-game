package com.cg.configuration;

import com.cg.exceptions.CocktailsApiErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FeignConfig {

    @Value("${external.frontend.url}")
    private String frontendUrl;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CocktailsApiErrorDecoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("PUT", "GET", "DELETE", "OPTIONS", "PATCH", "POST")
                        .allowedOrigins(frontendUrl);
            }
        };
    }
}
