package com.jbnsoft.inventory.sevice.stock;

import com.jbnsoft.inventory.repository.stock.StockLog;

import java.util.List;

public interface IStockLogService {

    StockLog create(StockLog stockLog) throws Exception;

    StockLog findById(Long id);
    List<StockLog> findAll();



}
