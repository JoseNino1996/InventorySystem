package com.jbnsoft.inventory.sevice.product;

import com.jbnsoft.inventory.repository.product.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product, Long id);
    Product findById(Long id);
    void deleteById(Long id);
    List<Product> getListOfProducts();


}
