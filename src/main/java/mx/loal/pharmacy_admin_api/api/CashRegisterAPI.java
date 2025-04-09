package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.CashRegisterDto;
import mx.loal.pharmacy_admin_api.payload.request.OpenCashRegister;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Caja registradora API",
    description = """
        Módulo para la gestión de las cajas registradoras, para realizar las aperturas y cierres
    """)
@RequestMapping(EndpointsConstants.CASH_REGISTER_ENDPOINT)
public interface CashRegisterAPI {

    @Operation(
        description = "Función que realiza la consulta de la caja actual",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/getCurrentCashRegister")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<CashRegisterDto> getCurrentCashRegister();

    @Operation(
        description = "Función que realiza la apertura de una caja",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping("/openCashRegister")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<CashRegisterDto> openCashRegister(@RequestBody @Valid OpenCashRegister openCashRegister);

}
