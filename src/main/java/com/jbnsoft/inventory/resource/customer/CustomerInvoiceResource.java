package com.jbnsoft.inventory.resource.customer;


import com.jbnsoft.inventory.repository.customer.CustomerInvoice;

import com.jbnsoft.inventory.sevice.customer.ICustomerInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customer-invoice")
public class CustomerInvoiceResource {
    @Autowired
    private ICustomerInvoiceService customerInvoiceService;

    @PostMapping("/create")
    public CustomerInvoice create(@RequestBody CustomerInvoice customerInvoice) throws Exception {

        return  customerInvoiceService.create(customerInvoice);
    }

    @PostMapping("/update")
    public CustomerInvoice update(@RequestBody CustomerInvoice customerInvoice, @RequestParam Long id) throws Exception {
        return  customerInvoiceService.update(customerInvoice, id);
    }

    @GetMapping("/findById")
    public  CustomerInvoice findById(@RequestParam Long id) {
        return  customerInvoiceService.findById(id);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam Long id) throws Exception {
        customerInvoiceService.delete(id);
    }

    @GetMapping("/findAll")
    public List<CustomerInvoice> getListofCustomersInvoice() {
        return customerInvoiceService.getListOfCustomersInvoice();
    }

}
