package com.andy.gestor_tareas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration   // Clase de configuración que Spring lee al arrancar
public class ConfiguracionWeb implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                       // aplica a todas tus rutas /api
                .allowedOrigins("http://localhost:4200")     // permite llamadas desde tu Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // los verbos que usas
    }
}