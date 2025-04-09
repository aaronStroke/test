package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.LaboratoryDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Laboratorios API",
    description = """
        Módulo el cual se encarga de la gestión de la información del modulo de laboratorios
    """)
@RequestMapping(EndpointsConstants.LABORATORIES_ENDPOINT)
public interface LaboratoryAPI {

    @Operation(
        description = "Función que consulta la información de los laboratorios paginados",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<Pagination<LaboratoryDto>> findAll(
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size,
        @Parameter(name = "q", description = "Texto para realizar la búsqueda")
            @RequestParam(value = "q", required = false) String query);

    @Operation(
        description = "Función que realiza la búsqueda de laboratorios",
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
    ResponseEntity<List<LaboratoryDto>> search(
        @Parameter(name = "q", description = "Texto para realizar la búsqueda")
            @RequestParam(value = "q", required = false) String query);

    @Operation(
        description = "Función que consulta la información de un laboratorio en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{laboratoryId}")
    ResponseEntity<LaboratoryDto> findById(
        @Parameter(name = "laboratoryId", description = "Indica el id del laboratorio que se desea consultar", required = true)
            @PathVariable("laboratoryId") Long laboratoryId);

    @Operation(
        description = "Función que consulta la información de un laboratorio en base a su ID",
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
    ResponseEntity<LaboratoryDto> save(
        @Parameter(name = "laboratory", description = "Objeto que contiene la información del nuevo laboratorio", required = true)
            @RequestBody @Valid LaboratoryDto laboratory);

    @Operation(
        description = "Función que consulta la información de un laboratorio en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{laboratoryId}")
    ResponseEntity<LaboratoryDto> update(
        @Parameter(name = "laboratoryId", description = "Indica el id del laboratorio que se requiere actualizar", required = true)
            @PathVariable("laboratoryId") Long laboratoryId,
        @Parameter(name = "laboratory", description = "Objeto que contiene la información del nuevo laboratorio", required = true)
            @RequestBody @Valid LaboratoryDto laboratory);

}
