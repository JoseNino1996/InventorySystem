package com.jbnsoft.inventory.sevice.stock;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.customerinvoice.Transaction;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.ProductInventoryRepository;
import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.stock.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductInventoryService implements IProductInventoryService {
    @Autowired
    private ProductInventoryRepository productInventoryRepository;
    @Autowired
    StockLogService stockLogService;

    @Autowired
     private ProcessOrderUtil processOrderUtil;


    @Override
    public ProductInventory create(ProductInventory productInventory) throws Exception {
        if (isAlreadyStored(productInventory)) {
            throw new Exception("Product has already an inventory!");
        }

        ProductInventory savedProductInventory = productInventoryRepository.save(productInventory);
        createStockLog(productInventory);
        return savedProductInventory;
    }

    private boolean isAlreadyStored(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findByProductId(productInventory.getProduct().getId());

        return storedProductInventory == null ? false : true;
    }

    private boolean isEmpty(ProductInventory productInventory) {

        return productInventory == null ? true : false;
    }

    @Override
    public ProductInventory update(ProductInventory productInventory) throws Exception {
        ProductInventory oldInventory = findById(productInventory.getId());

        if(isEmpty(productInventory)) { throw new Exception("ProductInventory not found!"); }

        productInventory.setQuantity(oldInventory.getQuantity());

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
    public void processOrderQuantity(String transactionType, List<ProductOrder>... productOrders)  {
        List<ProductOrder>  newOrders =  productOrders[0];

        Map<Long,ProductInventory> mappedProductInventory = ProductInventoryUtil.getMappedProductInventory(findAll());

        Map<Long, Long> mappedProductIdAndOrderedQuantity = ProductInventoryUtil.getMappedProductIdAndOrderQuantity(newOrders,mappedProductInventory);

        if(transactionType.equals(Transaction.CREATE.getTransactionType())) {

            processOrderUtil.createOrder(mappedProductIdAndOrderedQuantity);

        }else if(transactionType.equals(Transaction.DELETE.getTransactionType())) {

            processOrderUtil.deleteOrder(mappedProductIdAndOrderedQuantity);

        } else if (transactionType.equals(Transaction.UPDATE.getTransactionType())){
            List<ProductOrder> currentOrders = productOrders[1];

            updateOrderQuantity(mappedProductIdAndOrderedQuantity,mappedProductInventory,currentOrders);

        }

    }

    private void updateOrderQuantity(Map<Long,Long>productIdAndOrderedQuantity
                                    ,Map<Long,ProductInventory> mappedProductInventory,
                                     List<ProductOrder> currentOrders) {

        Map<Long,Long> existingProductIdAndOrderQuantity = ProductInventoryUtil.getMappedProductIdAndOrderQuantity(currentOrders, mappedProductInventory);

        processOrderUtil.setCurrentProductIdAndOrderedQuantity(existingProductIdAndOrderQuantity);
        processOrderUtil.updateOrder(productIdAndOrderedQuantity);

    }



    @Override
    public ProductInventory addStock(ProductInventory productInventory) {
        updateProductInventoryDetails(productInventory);
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
        productInventoryRepository.save(productInventory);
    }

    @Override
    public ProductInventory findByProductId(long productId) {
        Map<Long,ProductInventory> mappedProductInventoryList = ProductInventoryUtil.getMappedProductInventory(findAll());
        ProductInventory productInventory = mappedProductInventoryList.get(productId);

        return  productInventory;

    }

    @Override
    public void saveAll(Iterable<ProductInventory> productInventories) {
        productInventoryRepository.saveAll(productInventories);
    }

}
