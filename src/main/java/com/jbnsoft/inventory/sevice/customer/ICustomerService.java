package com.jbnsoft.inventory.sevice.customer;


import com.jbnsoft.inventory.repository.customer.Customer;

import java.util.List;


public interface  ICustomerService {

    Customer save(Customer customer) throws Exception;
    Customer update(Customer customer) throws Exception;
    void deleteById(Long id) throws Exception;
    Customer findById(Long id);
    List<Customer> findAll();

    boolean isCustomerExist(String name );




}
