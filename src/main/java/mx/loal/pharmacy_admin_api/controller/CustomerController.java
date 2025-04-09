package mx.loal.pharmacy_admin_api.controller;


import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.api.CustomerAPI;
import mx.loal.pharmacy_admin_api.payload.CustomerDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerController implements CustomerAPI {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<Pagination<CustomerDto>> getAllCustomer(int page, int size) {
        var response =customerService.findAll(page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long customerId) {
        var response = customerService.findById(customerId);
        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<List<CustomerDto>> searchCustomer(String name) {
        var response = customerService.searchCustomer(name);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CustomerDto> addCustomer(CustomerDto customer) {
        var response = customerService.save(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(CustomerDto customer, Long customerId) {
        var response = customerService.update( customerId,customer);
        return  ResponseEntity.ok(response);
    }
}
