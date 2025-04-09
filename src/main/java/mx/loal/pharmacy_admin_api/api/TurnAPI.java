package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.TurnDto;
import mx.loal.pharmacy_admin_api.payload.request.ChangeTurnStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants.TURNS_ENDPOINT;

@Tag(
    name = "Turnos API",
    description = """
        Módulo el cual se encarga de la gestión de los turnos generados
    """)
@RequestMapping(TURNS_ENDPOINT)
public interface TurnAPI {

    @Operation(
        description = """
            Función que realiza la consulta de todos los turnos del día en curso
        """,
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    ResponseEntity<List<TurnDto>> getAllTurns();

    @Operation(
            description = """
            Función que realiza la consulta de todos los turnos del día en curso sin filtros
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/all-turns/{typeConsultId}")
    ResponseEntity<List<TurnDto>> allTurns(
            @PathVariable(required = false) Long typeConsultId);

    @Operation(
        description = """
            Función que realiza la consulta del turno que está en estatus 'ATTENDING'
        """,
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/attending-turn")
    ResponseEntity<TurnDto> getAttendingTurn();

    @Operation(
        description = """
            Función que realiza el registro de un nuevo turno
        """,
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping
    ResponseEntity<TurnDto> addTurn(
        @Parameter(name = "turn", description = "Objeto que contiene la información del nuevo turno", required = true)
            @RequestBody @Valid TurnDto turn);

    @PutMapping("/{turnId}")
    ResponseEntity<TurnDto> updateStatus(
        @Parameter(name = "turnId", description = "Indica el id del turno que cambiará de estatus", required = true)
            @PathVariable("turnId") Long turnId,
        @Parameter(name = "status", description = "Indica el nuevo estatus del turno", required = true)
            @RequestBody ChangeTurnStatus status);

}
