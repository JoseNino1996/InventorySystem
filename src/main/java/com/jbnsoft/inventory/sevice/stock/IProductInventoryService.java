package com.jbnsoft.inventory.sevice.stock;


import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface  IProductInventoryService {
    ProductInventory create(ProductInventory productInventory);

    ProductInventory update(ProductInventory productInventory);

    void deleteById(Long id);

    ProductInventory findById(Long id);

    List<ProductInventory> findAll();

    void saveAll(Iterable<ProductInventory> productInventories);


    void processOrderQuantity(List<ProductOrder> productOrders, String transactionType) throws Exception;

    ProductInventory addStock(ProductInventory productInventory);


    ProductInventory findByProductId(long productId);








}