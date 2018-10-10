package com.jbnsoft.inventory.sevice.stock.helper;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.IProductInventoryService;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public abstract class ProcessOrder {
    @Autowired
    protected ProductInventoryService iProductInventoryService;

    public abstract double processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                                List<ProductInventory> productInventoryList) throws Exception;

}
