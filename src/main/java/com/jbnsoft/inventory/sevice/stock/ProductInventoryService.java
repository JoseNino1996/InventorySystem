package com.jbnsoft.inventory.sevice.stock;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.ProductInventoryRepository;
import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductInventoryService implements IProductInventoryService {
    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    StockLogService stockLogService;

    @Override
    public ProductInventory create(ProductInventory productInventory) {
        if (isAlreadyStored(productInventory)) return null;
        return productInventoryRepository.save(productInventory);
    }

    private boolean isAlreadyStored(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findByProductId(productInventory.getProduct().getId());
        if(storedProductInventory != null) {
            return true;
        }
        return false;
    }

    @Override
    public ProductInventory update(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findById(productInventory.getId());
        productInventory.setQuantity(storedProductInventory.getQuantity());

        return productInventoryRepository.save(productInventory);
    }

    @Override
    public void deleteById(Long id) {
        productInventoryRepository.deleteById(id);
    }

    @Override
    public ProductInventory findById(Long id) {
        return productInventoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductInventory> findAll() {
        List<ProductInventory> listOfProductInventory  = new ArrayList<>();
        for(ProductInventory productInventory : productInventoryRepository.findAll()) {

            listOfProductInventory.add(productInventory);
        }
        return listOfProductInventory;
    }

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty, ProcessOrder processOrder, List<ProductInventory> productInventoryList) throws Exception {
         processOrder.processOrderQuantity(productIdAndOrderedQty,productInventoryList);
    }

    @Override
    public ProductInventory addStock(ProductInventory productInventory) {
        updateProductInventoryDetails(productInventory);
        productInventoryRepository.save(productInventory);

        createStockLog(productInventory);

        return productInventory;
    }

    private void createStockLog(ProductInventory productInventory) {
        StockLog stockLog = new StockLog();

        stockLog.setDate(new Date());
        stockLog.setAddedQuantity(productInventory.getQuantity());
        stockLog.setProductInventory(productInventory);

        stockLogService.create(stockLog);
    }

    private void updateProductInventoryDetails(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findById(productInventory.getId());

        productInventory.setQuantity(storedProductInventory.getQuantity() + productInventory.getQuantity());
        productInventory.setPrice(storedProductInventory.getPrice());
        productInventory.setProduct(storedProductInventory.getProduct());
    }

    @Override
    public ProductInventory findByProductId(long productId) {
        Map<Long,ProductInventory> mappedProductInventoryList =  mapProductInventoryList(findAll());
        ProductInventory productInventory = mappedProductInventoryList.get(productId);

        return  productInventory;

    }

    @Override
    public Map<Long, ProductInventory> mapProductInventoryList(List<ProductInventory> productInventoryList) {
        Map<Long,ProductInventory> mappedProductInventory = new HashMap<>();

        for(ProductInventory productInventory : productInventoryList) {
            mappedProductInventory.put(productInventory.getProduct().getId(),productInventory);
        }
        return  mappedProductInventory;
    }



    @Override
    public void saveAll(Iterable<ProductInventory> productInventories) {
        productInventoryRepository.saveAll(productInventories);
    }

}
