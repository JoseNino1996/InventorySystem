package com.jbnsoft.inventory.sevice.customerinvoice;

import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;

import java.util.List;

public interface ICustomerInvoiceService  {
    CustomerInvoice create(CustomerInvoice customerInvoice) throws Exception;
    CustomerInvoice update(CustomerInvoice customerInvoice) throws Exception;
    void delete(Long id) throws Exception;
    CustomerInvoice findById(Long id);
    List<CustomerInvoice> findAll();



}
