package com.jbnsoft.inventory.resource.product;


import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.sevice.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResource {

    @Autowired
    private IProductService productService;

    @PostMapping("/create")
    public Product create(@RequestBody Product product) throws Exception {
        return  productService.create(product);
    }

    @GetMapping("/findById")
    public  Product findById(@RequestParam Long id) {
        return  productService.findById(id);
    }
    @GetMapping("/findAll")
    public List<Product> findAll()  {
        return  productService.findAll();

    }


    @GetMapping("/findByName")
    public boolean findByName(@RequestParam String name) {
        return  productService.isNameExists(name);
    }

    @PostMapping("/update")
    public  Product update(@RequestBody Product product) throws Exception {
        return productService.update(product);

    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        productService.deleteById(id);
    }
}
