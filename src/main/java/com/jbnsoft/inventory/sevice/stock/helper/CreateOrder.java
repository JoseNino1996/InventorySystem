package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class CreateOrder extends ProcessOrder {

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                       List<ProductInventory> availableProducts) {

        List<ProductInventory> productInventoryList = new ArrayList<>();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {

             for(ProductInventory productInventory : availableProducts) {
                Product product = productInventory.getProduct();

                    if(product.getId().equals(entry.getKey())) {
                        productInventory.setQuantity(productInventory.getQuantity() - entry.getValue());
                        productInventoryList.add(productInventory);
                    }
            }

        }
        iProductInventoryService.saveAll(productInventoryList);
    }




}
