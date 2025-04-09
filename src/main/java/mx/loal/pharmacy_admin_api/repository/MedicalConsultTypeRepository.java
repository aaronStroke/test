package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.MedicalConsultType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalConsultTypeRepository extends JpaRepository<MedicalConsultType, Long> {

    Page<MedicalConsultType> findAllByOrderByNameAsc(Pageable pageable);

    List<MedicalConsultType> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);



}
