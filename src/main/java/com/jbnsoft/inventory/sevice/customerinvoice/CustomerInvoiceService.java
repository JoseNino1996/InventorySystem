package com.jbnsoft.inventory.sevice.customerinvoice;


import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;
import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoiceRepository;
import com.jbnsoft.inventory.repository.customerinvoice.Transaction;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerInvoiceService implements  ICustomerInvoiceService {

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;
    @Autowired
    private ProductInventoryService productInventoryService;



    @Override
    public CustomerInvoice create(CustomerInvoice customerInvoice) {

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(customerInvoice);
        productInventoryService.processOrderQuantity(Transaction.CREATE.getTransactionType(), customerInvoice.getProductOrderList());

        return savedCustomerInvoice;
    }


    @Override
    public void delete(Long id) {
        CustomerInvoice storedCustomerInvoice = findById(id);

        customerInvoiceRepository.deleteById(id);
        productInventoryService.processOrderQuantity(Transaction.DELETE.getTransactionType(),storedCustomerInvoice.getProductOrderList());

    }

    @Override
    public CustomerInvoice update(CustomerInvoice newInvoice)  {
        CustomerInvoice currentInvoice = findById(newInvoice.getId());

        productInventoryService.processOrderQuantity(Transaction.UPDATE.getTransactionType(),newInvoice.getProductOrderList(),currentInvoice.getProductOrderList());

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(newInvoice);
        return savedCustomerInvoice;
    }


    @Override
    public CustomerInvoice findById(Long id) {

        return customerInvoiceRepository.findById(id).orElse(null  );
    }

    @Override
    public List<CustomerInvoice> findAll() {
        List<CustomerInvoice> listOfCustomersInvoice = new ArrayList<>();
        for(CustomerInvoice customerInvoice : customerInvoiceRepository.findAll()) {
            listOfCustomersInvoice.add(customerInvoice);
        }
        return listOfCustomersInvoice;
    }

}
