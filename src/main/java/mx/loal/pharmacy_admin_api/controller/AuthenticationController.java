package mx.loal.pharmacy_admin_api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.payload.request.AuthRequest;
import mx.loal.pharmacy_admin_api.payload.response.AuthResponse;
import mx.loal.pharmacy_admin_api.service.AuthenticationService;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@Slf4j
@RequiredArgsConstructor
@RequestMapping(EndpointsConstants.AUTH_ENDPOINT)
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        log.info("Received request to authenticate user with username: {}", request.getUsername());
        var response = authenticationService.authenticate(request);
        log.info("User authenticated successfully with username: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }

}
