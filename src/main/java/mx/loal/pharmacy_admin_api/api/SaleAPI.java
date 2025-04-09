package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.SaleDto;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(
    name = "Ventas API",
    description = """
        Módulo el cual se encarga de la gestión de las ventas
    """)
@RequestMapping(EndpointsConstants.SALES_ENDPOINT)
public interface SaleAPI {

    @Operation(
        description = "Función que realiza el guardado de una venta",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping("/registerSale")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<SaleDto> registerSale(
        @Parameter(name = "sale", description = "Objeto que contiene la información para guardar el registro de una venta")
            @RequestBody @Valid SaleDto sale);

    @Operation(
        description = "Función que realiza la consulta de ventas en el día indicado",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/findSalesByDate/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<Pagination<SaleDto>> findSalesByDate(
        @Parameter(name = "date", description = "Fecha que se requiere realizar la búsqueda en formato yyyy-MM-dd", required = true)
            @PathVariable("date") String date,
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size);

    @Operation(
        description = "Función que realiza la consulta del total del monto de las ventas del día indicado",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/getTotalSalesAmountByDate/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<BigDecimal> getTotalSalesAmountByDate(
        @Parameter(name = "date", description = "Fecha que se requiere realizar la búsqueda en formato yyyy-MM-dd", required = true)
        @PathVariable("date") String date);
}
