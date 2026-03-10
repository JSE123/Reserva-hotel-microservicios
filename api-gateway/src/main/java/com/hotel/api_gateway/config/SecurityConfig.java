package com.hotel.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange
                        // .pathMatchers("/booking/reservas/publico").permitAll()
                        .pathMatchers("/bookings/create").hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/bookings/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.GET, "/bookings/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/inventory/api/v1/rooms/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/inventory/api/v1/roomtypes/all").permitAll()
                        .pathMatchers(HttpMethod.GET, "/inventory/api/v1/rooms/{idRoom}/validate").hasRole("ADMIN")
                        .pathMatchers("/inventory/api/v1/roomtypes/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/inventory/api/v1/rooms/**").hasRole("ADMIN")
                        .anyExchange().authenticated())

                // .oauth2ResourceServer(oauth2 ->
                // oauth2.jwt(jwt -> {})
                // );
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:8082/oauth2/jwks").build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter reactiveJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");

        authoritiesConverter.setAuthorityPrefix("");

        ReactiveJwtGrantedAuthoritiesConverterAdapter reactiveAdapter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
                authoritiesConverter);

        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(reactiveAdapter);
        return converter;
    }
}
