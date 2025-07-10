package com.ingsoft.tf.api_edurents.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity //Importante para anotaciones @PreAuthorize
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JWTFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()) // TODO: Permite solicitudes CORS desde otros dominios
            .csrf(AbstractHttpConfigurer::disable) // TODO: Desactiva la protección CSRF, ya que en APIs REST no se usa (se autentica con tokens, no con cookies)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(antMatcher("/auth/login")).permitAll()
                    .requestMatchers(antMatcher("/auth/login/google")).permitAll()
                    .requestMatchers(antMatcher("/auth/register")).permitAll()
                    .requestMatchers(antMatcher("/auth/forgot-password")).permitAll()
                    .requestMatchers(antMatcher("/auth/verify-token/{id}")).permitAll()
                    .requestMatchers(antMatcher("/auth/reset-password/{id}")).permitAll()
                    .requestMatchers(antMatcher("/public/categories")).permitAll()
                    .requestMatchers(antMatcher("/public/categories/**")).permitAll()
                    .requestMatchers(antMatcher("/public/courses")).permitAll()
                    .requestMatchers(antMatcher("/public/courses/**")).permitAll()
                    .requestMatchers(antMatcher("/public/products")).permitAll()
                    .requestMatchers(antMatcher("/public/products/**")).permitAll()
                    .requestMatchers(antMatcher("/public/sellers")).permitAll()
                    .requestMatchers(antMatcher("/public/sellers/**")).permitAll()
                    .requestMatchers(antMatcher("/public/careers")).permitAll()
                    .requestMatchers(antMatcher("/public/careers/**")).permitAll()
                    .requestMatchers(antMatcher("/public/career")).permitAll()
                    .requestMatchers(antMatcher("/public/career/**")).permitAll()
                    .requestMatchers(antMatcher("/public/reviews")).permitAll()
                    .requestMatchers(antMatcher("/public/reviews/**")).permitAll()
                    .requestMatchers("/api/v1/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                    // TODO: Cualquier otra solicitud requiere autenticación (JWT u otra autenticación configurada)
                    .anyRequest().authenticated()
            )
            // TODO: Permite la autenticación básica (para testingcon Postman, por ejemplo)
            //.httpBasic(Customizer.withDefaults())
            // TODO: Desactiva el formulario de inicio de sesión predeterminado, ya que se usará JWT
            .formLogin(AbstractHttpConfigurer::disable)
            // TODO: Configura el manejo de excepciones para autenticación. Usa JwtAuthenticationEntryPoint para manejar errores 401 (no autorizado)
            .exceptionHandling(e ->
                e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            // TODO: Configura la política de sesiones como "sin estado" (stateless), ya que JWT maneja la autenticación, no las sesiones de servidor
                .sessionManagement(h ->
                        h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // TODO: Agrega la configuración para JWT en el filtro antes de los filtros predeterminados de Spring Security
                        .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());
            // TODO: Añadir el JWTFilter antes del filtro de autenticación de nombre de usuario/contraseña.
        // Esto permite que el JWTFilter valide el token antes de la autenticación
        http.addFilterBefore(jwtRequestFilter,  UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // TODO: Proporciona el AuthenticationManager que gestionará la autenticación basada en los detalles de usuario y contraseña
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/public/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

}