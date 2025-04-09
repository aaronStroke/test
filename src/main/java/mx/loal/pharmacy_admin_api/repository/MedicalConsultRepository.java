package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.MedicalConsult;
import mx.loal.pharmacy_admin_api.model.Substance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalConsultRepository extends JpaRepository<MedicalConsult, Long> {

   }
