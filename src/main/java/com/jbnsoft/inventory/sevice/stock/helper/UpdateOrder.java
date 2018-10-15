package com.jbnsoft.inventory.sevice.stock.helper;

import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class UpdateOrder extends ProcessOrder {


    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                     Map<Long, ProductInventory> mappedProductInventory) {
        
        List<ProductInventory> productInventoryList = new ArrayList<>();
            // mapped stored list ..
           //pass the orderList

        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {
//            ProductInventory productInventory = mappedProductInventory.get(entry.getKey());
//            productInventory.setQuantity(productInventory.getQuantity() - entry.getValue());
//            productInventoryList.add(productInventory);


        }

    }
}
