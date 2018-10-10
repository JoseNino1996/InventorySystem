package com.jbnsoft.inventory.sevice.product;

import com.jbnsoft.inventory.repository.product.ProductOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;

import java.util.List;

public interface IProductOrderService {
    ProductOrder create(ProductOrder productOrder) throws Exception;
    void delete(Long id);
    ProductOrder update(ProductOrder productOrder, Long id);
    ProductOrder findById(Long id);



}
