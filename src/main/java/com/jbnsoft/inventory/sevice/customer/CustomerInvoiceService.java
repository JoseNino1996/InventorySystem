package com.jbnsoft.inventory.sevice.customer;


import com.jbnsoft.inventory.repository.customer.CustomerInvoice;
import com.jbnsoft.inventory.repository.customer.CustomerInvoiceRepository;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.product.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.product.ProductOrderService;
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
    ProductOrderService productOrderService;


    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private DeleteOrder deleteOrder;


    @Override
    public CustomerInvoice create(CustomerInvoice customerInvoice) throws Exception {
        List<ProductOrder> productOrderList = customerInvoice.getProductOrderList();

        double amountDue = getAmountDueInProcessedOrderedQuantity(productOrderList,createOrder);
        customerInvoice.setAmountDue(amountDue);

        return customerInvoiceRepository.save(customerInvoice);
    }


    @Override
    public void delete(Long id) throws Exception {
        CustomerInvoice storedCustomerInvoice = findById(id);
        List<ProductOrder> productOrderList = storedCustomerInvoice.getProductOrderList();

        getAmountDueInProcessedOrderedQuantity(productOrderList,deleteOrder);

        customerInvoiceRepository.deleteById(id);
    }

    @Override
    public CustomerInvoice update(CustomerInvoice customerInvoice) throws Exception {
        CustomerInvoice storedCustomerInvoice =findById(customerInvoice.getId());
        List<ProductOrder> currentOrderList = storedCustomerInvoice.getProductOrderList();


        getAmountDueInProcessedOrderedQuantity(currentOrderList, deleteOrder);
        List<ProductOrder> newOrderList = customerInvoice.getProductOrderList();
        double amountDue = getAmountDueInProcessedOrderedQuantity(newOrderList,createOrder);
        customerInvoice.setAmountDue(amountDue);


        validateCustomerPayment(storedCustomerInvoice, customerInvoice);

        customerInvoice.setId(storedCustomerInvoice.getId());
        return customerInvoiceRepository.save(customerInvoice);
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

    @Override
    public boolean checkTenderedAmount(CustomerInvoice customerInvoice)  {
        if(customerInvoice.getAmountTendered() >= customerInvoice.getAmountDue()) {
             return true;
        }
        return false;
    }

    private void validateCustomerPayment(CustomerInvoice storedCustomerInvoice, CustomerInvoice newCustomerInvoice) throws Exception {
        if(newCustomerInvoice.getAmountTendered()==0) {
            storedCustomerInvoice.setAmountDue(newCustomerInvoice.getAmountDue());

            if(checkTenderedAmount(storedCustomerInvoice)) {
                newCustomerInvoice.setAmountTendered(storedCustomerInvoice.getAmountTendered());
                   } else {
                throw new Exception("Insufficient entered payment!" + " subtotal is :" + newCustomerInvoice.getAmountDue());
            }
        } else {
            if(!checkTenderedAmount(newCustomerInvoice))  {
                throw new Exception("Insufficient entered payment!" + " subtotal is :" + newCustomerInvoice.getAmountDue());
            }
        }
    }

    private double getAmountDueInProcessedOrderedQuantity(List<ProductOrder> productOrders, ProcessOrder processOrder) throws Exception {
        Map<Long, Long> productIdAndOrderedQty = new HashMap<>();

        List<ProductInventory> availableProducts = new ArrayList<>();

        for (ProductOrder productOrder : productOrders) {
            Product product = productOrder.getProduct();

            ProductInventory productInventory = productInventoryService.validateProductIfAvailable(product.getId(), productInventoryService.getListOfProductInventory(), processOrder);
            availableProducts.add(productInventory);

            productIdAndOrderedQty.put(productInventory.getId(), productOrder.getOrderedQty());
        }
        double amountDue = productInventoryService.processOrderQuantity(productIdAndOrderedQty, processOrder,availableProducts);

        return amountDue;
    }


}
