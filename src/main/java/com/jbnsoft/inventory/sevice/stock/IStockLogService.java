package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.stock.StockLog;

import java.util.List;

public interface IStockLogService {

    StockLog create(StockLog stockLog) throws Exception;
    void delete(Long id);
    StockLog update(StockLog stockLog, Long id);
    StockLog findById(Long id);
    List<StockLog> getStocklogs();

}
