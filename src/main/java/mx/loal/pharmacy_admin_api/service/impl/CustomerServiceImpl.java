package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.exceptions.NoContentException;
import mx.loal.pharmacy_admin_api.exceptions.RequestException;
import mx.loal.pharmacy_admin_api.model.Customer;
import mx.loal.pharmacy_admin_api.payload.CustomerDto;
import mx.loal.pharmacy_admin_api.payload.Pagination;
import mx.loal.pharmacy_admin_api.repository.CustomerRepository;
import mx.loal.pharmacy_admin_api.service.CustomerService;
import mx.loal.pharmacy_admin_api.utils.constants.ExceptionMessageConstants;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Pagination<CustomerDto> findAll(int page, int size) {

        var result = customerRepository.findAll(PageRequest.of(page, size));

        if(result.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.CUSTOMER_NOT_FOUND);

        var customers = result
                .stream()
                .map(this::convertToDto)
                .toList();

        return Pagination
                .<CustomerDto>builder()
                .empty(result.isEmpty())
                .first(result.isFirst())
                .last(result.isLast())
                .last(result.isLast())
                .number(result.getNumber())
                .numberOfElements(result.getNumberOfElements())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .content(customers)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDto findById(Long customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.CUSTOMER_NOT_FOUND));
        return convertToDto(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDto> searchCustomer(String name) {
        if(StringUtils.isBlank(name))
            throw new RequestException("firtsName or SecondName is mandatory ");

        var customers = customerRepository.searchCustomer(name);

        if (customers.isEmpty())
            throw new NoContentException(ExceptionMessageConstants.CUSTOMER_NOT_FOUND);

        return customers;
    }

    @Transactional
    @Override
    public CustomerDto save(CustomerDto customer) {
        var newCustom = convertToEntity(customer);


        return convertToDto(customerRepository.save(newCustom));
    }

    @Transactional
    @Override
    public CustomerDto update(Long customerId, CustomerDto customer) {
        var existCustom = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoContentException(ExceptionMessageConstants.CUSTOMER_NOT_EXIST));

        if(!existCustom.getId().equals(customer.getId()))
            throw new RequestException(ExceptionMessageConstants.CUSTOMER_UPDATE_CONFLICT);

        BeanUtils.copyProperties(customer, existCustom);


        customerRepository.save(existCustom);
        return customer;
    }

    @Override
    public Boolean delete() {return null;}

    private Customer convertToEntity(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }

    private CustomerDto convertToDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
}
