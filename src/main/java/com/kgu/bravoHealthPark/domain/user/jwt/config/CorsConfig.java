package com.kgu.bravoHealthPark.domain.user.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/user/**", config);
        source.registerCorsConfiguration("/alarm/**", config);
        source.registerCorsConfiguration("/calendar/**", config);
        source.registerCorsConfiguration("/medicationInfo/**", config);
        source.registerCorsConfiguration("/medicionInfo/**", config);
        return new CorsFilter(source);
    }
}
