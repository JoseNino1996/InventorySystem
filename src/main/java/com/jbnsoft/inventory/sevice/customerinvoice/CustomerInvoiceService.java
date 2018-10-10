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

    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private DeleteOrder deleteOrder;


    @Override
    public CustomerInvoice create(CustomerInvoice customerInvoice) throws Exception {
        List<ProductOrder> productOrderList = customerInvoice.getProductOrderList();

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(customerInvoice);

        processOrderInProductInventory(productOrderList,createOrder);
        return savedCustomerInvoice;
    }


    @Override
    public void delete(Long id) throws Exception {
        CustomerInvoice storedCustomerInvoice = findById(id);
        List<ProductOrder> productOrderList = storedCustomerInvoice.getProductOrderList();

        customerInvoiceRepository.deleteById(id);
        processOrderInProductInventory(productOrderList,deleteOrder);
    }

    @Override
    public CustomerInvoice update(CustomerInvoice customerInvoice) throws Exception {
        CustomerInvoice storedCustomerInvoice =findById(customerInvoice.getId());
        List<ProductOrder> currentOrderList = storedCustomerInvoice.getProductOrderList();
        processOrderInProductInventory(currentOrderList, deleteOrder);

        List<ProductOrder> newOrderList = customerInvoice.getProductOrderList();

        CustomerInvoice savedCustomerInvoice = customerInvoiceRepository.save(customerInvoice);

        processOrderInProductInventory(newOrderList,createOrder);
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


    private void  processOrderInProductInventory(List<ProductOrder> productOrders, ProcessOrder processOrder) throws Exception {
        Map<Long, Long> productIdAndOrderedQty = new HashMap<>();

        List<ProductInventory> availableProducts = new ArrayList<>();
        List<ProductInventory> storedInventoryProduct = productInventoryService.getListOfProductInventory();


        for (ProductOrder productOrder : productOrders) {
            Product product = productOrder.getProduct();

            ProductInventory productInventory = productInventoryService.findProductInventoryByProductId(product.getId(), storedInventoryProduct);

            availableProducts.add(productInventory);

            productIdAndOrderedQty.put(productInventory.getId(), productOrder.getOrderedQty());
        }

        productInventoryService.processOrderQuantity(productIdAndOrderedQty, processOrder,availableProducts);
    }


}
