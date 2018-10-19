package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Component
public class UpdateOrder extends  ProcessOrder {

    private Map<Long,Long> currentProductIdAndOrderedQuantity;

    private ProductInventory productInventory;

    public void setCurrentProductIdAndOrderedQuantity(Map<Long, Long> currentProductIdAndOrderedQuantity) {
        this.currentProductIdAndOrderedQuantity = currentProductIdAndOrderedQuantity;
    }

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQuantity, Map<Long, ProductInventory> mappedProductInventory) {
        List<ProductInventory> productInventoryList = new ArrayList<>();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQuantity.entrySet()) {
            long orderQuantity = entry.getValue();

            productInventory = mappedProductInventory.get( entry.getKey() );
            Long currentOrderQuantity = currentProductIdAndOrderedQuantity.get(productInventory.getProduct().getId());

            if(isCurrentOrderQuantityNotNull(currentOrderQuantity)) {
                processCurrentOrderQuantity(orderQuantity, currentOrderQuantity);
            }else {
                processNewOrderQuantity(orderQuantity, productInventory.getQuantity());
            }

            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);
    }


    private void processNewOrderQuantity(long orderQuantity, long storedQuantity) {
        productInventory.setQuantity(orderQuantity - storedQuantity);

    }


    private void processCurrentOrderQuantity(long orderQuantity, long currentOrderQuantity ) {
            if(orderQuantity > currentOrderQuantity) {
                productInventory.setQuantity(productInventory.getQuantity() - (orderQuantity - currentOrderQuantity) );
            }else if(orderQuantity < currentOrderQuantity) {
                productInventory.setQuantity(productInventory.getQuantity() + (currentOrderQuantity - orderQuantity) );
            }
    }

    private boolean isCurrentOrderQuantityNotNull( Long currentOrderQuantity) {
      boolean result = currentOrderQuantity != null ? true : false ;

      return  result;
    }

}
