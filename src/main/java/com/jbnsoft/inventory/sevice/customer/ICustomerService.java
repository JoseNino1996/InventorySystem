package com.jbnsoft.inventory.sevice.customer;


import com.jbnsoft.inventory.repository.customer.Customer;

import java.util.List;


public interface  ICustomerService {

    Customer saveCustomer(Customer customer) throws Exception;

    void deleteCustomer(Long id) throws Exception;
    Customer findById(Long id);
    List<Customer> getListOfCustomer();




}
