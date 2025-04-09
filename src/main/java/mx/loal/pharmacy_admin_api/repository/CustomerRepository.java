package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Customer;
import mx.loal.pharmacy_admin_api.payload.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

     Page<Customer> findAll(Pageable pageable);

     @Query("""
        SELECT DISTINCT new mx.loal.pharmacy_admin_api.payload.CustomerDto(c)
        FROM Customer c
        WHERE LOWER(CONCAT(c.firstName, ' ', c.secondName)) LIKE CONCAT('%', LOWER(:name), '%')
    """)
     List<CustomerDto> searchCustomer(String name);






}
