package com.jbnsoft.inventory.sevice.stock;

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


    @Override
    public StockLog create(StockLog stockLog)  {

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
    public List<StockLog> findAll() {
        List<StockLog> stockLogList = new ArrayList<>();
        for(StockLog stockLog : stockLogRepository.findAll()) {
            stockLogList.add(stockLog);
        }
        return stockLogList;
    }


}
