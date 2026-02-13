package de.cronoscx.c3.dojo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
class DojoConfig {

    @Bean
    @Order(1)
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .sessionManagement(SessionManagementConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
