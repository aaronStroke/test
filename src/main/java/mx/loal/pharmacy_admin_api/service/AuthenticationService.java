package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.model.User;
import mx.loal.pharmacy_admin_api.payload.request.AuthRequest;
import mx.loal.pharmacy_admin_api.payload.response.AuthResponse;

public interface AuthenticationService {

//    AuthResponse register(RegisterRequestDTO request);
    AuthResponse authenticate(AuthRequest authRequest);

}
