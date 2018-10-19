package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class CreateOrder extends ProcessOrder {

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                     Map<Long,ProductInventory> mappedProductInventory) {

        List<ProductInventory> productInventoryList = new ArrayList<>();

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {
            long productId = entry.getKey();
            long orderQuantity = entry.getValue();

            ProductInventory productInventory = mappedProductInventory.get( productId );
            productInventory.setQuantity(productInventory.getQuantity() - orderQuantity );
            productInventoryList.add(productInventory);

        }

        productInventoryService.saveAll(productInventoryList);
   }

}
