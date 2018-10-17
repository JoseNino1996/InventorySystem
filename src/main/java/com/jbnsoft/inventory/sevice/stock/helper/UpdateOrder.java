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
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty, Map<Long, ProductInventory> mappedProductInventory) {
        List<ProductInventory> productInventoryList = new ArrayList<>();


        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {

            ProductInventory productInventory = mappedProductInventory.get(entry.getKey());

            Long currentOrderQuantity = existingProductIdAndOrderQuantity.get(productInventory.getProduct().getId());

            if(currentOrderQuantity != null) {
                if(entry.getValue() > currentOrderQuantity) {
                    productInventory.setQuantity(productInventory.getQuantity() - ( entry.getValue() - currentOrderQuantity) );
                }else if(entry.getValue() < currentOrderQuantity) {
                    productInventory.setQuantity(productInventory.getQuantity() + (currentOrderQuantity - entry.getValue()) );
                }
                productInventoryList.add(productInventory);
                continue;
            }

           productInventory.setQuantity(productInventory.getQuantity() - entry.getValue());
            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);
    }
}
