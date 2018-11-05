package com.jbnsoft.inventory.resource.stock;

import com.jbnsoft.inventory.repository.stock.StockLog;
import com.jbnsoft.inventory.sevice.stock.IStockLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-log")
public class StockLogResource {
    @Autowired
    private IStockLogService iStockLogService;


    @GetMapping("/findById")
    public  StockLog findById(@RequestParam Long id)  {
        return  iStockLogService.findById(id);
    }

    @GetMapping("/findAll")
    public List<StockLog> findAll() {
        return iStockLogService.findAll();
    }

}
