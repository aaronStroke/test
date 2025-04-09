package mx.loal.pharmacy_admin_api.security.config;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.security.filters.JwtAuthFilter;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import static mx.loal.pharmacy_admin_api.utils.constants.RoleConstants.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;

    private static final String[] WHITE_LIST_URL = {
        EndpointsConstants.AUTH_PATTERN_ENDPOINT,
        EndpointsConstants.ACTUATOR_PATTERN_ENDPOINT,
        EndpointsConstants.TURNS_WEBSOCKET_ENDPOINT,
        EndpointsConstants.TURNS_WEBSOCKET_PATTERN_ENDPOINT,
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req
                    .requestMatchers(WHITE_LIST_URL).permitAll()

                    .requestMatchers(GET, EndpointsConstants.LABORATORIES_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.LABORATORIES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.LABORATORIES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.LABORATORIES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.MEASUREMENT_UNITS_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.MEASUREMENT_UNITS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.MEASUREMENT_UNITS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.MEASUREMENT_UNITS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.SUBSTANCES_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.SUBSTANCES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.SUBSTANCES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.SUBSTANCES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.MEDICAL_CONSULT_TYPES_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.MEDICAL_CONSULT_TYPES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.MEDICAL_CONSULT_TYPES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.MEDICAL_CONSULT_TYPES_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.MEDICAL_CONSULT_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.MEDICAL_CONSULT_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_DOCTOR)
                    .requestMatchers(PUT, EndpointsConstants.MEDICAL_CONSULT_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_DOCTOR)
                    .requestMatchers(DELETE, EndpointsConstants.MEDICAL_CONSULT_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_DOCTOR)

                    .requestMatchers(GET, EndpointsConstants.PRODUCTS_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.PRODUCTS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.PRODUCTS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.PRODUCTS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.USERS_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.USERS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.USERS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(DELETE, EndpointsConstants.USERS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                    .requestMatchers(GET, EndpointsConstants.MENUS_PATTERN_ENDPOINT).authenticated()

                    .requestMatchers(GET, EndpointsConstants.TURNS_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.TURNS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                    .requestMatchers(PUT, EndpointsConstants.TURNS_PATTERN_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER, ROLE_DOCTOR)

                    .requestMatchers(GET, EndpointsConstants.CUSTOMER_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(POST, EndpointsConstants.CUSTOMER_PATTERN_ENDPOINT).authenticated()
                    .requestMatchers(PUT, EndpointsConstants.CUSTOMER_PATTERN_ENDPOINT).authenticated()

                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout ->
                logout
                    .logoutUrl(EndpointsConstants.LOGOUT_ENDPOINT)
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            ).build();
    }
}
