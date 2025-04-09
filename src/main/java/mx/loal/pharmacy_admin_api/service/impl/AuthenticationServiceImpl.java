package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.model.Token;
import mx.loal.pharmacy_admin_api.model.User;
import mx.loal.pharmacy_admin_api.payload.request.AuthRequest;
import mx.loal.pharmacy_admin_api.payload.response.AuthResponse;
import mx.loal.pharmacy_admin_api.repository.TokenRepository;
import mx.loal.pharmacy_admin_api.repository.UserRepository;
import mx.loal.pharmacy_admin_api.security.services.JwtService;
import mx.loal.pharmacy_admin_api.service.AuthenticationService;
import mx.loal.pharmacy_admin_api.utils.enums.TokenType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        log.info("Authenticating user with username: {}", authRequest.getUsername());

        var user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Bad Credentials"));

        authenticateUser(authRequest.getUsername(), authRequest.getPassword());

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return buildAuthResponse(jwtToken, refreshToken);
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    private void saveUserToken(User user, String jwtToken) {

        var token = Token.builder()
            .user(user)
            .accessToken(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();

        try {
            tokenRepository.save(token);
        } catch (DataIntegrityViolationException e) {
            // Si el token ya existe, se actualiza
            var existingToken = tokenRepository.findByAccessToken(jwtToken)
                    .orElseThrow(() -> new RuntimeException("Deberia existir token pero no se encontro"));
            existingToken.setExpired(false);
            existingToken.setRevoked(false);
            tokenRepository.save(existingToken);
        }
    }

    private void revokeAllUserTokens(User user) {

        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private AuthResponse buildAuthResponse(String jwtToken, String refreshToken) {
        return AuthResponse
            .builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }
}
