package com.sifuturo.sigep.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// --- IMPORTS NECESARIOS PARA CORS ---
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. AQUI AGREGAMOS LA CONFIGURACIÓN DE CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            .csrf(AbstractHttpConfigurer::disable) 
            .authorizeHttpRequests(auth -> auth
                // Importante: Asegúrate de que OPTIONS esté permitido explícitamente o cubierto por el permitAll
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Siempre bueno dejar pasar OPTIONS
                .anyRequest().authenticated()
            );
        
        return http.build();
    }

    // 2. DEFINIMOS EL BEAN CON LAS REGLAS DE CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ¡OJO! Aquí autorizamos a tu Frontend (puerto 5173)
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); 
        
        // Métodos permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Cabeceras permitidas (Authorization, Content-Type, etc.)
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permitir credenciales (cookies, headers de auth)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicamos esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}