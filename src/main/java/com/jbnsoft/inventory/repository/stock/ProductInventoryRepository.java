package com.jbnsoft.inventory.repository.stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductInventoryRepository extends CrudRepository<ProductInventory, Long> {

    @Query(value = "SELECT * FROM product_inventory WHERE product_id = ?1", nativeQuery = true)
    ProductInventory findByProductId(Long id);


}
