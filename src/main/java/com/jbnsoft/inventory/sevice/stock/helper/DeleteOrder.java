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
    public double processOrderQuantity(Map<Long, Long> productIdAndOrderedQty,
                                       List<ProductInventory> availableProducts) {

        List<ProductInventory> productInventoryList = new ArrayList<>();
        double subtotal = 0;

            for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {
                for(ProductInventory productInventory : availableProducts) {
                    Product product = productInventory.getProduct();
                    if(product.getId() == entry.getKey()) {
                        // do the calculation
                        productInventory.setQuantity(productInventory.getQuantity()+ entry.getValue());
                        productInventoryList.add(productInventory);
                        subtotal += entry.getValue() * productInventory.getPrice();
                    }
                }
        }

        iProductInventoryService.saveAll(productInventoryList);
        return subtotal;
    }
}
