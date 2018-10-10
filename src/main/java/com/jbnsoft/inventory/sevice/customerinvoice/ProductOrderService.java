package com.jbnsoft.inventory.sevice.customerinvoice;

import com.jbnsoft.inventory.repository.product.Product;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrderRepository;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.sevice.stock.IProductInventoryService;
import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductOrderService implements  IProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private IProductInventoryService productInventoryService;

    @Autowired
    private CreateOrder createOrder;
    @Override
    public ProductOrder create(ProductOrder productOrder) throws Exception {
        Map<Long, Long> productIdAndOrderQty = new HashMap<>();
        Product product = productOrder.getProduct();
        ProductInventory productInventory = productInventoryService.findProductInventoryByProductId(product.getId());

        ProductOrder savedProductOrder = productOrderRepository.save(productOrder);

        productIdAndOrderQty.put(productInventory.getId(), productOrder.getOrderedQty());
        productInventoryService.processOrderQuantity(productIdAndOrderQty,createOrder, productInventoryService.getListOfProductInventory());

        return savedProductOrder;

    }

    @Override
    public void delete(Long id) {
        productOrderRepository.deleteById(id);
    }
    @Override
    public ProductOrder update(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public ProductOrder findById(Long id) {

        return productOrderRepository.findById(id).orElse(null  );
    }


}
