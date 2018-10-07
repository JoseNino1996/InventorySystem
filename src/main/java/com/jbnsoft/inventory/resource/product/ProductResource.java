package com.jbnsoft.inventory.resource.product;


import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.sevice.product.IProductService;
import com.jbnsoft.inventory.sevice.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResource {

    @Autowired
    private IProductService productService;

    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        return  productService.createProduct(product);
    }

    @GetMapping("/findById")
    public  Product findById(@RequestParam Long id) {
        return  productService.findById(id);
    }
    @GetMapping("/findAll")
    public List<Product> findAll()  {
        return  productService.getListOfProducts();

    }
    @PostMapping("/update/{id}")
    public  Product update(@RequestBody Product product, @PathVariable("id") Long id)  {
        return productService.updateProduct(product, id);

    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        productService.deleteById(id);
    }
}
