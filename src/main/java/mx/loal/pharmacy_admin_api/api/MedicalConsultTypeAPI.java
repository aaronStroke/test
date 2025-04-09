package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultTypeDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Tipos de consultas médicas API",
    description = """
        Módulo el cual se encarga de la gestión de la información de los tipos de consultas médicas.
    """)
@RequestMapping(EndpointsConstants.MEDICAL_CONSULT_TYPES_ENDPOINT)
public interface MedicalConsultTypeAPI {

    @Operation(
        description = "Función que realiza la consulta la información en paginación de los tipos de consultas médicas",
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
    ResponseEntity<Pagination<MedicalConsultTypeDto>> findAll(
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size);

    @Operation(
            description = "Función que realiza la consulta la información en paginación de los tipos de consultas médicas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/catalog")
    ResponseEntity<List<MedicalConsultTypeDto>> getCatalog();

    @Operation(
        description = "Función que consulta la información de un tipo de consulta médica en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{medicalConsultTypeId}")
    ResponseEntity<MedicalConsultTypeDto> findById(
        @Parameter(name = "medicalConsultTypeId", description = "Indica el id del tipo de consulta que se desea consultar", required = true)
            @PathVariable("medicalConsultTypeId") Long medicalConsultTypeId);

    @Operation(
        description = "Función que realiza el guardado de la información de un tipo de consulta médica",
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
    ResponseEntity<MedicalConsultTypeDto> save(
        @Parameter(name = "medicalConsultType", description = "Objeto que contiene la información del nuevo tipo de consulta", required = true)
            @RequestBody @Valid MedicalConsultTypeDto medicalConsultType);

    @Operation(
        description = "Función que realiza la actualización de la información de un tipo de consulta médica en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{medicalConsultTypeId}")
    ResponseEntity<MedicalConsultTypeDto> update(
        @Parameter(name = "medicalConsultTypeId", description = "Indica el id del tipo de consulta que se requiere actualizar", required = true)
            @PathVariable("medicalConsultTypeId") Long medicalConsultTypeId,
        @Parameter(name = "medicalConsultType", description = "Objeto que contiene la información del tipo de consulta médica", required = true)
            @RequestBody @Valid MedicalConsultTypeDto medicalConsultType);

}
