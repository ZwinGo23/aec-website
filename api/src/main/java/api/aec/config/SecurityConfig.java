package api.aec.config;

import api.aec.auth.AuthenticationHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.security.enabled}")
    private boolean securityEnabled;

    private final AuthenticationHandler authenticationHandler;

    public SecurityConfig(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());

        if (!securityEnabled) {
            http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }

        http.authenticationProvider(authenticationHandler)
                .csrf(CsrfConfigurer::spa)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api-aec/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api-aec/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api-aec/csrf").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api-aec/informations/**",  "/api-aec/announcements/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                        response.setStatus(HttpServletResponse.SC_FORBIDDEN))
                );
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationHandler authenticationHandler) {
        return new ProviderManager(authenticationHandler);
    }
}