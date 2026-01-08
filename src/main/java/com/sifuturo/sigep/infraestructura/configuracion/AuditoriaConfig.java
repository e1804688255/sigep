package com.sifuturo.sigep.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditoriaConfig {

    // Esto le dice a Spring QUIÉN está haciendo el cambio.
    // Como aún no tenemos login, ponemos "SISTEMA" o "ADMIN" hardcodeado.
    // Luego conectaremos esto con Spring Security para que ponga el nombre del usuario real.
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("SISTEMA_TEST"); 
    }
}