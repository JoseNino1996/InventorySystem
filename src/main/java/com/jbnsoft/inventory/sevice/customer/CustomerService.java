package com.jbnsoft.inventory.sevice.customer;

import com.jbnsoft.inventory.repository.customer.Customer;

import com.jbnsoft.inventory.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) throws Exception {
        if(isCustomerExist(customer.getName())) { throw new Exception("Customer's already exist"); }
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) throws Exception {
        if(isCustomerExist(customer.getName())) { throw new Exception("Customer's already exist"); }

        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id){
        Customer customer =findById(id);
        if(customer == null) { return; }
        customerRepository.deleteById(customer.getId());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> listOfCustomers = new ArrayList<>();
        for(Customer customer : customerRepository.findAll()) {
            listOfCustomers.add(customer);
        }
        return listOfCustomers;
    }

    @Override
    public boolean isCustomerExist(String name) {
        Customer customer = customerRepository.findByName(name);

        return customer != null ? true : false;
    }


}
