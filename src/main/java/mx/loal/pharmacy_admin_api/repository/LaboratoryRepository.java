package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Laboratory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {

    Page<Laboratory> findAllByOrderByNameAsc(Pageable pageable);

    Page<Laboratory> findAllByNameIgnoreCaseOrderByNameAsc(Pageable pageable, String name);

    @Query("""
        SELECT DISTINCT l
        FROM Laboratory l
        WHERE ( :search IS NULL OR LOWER(l.name) LIKE CONCAT('%', LOWER(:search), '%') )
    """)
    List<Laboratory> searchLaboratories(String search);

    boolean existsByNameIgnoreCase(String name);
}
