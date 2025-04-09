package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.UserAPI;
import mx.loal.pharmacy_admin_api.payload.UserDto;
import mx.loal.pharmacy_admin_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> getCurrentUser() {
        var response = userService.getCurrentUser();
        return ResponseEntity.ok(response);
    }

}
