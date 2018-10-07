package com.jbnsoft.inventory.resource.product;


import com.jbnsoft.inventory.repository.product.ProductOrder;
import com.jbnsoft.inventory.sevice.product.IProductOrderService;
import com.jbnsoft.inventory.sevice.product.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-order")
public class ProductOrderResource {
    @Autowired
    private IProductOrderService productOrderService;

    @PostMapping("/create")
    public ProductOrder create(@RequestBody ProductOrder productOrder) throws Exception {
        return  productOrderService.create(productOrder);
    }
    @GetMapping("/findById")
    public  ProductOrder findById(@RequestParam Long id) {
        return  productOrderService.findById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productOrderService.delete(id);
    }
}
