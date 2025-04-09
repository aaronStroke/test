package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.loal.pharmacy_admin_api.payload.AgeGroupDto;
import mx.loal.pharmacy_admin_api.payload.PresentationDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(
    name = "Presentación API",
    description = """
        Módulo para la consulta de la información de los tipos de presentación
        que puede tener un producto
    """)
@RequestMapping(EndpointsConstants.PRESENTATIONS_ENDPOINT)
public interface PresentationAPI {

    @Operation(
        description = "Función que consulta todas las presentaciones",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    ResponseEntity<List<PresentationDto>> findAll();

}
