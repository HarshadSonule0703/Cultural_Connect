package com.cultureconnect.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtValidationWebFilter jwtValidationWebFilter) {

        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

            // ✅ JWT validation filter
            .addFilterAt(jwtValidationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

            .authorizeExchange(ex -> ex

                // 1️⃣ PUBLIC AUTH ENDPOINTS
                .pathMatchers(
                    "/cultureconnect/login",
                    "/cultureconnect/citizenRegister",
                    "/cultureconnect/forgotPassword"
                ).permitAll()
                .pathMatchers("/cultureconnect/getUserById/{userId}",
                		"/cultureconnect/userRegisterByAdmin",
                		"/cultureconnect/audit_log").hasAnyAuthority("ADMIN")
                

                			  // ✅ AUDIT
                             .pathMatchers("/audit_log/**", "/audits/**")
                             .hasAnyAuthority("ADMIN", "AUDITOR")

                             // ✅ CITIZENS
                             .pathMatchers("/api/citizens/**")
                             .hasAnyRole("CITIZEN", "ADMIN")

                             // ✅ PROGRAMS & GRANTS
                             .pathMatchers("/api/programs/**")
                             .hasAnyRole("MANAGER", "ADMIN")

                             .pathMatchers("/api/applications/**")
                             .hasAnyRole("CITIZEN", "OFFICER", "ADMIN")

                             // ✅ COMPLIANCE
                             .pathMatchers("/compliance/**")
                             .hasAnyRole("COMPLIANCE","ADMIN")

                             // ✅ EVENTS & RESOURCES
                             .pathMatchers("/api/events/**", "/api/resources/**")
                             .hasAnyRole("OFFICER","ADMIN")

                             // ✅ HERITAGE
                             .pathMatchers("/api/heritage-sites/**", "/api/activities/**")
                             .hasAnyRole("OFFICER","ADMIN","MANAGER")

                             // ✅ REPORTS
                             .pathMatchers("/api/reports/**")
                             .hasAnyRole("MANAGER","ADMIN","AUDITOR")


                // 🔒 Everything else secured
                .anyExchange().authenticated()
            )
            .build();
    }
}