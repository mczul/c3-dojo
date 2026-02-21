package de.cronoscx.c3.dojo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableConfigurationProperties(DojoManagementConfigProperties.class)
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
class DojoConfig {

    @Bean
    @Order(1)
    SecurityFilterChain managementFilterChain(HttpSecurity http,
                                              DojoManagementConfigProperties dojoManagementConfigProperties) {
        final var managementUserDetailsService = new InMemoryUserDetailsManager(
                new User(
                        dojoManagementConfigProperties.username(),
                        "{noop}%s".formatted(dojoManagementConfigProperties.password()),
                        List.of()
                )
        );

        return http
                .securityMatcher("/actuator/**")
                .userDetailsService(managementUserDetailsService)
                .httpBasic(customizer -> customizer.realmName("C3 Dojo :: Management"))
                .authorizeHttpRequests(customizer -> customizer
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain apiFilterChain(HttpSecurity http) {
        return http
                .securityMatcher("/api/**")
                .sessionManagement(SessionManagementConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
