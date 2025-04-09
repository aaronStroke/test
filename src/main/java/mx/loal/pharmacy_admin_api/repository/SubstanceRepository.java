package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Substance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubstanceRepository extends JpaRepository<Substance, Long> {

    Page<Substance> findAllByOrderByNameAsc(Pageable pageable);

    @Query("""
        SELECT s
        FROM substance s
        WHERE ( :search IS NULL OR LOWER(CAST(s.name AS string)) LIKE CONCAT('%', LOWER(CAST(:search AS string)), '%') )
    """)
    List<Substance> searchSubstances(String search);

    @Query("""
        SELECT s
        FROM substance s
        WHERE s.id in :ids
    """)
    List<Substance> findAllByIds(List<Long> ids);

    boolean existsByNameIgnoreCase(String name);

}
