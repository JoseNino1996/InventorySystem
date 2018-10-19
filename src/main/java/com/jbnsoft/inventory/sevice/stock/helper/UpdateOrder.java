package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Component
public class UpdateOrder extends  ProcessOrder {

    private Map<Long,Long> currentProductIdAndOrderedQuantity;

    public void setCurrentProductIdAndOrderedQuantity(Map<Long, Long> currentProductIdAndOrderedQuantity) {
        this.currentProductIdAndOrderedQuantity = currentProductIdAndOrderedQuantity;
    }

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQuantity, Map<Long, ProductInventory> mappedProductInventory) {
        List<ProductInventory> productInventoryList = new ArrayList<>();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQuantity.entrySet()) {
            long productId = entry.getKey();
            long orderQuantity = entry.getValue();

            ProductInventory productInventory = mappedProductInventory.get(productId);
            Long currentOrderQuantity = currentProductIdAndOrderedQuantity.get(productInventory.getProduct().getId());

            if(currentOrderQuantity != null) {

                processOrder(productInventory, orderQuantity, currentOrderQuantity);
                productInventoryList.add(productInventory);
                continue;
            }


            productInventory.setQuantity(orderQuantity - productInventory.getQuantity());
            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);
    }

    private void  processOrder(ProductInventory productInventory,long orderQuantity, long currentOrderQuantity ) {

            if(orderQuantity > currentOrderQuantity) {
                productInventory.setQuantity(productInventory.getQuantity() - (orderQuantity - currentOrderQuantity) );
            }else if(orderQuantity < currentOrderQuantity) {
                productInventory.setQuantity(productInventory.getQuantity() + (currentOrderQuantity - orderQuantity) );
            }
    }
}
