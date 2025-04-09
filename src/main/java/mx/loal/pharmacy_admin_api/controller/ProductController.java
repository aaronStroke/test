package mx.loal.pharmacy_admin_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.api.ProductAPI;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.ProductDto;
import mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount;
import mx.loal.pharmacy_admin_api.service.ProductService;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController implements ProductAPI {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductDto>> searchProducts(String query, String barCode) {
        var response = productService.searchProducts(query, barCode);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Pagination<ProductDto>> findAll(int page, int size, String query, ProductCategory category) {
        var response = productService.findAll(page, size, query, category);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProductDto> findById(Long productId) {
        var response = productService.findById(productId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProductDto> save(ProductDto product) {
        var response = productService.save(product);
        return ResponseEntity
            .status(CREATED)
            .body(response);
    }

    @Override
    public ResponseEntity<ProductDto> update(Long productId, ProductDto product) {
        var response = productService.update(productId, product);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MissingStockProductsCount> getMissingStockProductsCount(Integer count) {
        var response = productService.getMissingStockProductsCount(count);
        return ResponseEntity.ok(response);
    }
}
