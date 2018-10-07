package com.jbnsoft.inventory.sevice.product;

import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements  IProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        product.setDateCreated(new Date());
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(Product product, Long id) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getListOfProducts() {
        List<Product> productList = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            productList.add(product);
        }
        return productList;
    }
}
