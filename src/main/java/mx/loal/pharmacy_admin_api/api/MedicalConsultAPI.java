package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
    name = "Consulta Médica API",
    description = """
        Módulo el cual se encarga de la gestión de las consultas médicas
    """)
@RequestMapping(EndpointsConstants.MEDICAL_CONSULT_ENDPOINT)
public interface MedicalConsultAPI {

    @Operation(
        description = "Función que realiza el guardado de la información de una consulta médica",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/customer/{customerId}")
    ResponseEntity<MedicalConsultDto> save(
        @Parameter(name = "customerId", description = "Indica el id del cliente/paciente al que se agregará la consulta", required = true)
            @PathVariable("customerId") Long customerId,
        @Parameter(name = "medicalConsult", description = "Objeto que contiene la información de la nueva consulta medica", required = true)
            @RequestBody @Valid MedicalConsultDto medicalConsult);

}
