package mx.loal.pharmacy_admin_api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.ProductDto;
import mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount;
import mx.loal.pharmacy_admin_api.utils.constants.EndpointsConstants;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
    name = "Productos API",
    description = """
        Módulo el cual se encarga de la gestión de la información del modulo de productos
    """)
@RequestMapping(EndpointsConstants.PRODUCTS_ENDPOINT)
public interface ProductAPI {

    @Operation(
        description = """
            Función que realiza la búsqueda de productos ya sea por un texto o por el código de barras.
            La búsqueda por texto incluye la sustancia del producto o el nombre del producto
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_DOCTOR')")
    ResponseEntity<List<ProductDto>> searchProducts(
        @Parameter(name = "q", description = "Texto a buscar del producto")
            @RequestParam(value = "q", required = false) String query,
        @Parameter(name = "barCode", description = "Código de barras del producto")
            @RequestParam(value = "barCode", required = false) String barCode);

    @Operation(
        description = "Función que consulta la información de los productos paginados",
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
    ResponseEntity<Pagination<ProductDto>> findAll(
        @Parameter(name = "page", description = "Número de pagina a consultar", required = true)
            @RequestParam("page") int page,
        @Parameter(name = "size", description = "Cantidad de registros por pagina", required = true)
            @RequestParam("size") int size,
        @Parameter(name = "q", description = "Búsqueda de texto que contenga un producto")
        @RequestParam(value = "q", required = false) String query,
        @Parameter(name = "category", description = "Categoría a la que pertenece un producto")
            @RequestParam(value = "category", required = false) ProductCategory category);

    @Operation(
        description = "Función que consulta la información de un producto en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_DOCTOR')")
    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> findById(
        @Parameter(name = "productId", description = "Indica el id del producto que se desea consultar", required = true)
            @PathVariable("productId") Long productId);

    @Operation(
        description = "Función que consulta la información de un producto en base a su ID",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    ResponseEntity<ProductDto> save(
        @Parameter(name = "product", description = "Objeto que contiene la información del nuevo producto", required = true)
            @RequestBody @Valid ProductDto product);

    @Operation(
        description = "Función que consulta la información de un producto en base a su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{productId}")
    ResponseEntity<ProductDto> update(
        @Parameter(name = "productId", description = "Indica el id del producto que se requiere actualizar", required = true)
            @PathVariable("productId") Long productId,
        @Parameter(name = "product", description = "Objeto que contiene la información del nuevo producto", required = true)
            @RequestBody @Valid ProductDto product);


    @Operation(description = """
        Función que realiza la búsqueda del conteo de productos que tienen stock menor
        al parámetro count
    """,
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/getMissingStockProductsCount")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    ResponseEntity<MissingStockProductsCount> getMissingStockProductsCount(
        @Parameter(name = "count", description = "Valor del conteo para realizar el filtro")
            @RequestParam(value = "count", defaultValue = "5", required = false) Integer count);
}
