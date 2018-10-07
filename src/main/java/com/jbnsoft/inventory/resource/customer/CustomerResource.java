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
    public Customer createCustomer(@RequestBody Customer customer) throws Exception {

            return customerService.saveCustomer(customer);

    }

    @GetMapping("/findById")
    public Customer findCustomerById(@RequestParam Long id) {
        return customerService.findById(id);
    }

    @DeleteMapping("/delete")
    public  void deleteCustomerById(@RequestParam Long id) throws Exception {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/findAll")
    public List<Customer> getListofCustomer() {
        return  customerService.getListOfCustomer();
    }
//    @PostMapping("/update/{id}")
//    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id) throws Exception {
//        return  customerService.updateCustomer(customer, id);
//    }


}
