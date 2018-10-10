package com.jbnsoft.inventory.sevice.stock;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.product.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.ProductInventoryRepository;
import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductInventoryService implements IProductInventoryService {
    @Autowired
    private ProductInventoryRepository productInventoryRepository;




    @Override
    public ProductInventory create(ProductInventory productInventory) {
        return productInventoryRepository.save(productInventory);
    }

    @Override
    public ProductInventory update(ProductInventory productInventory, Long id) {
        ProductInventory storedProductInventory = productInventoryRepository.findById(id).orElse(null   );
          productInventory.setId(storedProductInventory.getId());
        return productInventoryRepository.save(productInventory);
    }

    @Override
    public void delete(Long id) {
        productInventoryRepository.deleteById(id);
    }

    @Override
    public ProductInventory findById(Long id) {
        return productInventoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductInventory> getListOfProductInventory() {
        List<ProductInventory> listOfProductInventory  = new ArrayList<>();
        for(ProductInventory productInventory : productInventoryRepository.findAll()) {
            if(productInventory == null) { continue; }
            listOfProductInventory.add(productInventory);
        }
        return listOfProductInventory;
    }

    @Override
    public double processOrderQuantity(Map<Long, Long> productIdAndOrderedQty, ProcessOrder processOrder, List<ProductInventory> productInventoryList) throws Exception {
        return processOrder.processOrderQuantity(productIdAndOrderedQty,productInventoryList);
    }

    @Override
    public ProductInventory findProductInvetoryByProductId(Long id) {
        return productInventoryRepository.findByProductId(id);
    }

    @Override
    public ProductInventory validateProductIfAvailable(List<ProductOrder> productOrders, List<ProductInventory> productInventoryList, ProcessOrder processOrder) throws Exception {
     ProductInventory foundProductInventory = null;

       for(ProductInventory productInventory : productInventoryList) {
           Product storedProduct = productInventory.getProduct();

            for(ProductOrder productOrder : productOrders) {
                Product orderedProduct = productOrder.getProduct();

               if(storedProduct.getId() == orderedProduct.getId()) {
                   foundProductInventory = productInventory;
               }
            }
       }

        return foundProductInventory;
    }

    @Override
    public void saveAll(Iterable<ProductInventory> productInventories) {
        productInventoryRepository.saveAll(productInventories);
    }
}
