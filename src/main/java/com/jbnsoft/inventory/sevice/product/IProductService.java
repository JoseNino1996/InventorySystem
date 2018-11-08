package com.jbnsoft.inventory.sevice.product;

import com.jbnsoft.inventory.repository.product.Product;

import java.util.List;

public interface IProductService {
    Product create(Product product) throws Exception;
    Product update(Product product) throws Exception;
    Product findById(Long id);
    void deleteById(Long id);
    List<Product> findAll();
    boolean isNameExists(String name);



}
