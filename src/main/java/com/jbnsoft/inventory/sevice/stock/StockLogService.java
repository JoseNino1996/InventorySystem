package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.stock.ProductInventory;
import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.repository.stock.StockLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StockLogService implements  IStockLogService {
    @Autowired
    private StockLogRepository stockLogRepository;
    @Autowired
    private ProductInventoryService productInventoryService;


    @Override
    public StockLog create(StockLog stockLog)  {
        ProductInventory productInventory = stockLog.getProductInventory();
        ProductInventory storedProductInventory = productInventoryService.findById(productInventory.getId());
        storedProductInventory.setQuantity(stockLog.getAddedQuantity() + storedProductInventory.getQuantity());
        productInventoryService.update(storedProductInventory, productInventory.getId());
        return stockLogRepository.save(stockLog);
    }

    @Override
    public void delete(Long id) {
        stockLogRepository.deleteById(id);
    }

    @Override
    public StockLog update(StockLog stockLog, Long id) {
        StockLog storedStockLog = findById(id);
        stockLog.setId(storedStockLog.getId());
        return stockLogRepository.save(stockLog);
    }

    @Override
    public StockLog findById(Long id) {
        return stockLogRepository.findById(id).orElse(null);
    }

    @Override
    public List<StockLog> getStocklogs() {
        List<StockLog> stockLogList = new ArrayList<>();
        for(StockLog stockLog : stockLogRepository.findAll()) {
            stockLogList.add(stockLog);
        }
        return stockLogList;
    }


}
