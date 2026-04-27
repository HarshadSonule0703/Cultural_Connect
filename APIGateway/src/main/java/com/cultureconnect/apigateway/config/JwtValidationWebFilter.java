package com.cultureconnect.apigateway.config;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationWebFilter implements WebFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtValidationWebFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // If it's a login request, don't even try to validate a token
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/login") || path.contains("/citizenRegister")) {
            return chain.filter(exchange);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtTokenUtil.isTokenValid(token)) {
                    String username = jwtTokenUtil.getUsername(token);
                    String role = jwtTokenUtil.getRole(token);

                    // Use UsernamePasswordAuthenticationToken
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            username, 
                            null, 
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

                    return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                }
            } catch (Exception e) {
                // Log the error: log.error("JWT Validation failed: {}", e.getMessage());
            }
        }
        return chain.filter(exchange);
    }
}