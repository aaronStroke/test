package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.MeasurementUnitDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Unidades de Medida API",
    description = """
        Módulo el cual se encarga de la gestión de la información del modulo de las unidades de medida.
    """)
@RequestMapping(EndpointsConstants.MEASUREMENT_UNITS_ENDPOINT)
public interface MeasurementUnitAPI {

    @Operation(
        description = "Función que realiza la consulta de las unidades de medida en base a paginación",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    ResponseEntity<Pagination<MeasurementUnitDto>> findAll(
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size);

    @Operation(
        description = "Función que realiza la búsqueda de unidades de medida",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<List<MeasurementUnitDto>> search(
        @Parameter(name = "q", description = "Texto para realizar la búsqueda")
            @RequestParam(value = "q", required = false) String query);

    @Operation(
        description = "Función que consulta la información de una unidad de medida en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{measurementUnitId}")
    ResponseEntity<MeasurementUnitDto> findById(
        @Parameter(name = "measurementUnitId", description = "Indica el id de la unidad de medida que se desea consultar", required = true)
            @PathVariable("measurementUnitId") Long measurementUnitId);

    @Operation(
            description = "Función que realiza el guardado de la información de una unidad de medida",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    ResponseEntity<MeasurementUnitDto> save(
        @Parameter(name = "measurementUnit", description = "Objeto que contiene la información de la nueva unidad de medida", required = true)
            @RequestBody @Valid MeasurementUnitDto measurementUnit);

    @Operation(
            description = "Función que realiza la actualización de la información de una unidad de medida en base a su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{measurementUnitId}")
    ResponseEntity<MeasurementUnitDto> update(
        @Parameter(name = "measurementUnitId", description = "Indica el id de la unidad de medida que se requiere actualizar", required = true)
            @PathVariable("measurementUnitId") Long measurementUnitId,
        @Parameter(name = "measurementUnit", description = "Objeto que contiene la información de la nueva unidad de medida", required = true)
            @RequestBody @Valid MeasurementUnitDto measurementUnit);

}
