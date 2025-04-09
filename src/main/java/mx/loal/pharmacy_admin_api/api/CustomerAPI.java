package mx.loal.pharmacy_admin_api.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.CustomerDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Clientes API",
        description = """
         Modulo el cual se encargara  de la  gestion de la informacion del cliente 
         """)

@RequestMapping(EndpointsConstants.CUSTOMER_ENDPOINT)
public interface CustomerAPI {


    @Operation(
            description = """
            Función que realiza la consulta de todos los clientes registrados
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
    ResponseEntity<Pagination<CustomerDto>> getAllCustomer(
            @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
            @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size
    );

    @Operation(
            description = """
            Función que realiza la consulta del cliente por id
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{customerId}")
    ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(name = "customerId", description = "Indica el id del cliente que se va a consultar", required = true)
            @PathVariable("customerId") Long customerId);



    @Operation(
            description = """
            Función que realiza la consulta de todos los clientes registrados
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/search")
    ResponseEntity<List<CustomerDto>> searchCustomer(
            @Parameter(name = "n", description = "Texto que busca al cliente")
            @RequestParam(value = "n", required = false) String name
    );




    @Operation(
            description = """
            Función que realiza el registro de un nuevo cliente
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
    ResponseEntity<CustomerDto> addCustomer(
            @Parameter(name = "customer", description = "Objeto del nuevo cliente", required = true)
            @RequestBody @Valid CustomerDto customer);

    @Operation(
            description = """
            Función que realiza la actualizacion de un registro del cliente
        """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{customerId}")
    ResponseEntity<CustomerDto> updateCustomer(
            @Parameter(name = "customer", description = " Objeto que tiene la nueva informacion del cliente", required = true)
            @RequestBody @Valid CustomerDto customer,
            @Parameter(name = "customer", description = "Indica el id del cliente que se va actualizar", required = true)
            @PathVariable  Long  customerId
    );



}
