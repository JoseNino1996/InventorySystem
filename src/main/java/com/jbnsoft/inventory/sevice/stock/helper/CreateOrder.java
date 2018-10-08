package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class CreateOrder extends ProcessOrder {

    @Override
    public double processOrderQuantity(Map<Long, Long> productIdAndOrderedQty) throws Exception {
        List<ProductInventory> productInventoryList = new ArrayList<>();
        double subtotal = 0;

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {
            ProductInventory productInventory = checkIfExist(entry.getKey());

            if(!checkProductAvailability(entry.getValue(),productInventory.getQuantity())) {
                throw new Exception("Insufficient product quantity! remaining product quantity:" + productInventory.getQuantity() );
            }
            productInventory.setQuantity(productInventory.getQuantity() - entry.getValue());

            productInventoryList.add(productInventory);
            subtotal += entry.getValue() * productInventory.getPrice();
        }


        iProductInventoryService.saveAll(productInventoryList);
        return subtotal;
    }

}
