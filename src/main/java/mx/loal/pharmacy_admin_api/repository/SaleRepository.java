package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("""
        SELECT SUM(s.total)
        FROM Sale s
        WHERE s.date BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalSalesAmountByDate(LocalDateTime startDate, LocalDateTime endDate);

    Long countSaleByDateBetween(LocalDateTime dateAfter, LocalDateTime dateBefore);

}
