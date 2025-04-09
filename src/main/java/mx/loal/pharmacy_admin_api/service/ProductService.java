package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.payload.ProductDto;
import mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;

import java.util.List;

public interface ProductService {

    List<ProductDto> searchProducts(String query, String barCode);

    Pagination<ProductDto> findAll(int page, int size, String query, ProductCategory category);

    ProductDto findById(Long productId);

    ProductDto save(ProductDto product);
    ProductDto update(Long productId, ProductDto product);

    MissingStockProductsCount getMissingStockProductsCount(Integer count);

}
