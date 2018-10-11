package com.jbnsoft.inventory.resource.customer;

import com.jbnsoft.inventory.repository.customer.Customer;
import com.jbnsoft.inventory.sevice.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    private ICustomerService customerService;

    @PostMapping("/create")
    public Customer create(@RequestBody Customer customer) throws Exception {

            return customerService.save(customer);

    }

    @GetMapping("/findById")
    public Customer findById(@RequestParam Long id) {
        return customerService.findById(id);
    }

    @DeleteMapping("/delete")
    public  void deleteById(@RequestParam Long id) throws Exception {
        customerService.deleteById(id);
    }

    @GetMapping("/findAll")
    public List<Customer> findAll() {
        return  customerService.findAll();
    }

    @PostMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer) throws Exception {
        return  customerService.update(customer);
    }


}
