package com.jbnsoft.inventory.sevice.customerinvoice;


import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;
import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoiceRepository;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import com.jbnsoft.inventory.sevice.stock.helper.DeleteOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerInvoiceService implements  ICustomerInvoiceService {

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;
    @Autowired
    private ProductInventoryService productInventoryService;



    @Override
    public CustomerInvoice create(CustomerInvoice customerInvoice) {

        customerInvoice.getDate();
        customerInvoice.getAmountDue();

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(customerInvoice);
        productInventoryService.processOrderQuantity(customerInvoice.getProductOrderList(),customerInvoice.getTransactionType());

        return savedCustomerInvoice;
    }


    @Override
    public void delete(Long id) {
        CustomerInvoice storedCustomerInvoice = findById(id);

        customerInvoiceRepository.deleteById(id);
        productInventoryService.processOrderQuantity( storedCustomerInvoice.getProductOrderList(),storedCustomerInvoice.getTransactionType());

    }

    @Override
    public CustomerInvoice update(CustomerInvoice customerInvoice)  {

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(customerInvoice);

        productInventoryService.processOrderQuantity(customerInvoice.getProductOrderList(),customerInvoice.getTransactionType());

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
