package com.jbnsoft.inventory.sevice.customer;

import com.jbnsoft.inventory.repository.customer.CustomerInvoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICustomerInvoiceService  {
    CustomerInvoice create(CustomerInvoice customerInvoice) throws Exception;
    CustomerInvoice update(CustomerInvoice customerInvoice, Long id) throws Exception;
    void delete(Long id) throws Exception;
    CustomerInvoice findById(Long id);
    List<CustomerInvoice> getListOfCustomersInvoice();

    boolean checkCustomerPayment(CustomerInvoice customerInvoice);

}
