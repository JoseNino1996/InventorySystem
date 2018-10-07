package com.jbnsoft.inventory.resource.stock;


import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.IProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-inventory")
public class ProductInventoryResource  {
    @Autowired
    private IProductInventoryService productInventoryService;

    @PostMapping("/create")
    public ProductInventory create(@RequestBody ProductInventory productInventory) {
        return  productInventoryService.create(productInventory);
    }

    @GetMapping("/findById")
    public ProductInventory findById(@RequestParam Long id) {
        return productInventoryService.findById(id);

    }

    @DeleteMapping("/delete/{id}")
    public  void deleteById(@PathVariable("id") Long id) {
        productInventoryService.delete(id);
    }

    @PostMapping("/update")
    public  ProductInventory update(@RequestBody ProductInventory productInventory, @RequestParam("id") Long id) {

        return  productInventoryService.update(productInventory,id);
    }
    @GetMapping("/findAll")
    public List<ProductInventory> findAll() {
        return productInventoryService.getListOfProductInventory();
    }




}
