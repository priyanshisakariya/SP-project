package com.campus.campus_backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
//import com.campus.campus_backend.security.GoogleOAuthSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final GoogleOAuthSuccessHandler successHandler;
//
//    public SecurityConfig(GoogleOAuthSuccessHandler successHandler) {
//        this.successHandler = successHandler;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})   // <-- ADD THIS LINE
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        /*
                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler)
                        .failureHandler((request, response, exception) -> {
                            System.out.println("==================================");
                            System.out.println("GOOGLE LOGIN FAILED");
                            System.out.println(exception.getMessage());
                            exception.printStackTrace();
                            response.sendRedirect("/login?error");
                        })
                );*/

        return http.build();
    }

    // Paste this method BELOW securityFilterChain()
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}