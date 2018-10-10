package com.jbnsoft.inventory.sevice.customerinvoice;

import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;

public interface IProductOrderService {
    ProductOrder create(ProductOrder productOrder) throws Exception;
    void delete(Long id);
    ProductOrder update(ProductOrder productOrder);
    ProductOrder findById(Long id);



}
