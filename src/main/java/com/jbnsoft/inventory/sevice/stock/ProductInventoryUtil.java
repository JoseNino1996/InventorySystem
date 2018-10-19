package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component

public  final  class ProductInventoryUtil {

    private ProductInventoryUtil() { }

    public static Map<Long, ProductInventory> getMappedProductInventory(List<ProductInventory> productInventoryList) {
        Map<Long,ProductInventory> mappedProductInventory = new HashMap<>();

        for(ProductInventory productInventory : productInventoryList) {
            mappedProductInventory.put(productInventory.getProduct().getId(),productInventory);
        }
        return  mappedProductInventory;
    }


    public static Map<Long,Long> getMappedProductIdAndOrderQuantity(List<ProductOrder> productOrders , Map<Long,ProductInventory> mappedProductInventory) {
        Map<Long, Long> mappedProductIdAndOrderedQuantity = new HashMap<>();
        for (ProductOrder productOrder : productOrders) {

            ProductInventory productInventory = mappedProductInventory.get(productOrder.getProduct().getId());

            mappedProductIdAndOrderedQuantity.put(productInventory.getId(), productOrder.getOrderedQty());
        }
        return  mappedProductIdAndOrderedQuantity;
    }





}
