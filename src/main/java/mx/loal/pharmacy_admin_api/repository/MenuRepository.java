package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
