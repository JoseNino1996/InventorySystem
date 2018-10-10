package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import java.util.List;
import java.util.Map;

public interface  IProductInventoryService {
      ProductInventory create(ProductInventory productInventory);
       ProductInventory update(ProductInventory productInventory);
       void delete(Long id);
       ProductInventory findById(Long id);
       List<ProductInventory> getListOfProductInventory();

      void saveAll(Iterable<ProductInventory> productInventories);
      void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty, ProcessOrder processOrder, List<ProductInventory> productInventoryList) throws Exception;

      ProductInventory addProductQuantity(ProductInventory productInventory);

      ProductInventory findProductInventoryByProductId(long id, List<ProductInventory> productInventoryList);

      ProductInventory validateProductIfAvailable(List<ProductOrder> productOrders) throws Exception;


      ProductInventory findProductInventoryByProductId(Long productId);


}
