package com.jbnsoft.inventory.sevice.stock;
import com.jbnsoft.inventory.repository.customerinvoice.CustomerInvoice;
import com.jbnsoft.inventory.repository.customerinvoice.ProductOrder;
import com.jbnsoft.inventory.repository.customerinvoice.Transaction;
import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.ProductInventoryRepository;
import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.customerinvoice.CustomerInvoiceService;
import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import com.jbnsoft.inventory.sevice.stock.helper.DeleteOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import com.jbnsoft.inventory.sevice.stock.helper.UpdateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductInventoryService implements IProductInventoryService {
    @Autowired
    private ProductInventoryRepository productInventoryRepository;
    @Autowired
    CustomerInvoiceService customerInvoiceService;
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private DeleteOrder deleteOrder;
    @Autowired
    private UpdateOrder updateOrder;
    @Autowired
    StockLogService stockLogService;

    @Override
    public ProductInventory create(ProductInventory productInventory) {
        if (isAlreadyStored(productInventory)) return null;
        return productInventoryRepository.save(productInventory);
    }

    private boolean isAlreadyStored(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findByProductId(productInventory.getProduct().getId());
        if(storedProductInventory != null) {
            return true;
        }
        return false;
    }

    @Override
    public ProductInventory update(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findById(productInventory.getId());
        productInventory.setQuantity(storedProductInventory.getQuantity());

        return productInventoryRepository.save(productInventory);
    }

    @Override
    public void deleteById(Long id) {
        productInventoryRepository.deleteById(id);
    }

    @Override
    public ProductInventory findById(Long id) {
        return productInventoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductInventory> findAll() {
        List<ProductInventory> listOfProductInventory  = new ArrayList<>();
        for(ProductInventory productInventory : productInventoryRepository.findAll()) {

            listOfProductInventory.add(productInventory);
        }
        return listOfProductInventory;
    }

    @Override
    public void processOrderQuantity(CustomerInvoice customerInvoice)  {
        List<ProductOrder> productOrders = customerInvoice.getProductOrderList();
        String transactionType = customerInvoice.getTransactionType();
        List<ProductInventory> productInventoryList = findAll();
        Map<Long,ProductInventory> mappedProductInventory = ProductInventoryUtil.getMappedProductInventory(productInventoryList);
        Map<Long, Long> mappedProductIdAndOrderedQuantity = ProductInventoryUtil.getMappedProductIdAndOrderQuantity(productOrders,mappedProductInventory);

        if(transactionType.equalsIgnoreCase(String.valueOf(Transaction.CREATE))) {

            createOrder.processOrderQuantity(mappedProductIdAndOrderedQuantity, mappedProductInventory);

        }else if(transactionType.equalsIgnoreCase(String.valueOf(Transaction.DELETE))) {

            deleteOrder.processOrderQuantity(mappedProductIdAndOrderedQuantity,mappedProductInventory);

        } else if (transactionType.equalsIgnoreCase(String.valueOf(Transaction.UPDATE))){
            CustomerInvoice storedCustomerInvoice = customerInvoiceService.findById(customerInvoice.getId());

            Map<Long,Long> mappedCurrentProductOrder = ProductInventoryUtil.getMappedProductIdAndOrderQuantity(storedCustomerInvoice.getProductOrderList(), mappedProductInventory);
            updateOrder.setCurrentProductOrder(mappedCurrentProductOrder);

            updateOrder.processOrderQuantity(mappedProductIdAndOrderedQuantity,mappedProductInventory);
        }

    }







    @Override
    public ProductInventory addStock(ProductInventory productInventory) {
        updateProductInventoryDetails(productInventory);
        productInventoryRepository.save(productInventory);

        createStockLog(productInventory);

        return productInventory;
    }

    private void createStockLog(ProductInventory productInventory) {
        StockLog stockLog = new StockLog();

        stockLog.setDate(new Date());
        stockLog.setAddedQuantity(productInventory.getQuantity());
        stockLog.setProductInventory(productInventory);

        stockLogService.create(stockLog);
    }

    private void updateProductInventoryDetails(ProductInventory productInventory) {
        ProductInventory storedProductInventory = findById(productInventory.getId());

        productInventory.setQuantity(storedProductInventory.getQuantity() + productInventory.getQuantity());
        productInventory.setPrice(storedProductInventory.getPrice());
        productInventory.setProduct(storedProductInventory.getProduct());
    }

    @Override
    public ProductInventory findByProductId(long productId) {
        Map<Long,ProductInventory> mappedProductInventoryList = ProductInventoryUtil.getMappedProductInventory(findAll());
        ProductInventory productInventory = mappedProductInventoryList.get(productId);

        return  productInventory;

    }

    @Override
    public void saveAll(Iterable<ProductInventory> productInventories) {
        productInventoryRepository.saveAll(productInventories);
    }

}
