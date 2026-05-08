package com.cultureconnect.apigateway.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

//@Configuration
//public class CorsConfig {
//	@Bean
//	public CorsWebFilter corsWebFilter() {
//
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.addAllowedOriginPattern("*");
//		config.addAllowedOrigin("http://localhost:5173");
//		
//
//		config.addAllowedHeader("*");
//
//		config.addAllowedMethod("*");
//
//		config.setAllowCredentials(true);
//
//		
//		config.addExposedHeader("Authorization");
//		
//		UrlBasedCorsConfigurationSource source =
//
//				new UrlBasedCorsConfigurationSource();
//
//		source.registerCorsConfiguration("/**", config);
//
//		return new CorsWebFilter(source);
//
//	}
//	
//
//}
@Configuration

public class CorsConfig {
<<<<<<< HEAD
	@Bean

	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();

		config.addAllowedOrigin("http://localhost:5173"); // ✅ IMPORTANT
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source =
=======

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // ✅ Exact frontend origin (NO *)
        config.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        // ✅ JWT header is REQUIRED
        config.setAllowedHeaders(
                List.of("Authorization", "Content-Type")
        );

        // ✅ MUST include OPTIONS
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        // ✅ Auth-based systems
        config.setAllowCredentials(true);
>>>>>>> ef4c34f3cd0654b5475b10f5804f7a5612221bfd

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

<<<<<<< HEAD
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);

	}

}
=======
        return new CorsWebFilter(source);
    }
}
>>>>>>> ef4c34f3cd0654b5475b10f5804f7a5612221bfd
