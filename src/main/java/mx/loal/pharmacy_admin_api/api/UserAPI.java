package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.loal.pharmacy_admin_api.payload.UserDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
    name = "Usuario API",
    description = """
    Módulo el cual se encarga de la gestión de la información del modulo de usuarios
""")
@RequestMapping(EndpointsConstants.USERS_ENDPOINT)
public interface UserAPI {

    @Operation(
        description = "Función que consulta la información del usuario actual en sesión",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/current-user")
    ResponseEntity<UserDto> getCurrentUser();

}
