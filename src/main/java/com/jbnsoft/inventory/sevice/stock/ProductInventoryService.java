package com.jbnsoft.inventory.sevice.stock;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.ProductInventoryRepository;
import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductInventoryService implements IProductInventoryService {
    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    StockLogService stockLogService;

    @Override
    public ProductInventory create(ProductInventory productInventory) {
        return productInventoryRepository.save(productInventory);
    }

    @Override
    public ProductInventory update(ProductInventory productInventory) {
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

            listOfProductInventory.add(productInventory);
        }
        return listOfProductInventory;
    }

    @Override
    public void processOrderQuantity(Map<Long, Long> productIdAndOrderedQty, ProcessOrder processOrder, List<ProductInventory> productInventoryList) throws Exception {
         processOrder.processOrderQuantity(productIdAndOrderedQty,productInventoryList);
    }

    @Override
    public ProductInventory addProductQuantity(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findById(productInventory.getId());
        productInventory.setQuantity(storedProductInventory.getQuantity() + productInventory.getQuantity());
        productInventory.setPrice(storedProductInventory.getPrice());
        productInventory.setProduct(storedProductInventory.getProduct());

        update(productInventory);

        StockLog stockLog = new StockLog();
        stockLog.setDate(new Date());
        stockLog.setAddedQuantity(productInventory.getQuantity());
        stockLog.setProductInventory(productInventory);
        stockLogService.create(stockLog);

        return productInventory;
    }



    @Override
    public ProductInventory validateProductIfAvailable(List<ProductOrder> productOrders) throws Exception {
        List<ProductInventory> productInventoryList = getListOfProductInventory();
        ProductInventory foundProductInventory = null;

        for (ProductInventory productInventory : productInventoryList) {
            Product storedProduct = productInventory.getProduct();

            for(ProductOrder productOrder : productOrders) {
                if (storedProduct.getId() == productOrder.getId()) {
                    foundProductInventory = productInventory;
                    break;
                }
            }
        }

        if(foundProductInventory != null) {

            return foundProductInventory;

        } else {

            throw new Exception("Product not available");

        }
    }

    @Override
    public ProductInventory findProductInventoryByProductId(Long productId) {
        return productInventoryRepository.findByProductId(productId);
    }

    @Override
    public ProductInventory findProductInventoryByProductId(long productId, List<ProductInventory> productInventoryList) {
        ProductInventory foundProductInventory = null;
            for (ProductInventory productInventory : productInventoryList) {
                Product storedProduct = productInventory.getProduct();

                    if (storedProduct.getId() ==productId) {
                        foundProductInventory = productInventory;
                        break;
                    }
            }

        return foundProductInventory;
    }

    @Override
    public void saveAll(Iterable<ProductInventory> productInventories) {
        productInventoryRepository.saveAll(productInventories);
    }

}
