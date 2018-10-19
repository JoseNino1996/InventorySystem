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
    public ProductInventory create(@RequestBody ProductInventory productInventory) throws Exception {
        return  productInventoryService.create(productInventory);
    }

    @GetMapping("/findById")
    public ProductInventory findById(@RequestParam Long id) {
        return productInventoryService.findById(id);

    }

    @DeleteMapping("/delete/{id}")
    public  void deleteById(@PathVariable("id") Long id) {
        productInventoryService.deleteById(id);
    }

    @PostMapping("/update")
    public  ProductInventory update(@RequestBody ProductInventory productInventory) {

        return  productInventoryService.update(productInventory);
    }
    @GetMapping("/findAll")
    public List<ProductInventory> findAll() {
        return productInventoryService.findAll();
    }


    @PostMapping("/addStock")
    public ProductInventory addStock(@RequestBody ProductInventory productInventory) {

        return  productInventoryService.addStock(productInventory);
    }

    @GetMapping("/findByProductId")
    public ProductInventory findByProductId(@RequestParam Long id){
            return productInventoryService.findByProductId(id) ;
    }

}
