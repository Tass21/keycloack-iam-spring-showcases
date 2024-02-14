package com.tdksoft.demokeycloak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(t -> t.disable());

        // Any request should be authenticated
        http.authorizeHttpRequests(authorize -> {
            authorize.anyRequest().authenticated();
        });

      /*  // Authenticating using the spring oauth resource server with JWT
        http.oauth2ResourceServer(t -> {
            // Default customizer provided by the oauth server are used here
            t.jwt(Customizer.withDefaults());
        });*/

        // Authenticating using the spring keycloak server
        http.oauth2ResourceServer(t -> {
            t.opaqueToken(Customizer.withDefaults());
        });

        // None of the request should create a session
        http.sessionManagement(t -> {
            t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }
}
