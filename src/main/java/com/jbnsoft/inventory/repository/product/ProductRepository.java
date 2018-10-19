package com.jbnsoft.inventory.repository.product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findProductByName(String name);

}
