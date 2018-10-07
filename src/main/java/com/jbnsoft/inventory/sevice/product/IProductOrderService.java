package com.jbnsoft.inventory.sevice.product;

import com.jbnsoft.inventory.repository.product.ProductOrder;

public interface IProductOrderService {
    ProductOrder create(ProductOrder productOrder) throws Exception;
    void delete(Long id);
    ProductOrder update(ProductOrder productOrder, Long id);
    ProductOrder findById(Long id);


}
