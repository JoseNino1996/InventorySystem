package com.jbnsoft.inventory.sevice.stock.utilities;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public  class ProcessOrderUtil {
    @Autowired
    public  ProductInventoryService productInventoryService;

    private Map<Long, Long> currentProductIdAndOrderedQuantity;

    private ProductInventory productInventory;
    private Map<Long,ProductInventory> productInventoryMap;
    public  void createOrder(Map<Long, Long> productIdAndOrderedQty) {
        List<ProductInventory> productInventoryList = new ArrayList<>();
        loadProductInventories();
        for (Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {

             productInventory = productInventoryMap.get(entry.getKey());


            productInventory.setQuantity(productInventory.getQuantity() - entry.getValue());

            productInventoryList.add(productInventory);

        }


        productInventoryService.saveAll(productInventoryList);

    }
    private void loadProductInventories() {
            productInventoryMap = new HashMap<>();
            for (ProductInventory productInventory : productInventoryService.findAll()) {
                productInventoryMap.put(productInventory.getId(), productInventory);

        }
    }

    public  void deleteOrder(Map<Long, Long> productIdAndOrderedQty) {

        List<ProductInventory> productInventoryList = new ArrayList<>();
        loadProductInventories();
        for(Map.Entry<Long, Long> entry : productIdAndOrderedQty.entrySet()) {

            ProductInventory productInventory = productInventoryMap.get(entry.getKey());

            productInventory.setQuantity(productInventory.getQuantity() + entry.getValue());

            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);


    }
    public  void updateOrder(Map<Long, Long> productIdAndOrderedQuantity) {

        List<ProductInventory> productInventoryList = new ArrayList<>();
        loadProductInventories();
        for(Map.Entry<Long, Long> entry : productIdAndOrderedQuantity.entrySet()) {
            long orderQuantity = entry.getValue();

             productInventory = productInventoryMap.get( entry.getKey() );
            Long currentOrderQuantity = currentProductIdAndOrderedQuantity.get(productInventory.getProduct().getId());

            if(isCurrentOrderQuantityNotNull(currentOrderQuantity)) {
                processCurrentOrderQuantity(orderQuantity, currentOrderQuantity);
            }else {
                productInventory.setQuantity(productInventory.getQuantity() - orderQuantity  );
            }

            productInventoryList.add(productInventory);
        }

        productInventoryService.saveAll(productInventoryList);

    }



    private void processCurrentOrderQuantity(long orderQuantity, long currentOrderQuantity ) {
        long newStoredQuantity ;
        long storedQuantity= productInventory.getQuantity();

        if(orderQuantity > currentOrderQuantity) {
            newStoredQuantity  = storedQuantity - (orderQuantity - currentOrderQuantity);
        }else if(orderQuantity < currentOrderQuantity) {
            newStoredQuantity  = storedQuantity + (currentOrderQuantity - orderQuantity);
        }else {
            newStoredQuantity = storedQuantity;
        }
        productInventory.setQuantity(newStoredQuantity);
    }

    private boolean isCurrentOrderQuantityNotNull( Long currentOrderQuantity) {
        return  currentOrderQuantity != null ? true : false ;
    }



    public void setCurrentProductIdAndOrderedQuantity (Map < Long, Long > currentProductIdAndOrderedQuantity){
        this.currentProductIdAndOrderedQuantity = currentProductIdAndOrderedQuantity;
    }



}
