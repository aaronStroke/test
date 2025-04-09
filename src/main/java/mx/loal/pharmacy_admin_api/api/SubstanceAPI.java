package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SubstanceDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Sustancias API",
    description = """
        Módulo el cual se encarga de la gestión de la información del modulo de sustancias
    """)
@RequestMapping(EndpointsConstants.SUBSTANCES_ENDPOINT)
public interface SubstanceAPI {

    @Operation(
        description = "Función que consulta la información de las sustancias paginadas",
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
    ResponseEntity<Pagination<SubstanceDto>> findAll(
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size);

    @Operation(
        description = "Función que realiza la búsqueda de sustancias",
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
    ResponseEntity<List<SubstanceDto>> search(
        @Parameter(name = "q", description = "Texto para realizar la búsqueda")
            @RequestParam(value = "q", required = false) String query);

    @Operation(
        description = "Función que consulta la información de una sustancia en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{substanceId}")
    ResponseEntity<SubstanceDto> findById(
        @Parameter(name = "substanceId", description = "Indica el id de la sustancia que se desea consultar", required = true)
            @PathVariable("substanceId") Long substanceId);

    @Operation(
        description = "Función que consulta la información de una sustancia en base a su ID",
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
    ResponseEntity<SubstanceDto> save(
        @Parameter(name = "substance", description = "Objeto que contiene la información de la nueva sustancia", required = true)
            @RequestBody @Valid SubstanceDto substance);

    @Operation(
        description = "Función que consulta la información de una sustancia en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{substanceId}")
    ResponseEntity<SubstanceDto> update(
        @Parameter(name = "substanceId", description = "Indica el id de la sustancia que se requiere actualizar", required = true)
            @PathVariable("substanceId") Long substanceId,
        @Parameter(name = "substance", description = "Objeto que contiene la información de la nueva sustancia", required = true)
            @RequestBody @Valid SubstanceDto substance);

}
