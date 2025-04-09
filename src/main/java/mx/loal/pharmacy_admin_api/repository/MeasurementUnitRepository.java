package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.MeasurementUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {

    Page<MeasurementUnit> findAllByOrderByNameAsc(Pageable pageable);

    @Query("""
        SELECT DISTINCT mu
        FROM measurement_unit mu
        WHERE ( :search IS NULL OR LOWER(mu.name) LIKE CONCAT('%', LOWER(:search), '%') )
    """)
    List<MeasurementUnit> searchMeasurementUnits(String search);

    @Query("""
        SELECT mu
        FROM measurement_unit mu
        WHERE mu.id in :ids
    """)
    List<MeasurementUnit> findAllByIds(List<Long> ids);

    boolean existsByNameIgnoreCase(String name);

}
