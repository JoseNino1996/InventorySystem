package com.jbnsoft.inventory.resource.stock;


import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.product.Product;
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
    public  ProductInventory update(@RequestBody ProductInventory productInventory) {

        return  productInventoryService.update(productInventory);
    }
    @GetMapping("/findAll")
    public List<ProductInventory> findAll() {
        return productInventoryService.getListOfProductInventory();
    }


    @PostMapping("/addStock")
    public ProductInventory addStock(@RequestBody ProductInventory productInventory) {

        return  productInventoryService.addProductQuantity(productInventory);
    }

    @PostMapping("/validateProductOrder")
    public ProductInventory validateProductOrder(@RequestBody List<ProductOrder> productOrders) throws Exception {
            return productInventoryService.validateProductIfAvailable(productOrders) ;
    }

}
