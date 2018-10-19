package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public abstract class ProcessOrder {
    @Autowired
    protected ProductInventoryService productInventoryService;

    public abstract void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                                Map<Long,ProductInventory> mappedProductInventory) ;
    


}
