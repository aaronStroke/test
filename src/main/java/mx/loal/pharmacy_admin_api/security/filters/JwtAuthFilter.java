package mx.loal.pharmacy_admin_api.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.ExpiredTokenException;
import mx.loal.pharmacy_admin_api.exceptions.InvalidTokenException;
import mx.loal.pharmacy_admin_api.repository.TokenRepository;
import mx.loal.pharmacy_admin_api.security.services.JwtService;
import mx.loal.pharmacy_admin_api.utils.constants.AuthConstants;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("Processing request to: {}", request.getServletPath());

        if (isAuthPath(request)) {
            log.debug("Skipping JWT authentication for auth path");
            filterChain.doFilter(request, response);
            return;
        }

        final var authHeader = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);

        if (isInvalidAuthHeader(authHeader)) {
            log.debug("Invalid auth header, proceeding with filter chain");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = extractJwtFromHeader(authHeader);
        final String username;

        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredTokenException e) {
            log.warn("Expired token: {}", e.getMessage());
            handleException(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (InvalidTokenException e) {
            log.warn("Invalid token: {}", e.getMessage());
            handleException(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (username != null && isNotAuthenticated()) {
            processTokenAuthentication(request, jwt, username);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthPath(HttpServletRequest request) {
        return request.getServletPath().equals(EndpointsConstants.AUTH_ENDPOINT + "/login");
    }

    private boolean isInvalidAuthHeader(String authHeader) {
        return authHeader == null || !authHeader.startsWith(AuthConstants.BEARER_PREFIX);
    }

    private String extractJwtFromHeader(String authHeader) {
        return authHeader.substring(AuthConstants.BEARER_PREFIX.length());
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void processTokenAuthentication(HttpServletRequest request, String jwt, String username) {

        var userDetails = userDetailsService.loadUserByUsername(username);

        var isTokenValid = tokenRepository.findByAccessToken(jwt)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        log.debug("Processing token authentication for user: {}", username);
        log.debug("Is token valid: {}", isTokenValid);

        if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {

            var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.debug("Authentication set in SecurityContext for user: {}", username);
        } else {
            log.debug("Token validation failed for user: {}", username);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(AuthConstants.CONTENT_TYPE_JSON);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
