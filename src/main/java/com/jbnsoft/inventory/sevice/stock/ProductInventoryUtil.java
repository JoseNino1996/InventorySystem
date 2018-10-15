package com.jbnsoft.inventory.sevice.stock;

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





}
