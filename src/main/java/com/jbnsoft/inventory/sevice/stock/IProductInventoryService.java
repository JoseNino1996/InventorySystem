package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;

import java.util.List;


public interface  IProductInventoryService {
    ProductInventory create(ProductInventory productInventory) throws Exception;

    ProductInventory update(ProductInventory productInventory) throws Exception;

    void deleteById(Long id);

    ProductInventory findById(Long id);

    List<ProductInventory> findAll();

    void saveAll(Iterable<ProductInventory> productInventories);


    void processOrderQuantity(String transactionType, List<ProductOrder>... productOrders) throws Exception;

    ProductInventory addStock(ProductInventory productInventory);


    ProductInventory findByProductId(long productId);








}