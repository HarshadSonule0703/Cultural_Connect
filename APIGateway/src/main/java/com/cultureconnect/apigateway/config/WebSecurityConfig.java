

package com.cultureconnect.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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

            // ✅ ENABLE CORS (REQUIRED)
            .cors(Customizer.withDefaults())

            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

            .authorizeExchange(ex -> ex
                // ✅ MUST BE FIRST
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .pathMatchers(
                    "/cultureconnect/login",
                    "/cultureconnect/citizenRegister",
                    "/cultureconnect/forgotPassword",
                    "/cultureconnect/forgotPassword/otp",
                    "/api/citizens/register"
                ).permitAll()

                .pathMatchers("/api/citizens/**")
                .hasAnyRole("CITIZEN","ADMIN","OFFICER","MANAGER")
                
                .pathMatchers("/api/heritage-sites/**")
                .hasAnyRole("ADMIN","OFFICER","MANAGER")

                .pathMatchers("/api/programs/**")
                .hasAnyRole("MANAGER","ADMIN","CITIZEN")
                
                .pathMatchers("/api/applications/**")
                .hasAnyRole("MANAGER","ADMIN","CITIZEN")
                
                .pathMatchers("/api/events/**")
                .hasAnyRole("MANAGER","ADMIN","CITIZEN")
                
                .pathMatchers("/api/resources/**")
                .hasAnyRole("MANAGER","ADMIN")
                
                .pathMatchers("/api/notifications/**")
                .hasAnyRole("CITIZEN","ADMIN","OFFICER","MANAGER")
                
                .pathMatchers("/api/reports/**")
                .hasAnyRole("CITIZEN","ADMIN","OFFICER","MANAGER")
                

                .anyExchange().authenticated()
            )

            // ✅ JWT AFTER CORS
            .addFilterAt(jwtValidationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

            .build();
    }
}