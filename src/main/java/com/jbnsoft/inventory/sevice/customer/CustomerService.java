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
    public Customer saveCustomer(Customer customer)  {

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer =findById(id);
        if(customer != null )  {
            customerRepository.delete(customer);
            System.out.println("Customer has been deleted!");
        } else {
             throw new Exception("Customer not found");
        }
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> getListOfCustomer() {
        List<Customer> listOfCustomers = new ArrayList<>();
        for(Customer customer : customerRepository.findAll()) {
            listOfCustomers.add(customer);
        }
        return listOfCustomers;
    }


}
