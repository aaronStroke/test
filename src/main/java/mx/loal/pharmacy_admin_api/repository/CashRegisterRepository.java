package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.CashRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {


    @Query("""
        SELECT c
        FROM CashRegister c
        WHERE DATE(c.openingDate) = :openingDate
    """)
    Optional<CashRegister> findCurrentCashRegister(LocalDate openingDate);
}
