package com.jbnsoft.inventory.sevice.stock.helper;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.IProductInventoryService;
import com.jbnsoft.inventory.sevice.stock.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public abstract class ProcessOrder {
    @Autowired
    protected ProductInventoryService iProductInventoryService;

    public abstract double processOrderQuantity(Map<Long, Long> productIdAndOrderedQty) throws Exception;


     protected ProductInventory checkIfExist(Long id) throws Exception {
      List<ProductInventory> productInventoryList = iProductInventoryService.getListOfProductInventory();
      ProductInventory foundProductInventory =null;

      
      for(ProductInventory productInventory :  productInventoryList) {
          if (productInventory.getId() == id) {
              foundProductInventory = productInventory;
          }
      }
      if(foundProductInventory != null) {
          return foundProductInventory;
      } else {
          throw new Exception("Product is not available");
      }
  }
}
