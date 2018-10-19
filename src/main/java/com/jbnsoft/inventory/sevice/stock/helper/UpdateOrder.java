package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Component
@Qualifier("update")
public class UpdateOrder extends  ProcessOrder {


    private ProductInventory productInventory;



    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQuantity, Map<Long, ProductInventory> mappedProductInventory) {
        List<ProductInventory> productInventoryList = new ArrayList<>();
        Map<Long,Long> currentProductIdAndOrderedQuantity =  getCurrentProductIdAndOrderedQuantity();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQuantity.entrySet()) {
            long orderQuantity = entry.getValue();

            productInventory = mappedProductInventory.get( entry.getKey() );
            Long currentOrderQuantity = currentProductIdAndOrderedQuantity.get(productInventory.getProduct().getId());

            if(isCurrentOrderQuantityNotNull(currentOrderQuantity)) {
                processCurrentOrderQuantity(orderQuantity, currentOrderQuantity);
            }else {
                productInventory.setQuantity(orderQuantity - productInventory.getQuantity());
            }

            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);
    }

    private void processCurrentOrderQuantity(long orderQuantity, long currentOrderQuantity ) {
            long newOrderQuantity = 0;
            long storedQuantity= productInventory.getQuantity();

            if(orderQuantity > currentOrderQuantity) {
                newOrderQuantity  = storedQuantity - (orderQuantity - currentOrderQuantity);
            }else if(orderQuantity < currentOrderQuantity) {
                newOrderQuantity  = storedQuantity + (currentOrderQuantity - orderQuantity);
            }
            productInventory.setQuantity(newOrderQuantity);
    }

    private boolean isCurrentOrderQuantityNotNull( Long currentOrderQuantity) {
      return  currentOrderQuantity != null ? true : false ;
    }

}
