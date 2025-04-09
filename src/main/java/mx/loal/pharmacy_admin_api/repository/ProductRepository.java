package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Product;
import mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount;
import mx.loal.pharmacy_admin_api.utils.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Product p
        LEFT JOIN p.activeSubstances acs
        LEFT JOIN acs.substance s
        WHERE ( :search IS NULL OR LOWER(CAST(p.name AS string)) LIKE CONCAT('%', LOWER(CAST(:search AS string)), '%') )
           OR ( :search IS NULL OR LOWER(CAST(s.name AS string)) LIKE CONCAT('%', LOWER(CAST(:search AS string)), '%') )
          AND ( :category IS NULL OR p.category = :category )
        ORDER BY p.name
    """)
//    AND ( :substanceId IS NULL OR s.substanceId = :substanceId )
    Page<Product> findProducts(Pageable pageable,
        @Param("search") String search, @Param("category") ProductCategory category);

    @Query("""
        SELECT DISTINCT p
        FROM Product p
        LEFT JOIN p.activeSubstances acs
        LEFT JOIN acs.substance s
        WHERE ( :query IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:query), '%') )
           OR ( :query IS NULL OR LOWER(s.name) LIKE CONCAT('%', LOWER(:query), '%') )
        ORDER BY p.name
    """)
    List<Product> searchProductsByQuery(@Param("query") String query);

    @Query("""
        SELECT DISTINCT p
        FROM Product p
        WHERE p.barCode = :barCode
    """)
    List<Product> searchProductsByBarCode(@Param("barCode") String barCode);

    @Query("""
        SELECT new mx.loal.pharmacy_admin_api.payload.response.MissingStockProductsCount(COUNT(p))
        FROM Product p
        WHERE p.stock < :stock
    """)
    MissingStockProductsCount countAllByStockIsLessThanEqual(Integer stock);

    @Modifying
    @Query("""
        UPDATE Product p
        SET p.stock = :stock
        WHERE p.id = :id
    """)
    void updateStockByProductId(@Param("id") Long id, @Param("stock") Integer stock);

}
