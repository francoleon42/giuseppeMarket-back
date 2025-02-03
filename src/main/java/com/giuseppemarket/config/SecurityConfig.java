package com.giuseppemarket.config;

import com.giuseppemarket.config.jwt.JwtAuthenticationFilter;
import com.giuseppemarket.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String ADMINISTRADOR = Rol.ADMINISTRADOR.toString();
    private static final String VENDEDOR = Rol.VENDEDOR.toString();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authRequest -> {
                    configurePublicEndpoints(authRequest);
                    configureVendedorEndpoints(authRequest);
                    configureAdministradorEndpoints(authRequest);

                })
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://3.95.1.110", "http://3.95.1.110:80"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void configurePublicEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/ping").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
    }

    private void configureVendedorEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest

                // epic 2
                // amb producto
                .requestMatchers(HttpMethod.GET, "/productos/estado").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/productos/sucursales").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/productos/condiciones_productos").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.POST, "/productos/crear").hasRole(VENDEDOR)

                .requestMatchers(HttpMethod.PATCH, "/productos/update/{idProducto}").hasRole(VENDEDOR)

                // Item
                .requestMatchers(HttpMethod.POST, "/item/crear").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/item/ver_disponibles_de_producto/{idProducto}").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.DELETE, "/item/remove/{idItem}").hasRole(VENDEDOR)

                //
                .requestMatchers(HttpMethod.GET, "/productos/deficit_stock").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/item/vencido_hoy").hasAnyRole(VENDEDOR,ADMINISTRADOR)
                // amb y asignacion impuestos
                .requestMatchers(HttpMethod.POST, "/impuesto/crear").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.DELETE, "/impuesto/remove/{idImpuesto}").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.POST, "/impuesto/asignar").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/impuesto/impuestos_de_producto/{idProducto}").hasRole(VENDEDOR)

                //


                // pree venta epic 1
                .requestMatchers(HttpMethod.GET, "/productos/sucursal").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/productos/obtener_all").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/productos/codigoBarra/{cod}").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/productos/categoria/{categoria}").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/venta/condiciones_venta").hasRole(VENDEDOR)

                //venta
                .requestMatchers(HttpMethod.POST, "/venta/crear").hasRole(VENDEDOR)
                .requestMatchers(HttpMethod.GET, "/venta/historial_en_fechas").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/venta/visualizar_productos_vendidos").hasRole(ADMINISTRADOR);


    }

    private void configureAdministradorEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/gestionDeCombustible/cargarCombustible").hasRole(ADMINISTRADOR);
    }
}
