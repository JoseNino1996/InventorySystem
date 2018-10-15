package com.jbnsoft.inventory.sevice.stock.helper;
import com.jbnsoft.inventory.repository.customer.Customer;
import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.customerinvoice.CustomerInvoiceService;
import com.jbnsoft.inventory.sevice.stock.IProductInventoryService;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public abstract class ProcessOrder {
    @Autowired
    protected ProductInventoryService productInventoryService;
    @Autowired
    CustomerInvoiceService customerInvoiceService;
    public abstract void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                                Map<Long,ProductInventory> mappedProductInventory) ;

}
