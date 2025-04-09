package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.loal.pharmacy_admin_api.payload.AgeGroupDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(
    name = "Grupos de edad API",
    description = """
        Módulo para la consulta de la información de los grupos de edad para un producto
        en caso de ser un medicamento
    """)
@RequestMapping(EndpointsConstants.AGE_GROUPS_ENDPOINT)
public interface AgeGroupAPI {

    @Operation(
        description = "Función que consulta todos los grupos de edad",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    ResponseEntity<List<AgeGroupDto>> findAll();

}
