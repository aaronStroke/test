package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {
}
