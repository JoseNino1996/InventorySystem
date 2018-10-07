package com.jbnsoft.inventory.resource.stock;

import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.stock.IStockLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-log")
public class StockLogResource {
    @Autowired
    private IStockLogService iStockLogService;

    @PostMapping("/create")
    public StockLog create(@RequestBody StockLog stockLog) throws Exception {
        return  iStockLogService.create(stockLog);
    }
    @GetMapping("/findById")
    public  StockLog findById(@RequestParam Long id)  {
        return  iStockLogService.findById(id);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id)  {
        iStockLogService.delete(id);
    }
}
