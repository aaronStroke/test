package mx.loal.pharmacy_admin_api.service;


import mx.loal.pharmacy_admin_api.payload.CustomerDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;

import java.util.List;


public interface CustomerService {

    Pagination<CustomerDto> findAll(int page, int size);

    CustomerDto findById(Long customerId);

    List<CustomerDto> searchCustomer (String name);

    CustomerDto save(CustomerDto customer);

    CustomerDto update(Long customerId, CustomerDto customer);

    Boolean delete ();






}
