package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Component
public class UpdateOrder extends  ProcessOrder {

    private Map<Long,Long> existingProductIdAndOrderQuantity;


    public void setExistingProductIdAndOrderQuantity(Map<Long, Long> existingProductIdAndOrderQuantity) {
        this.existingProductIdAndOrderQuantity = existingProductIdAndOrderQuantity;
    }

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQuantity, Map<Long, ProductInventory> mappedProductInventory) {
        List<ProductInventory> productInventoryList = new ArrayList<>();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQuantity.entrySet()) {

            ProductInventory productInventory = mappedProductInventory.get(entry.getKey());
            Long currentOrderQuantity = existingProductIdAndOrderQuantity.get(productInventory.getProduct().getId());

            if(currentOrderQuantity != null) {
                processOrder(productInventory, entry.getValue(), currentOrderQuantity);
                productInventoryList.add(productInventory);
                continue;
            }

            processOrder(productInventory, entry.getValue(), currentOrderQuantity);
            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);
    }

    private void  processOrder(ProductInventory productInventory,long newOrderQuantity, long currentOrderQuantity ) {
        if(newOrderQuantity > currentOrderQuantity) {
            productInventory.setQuantity(productInventory.getQuantity() - (newOrderQuantity - currentOrderQuantity) );
        }else if(newOrderQuantity < currentOrderQuantity) {
            productInventory.setQuantity(productInventory.getQuantity() + (currentOrderQuantity - newOrderQuantity) );
        }

    }
}
