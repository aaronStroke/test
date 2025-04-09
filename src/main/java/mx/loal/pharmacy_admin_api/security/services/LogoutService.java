package mx.loal.pharmacy_admin_api.security.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static mx.loal.pharmacy_admin_api.utils.constants.AuthConstants.AUTHORIZATION_HEADER;
import static mx.loal.pharmacy_admin_api.utils.constants.AuthConstants.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;

        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_PREFIX))
            return;

        jwt = authHeader.substring(BEARER_PREFIX.length());

        var storedToken = tokenRepository.findByAccessToken(jwt)
                .orElse(null);

        if (Objects.nonNull(storedToken)) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
