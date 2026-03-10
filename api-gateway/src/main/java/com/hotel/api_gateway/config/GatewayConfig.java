package com.hotel.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("booking-service", r -> r.path("/bookings/**")
                        .uri("http://localhost:8084"))
                .route("inventory-service", r -> r.path("/inventory/**")
                        // .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8085"))
                .build();
    }

}
