package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DeleteOrder extends ProcessOrder {
    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                       List<ProductInventory> availableProducts) {

        List<ProductInventory> productInventoryList = new ArrayList<>();
        Map<Long,ProductInventory> mappedProductInventory  =  productInventoryService.mapProductInventoryList(availableProducts);

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {
            ProductInventory productInventory = mappedProductInventory.get(entry.getKey());

            if(productInventory.getProduct().getId().equals(entry.getKey())) {
                productInventory.setQuantity(productInventory.getQuantity() + entry.getValue());
                productInventoryList.add(productInventory);
            }
        }
        productInventoryService.saveAll(productInventoryList);

    }
}
