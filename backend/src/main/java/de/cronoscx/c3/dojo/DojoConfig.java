package de.cronoscx.c3.dojo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableConfigurationProperties(DojoManagementConfigProperties.class)
@EnableMethodSecurity
@EnableWebSecurity
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
class DojoConfig {

    @Bean
    UserDetailsService inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}pass")
                        .roles("USER")
                        .build()
        );
    }

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
    SecurityFilterChain sseFilterChain(HttpSecurity http) {
        return http
                .securityMatcher("/sse/**")
                .sessionManagement(SessionManagementConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(customizer -> customizer.realmName("C3 Dojo :: SSE"))
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/sse/dummy").authenticated()
                            .anyRequest().permitAll();
                })
                .build();
    }

}
