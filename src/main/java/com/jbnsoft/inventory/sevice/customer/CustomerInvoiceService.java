package com.jbnsoft.inventory.sevice.customer;


import com.jbnsoft.inventory.repository.customer.CustomerInvoice;
import com.jbnsoft.inventory.repository.customer.CustomerInvoiceRepository;
import com.jbnsoft.inventory.repository.product.ProductOrder;
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
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private DeleteOrder deleteOrder;

    @Override
    public CustomerInvoice create(CustomerInvoice customerInvoice) throws Exception {
        List<ProductOrder> productOrderList = customerInvoice.getProductOrderList();

       customerInvoice.setSubtotal(mapProductIdandOrderQuantity(productOrderList,createOrder));


       if(!checkCustomerPayment(customerInvoice)) {
            throw new Exception("Insufficient payment!");
       }
        return customerInvoiceRepository.save(customerInvoice);
    }

    @Override
    public void delete(Long id) throws Exception {
        CustomerInvoice storedCustomerInvoice = findById(id);

        if(storedCustomerInvoice==null) { throw new Exception("Invoice not found"); }

        List<ProductOrder> productOrderList = storedCustomerInvoice.getProductOrderList();

        mapProductIdandOrderQuantity(productOrderList,deleteOrder);

        customerInvoiceRepository.deleteById(id);
    }

    @Override
    public CustomerInvoice update(CustomerInvoice customerInvoice, Long id) throws Exception {
        CustomerInvoice storedCustomerInvoice =findById(id);
        List<ProductOrder> currentOrderList = storedCustomerInvoice.getProductOrderList();

        mapProductIdandOrderQuantity(currentOrderList, deleteOrder);
        List<ProductOrder> newOrderList = customerInvoice.getProductOrderList();
        customerInvoice.setSubtotal(mapProductIdandOrderQuantity(newOrderList,createOrder));
        customerInvoice.setId(storedCustomerInvoice.getId());
        return customerInvoiceRepository.save(customerInvoice);
    }


    private double mapProductIdandOrderQuantity(List<ProductOrder> productOrders, ProcessOrder processOrder) throws Exception {
        Map<Long, Long> productIdAndOrderedQty = new HashMap<>();
        ProductInventory productInventory;
        for (ProductOrder productOrder : productOrders) {
            productInventory = productOrder.getProductInventory();
            productIdAndOrderedQty.put(productInventory.getId(), productOrder.getOrderedQty());
        }
       return productInventoryService.processOrderQuantity(productIdAndOrderedQty, processOrder);

    }

    @Override
    public CustomerInvoice findById(Long id) {
        return customerInvoiceRepository.findById(id).orElse(null  );
    }

    @Override
    public List<CustomerInvoice> getListOfCustomersInvoice() {
        List<CustomerInvoice> listOfCustomersInvoice = new ArrayList<>();
        for(CustomerInvoice customerInvoice : customerInvoiceRepository.findAll()) {
            listOfCustomersInvoice.add(customerInvoice);
        }
        return listOfCustomersInvoice;
    }

    @Override
    public boolean checkCustomerPayment(CustomerInvoice customerInvoice) {
        if(customerInvoice.getPayment() >= customerInvoice.getSubtotal()) {
            customerInvoice.setChange(customerInvoice.getPayment() - customerInvoice.getSubtotal());
            return true;
        }
        return false;
    }
}
