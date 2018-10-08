package com.jbnsoft.inventory.service;

import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProcessOrderTest {

    private ProcessOrder processOrder;

    @Before
    public void setUp() {
        processOrder = new CreateOrder();
    }

    @Test
    public  void storedQuantityMustBeGreaterOrEqualThanOrderedQuantity(){
        boolean result =  processOrder.checkProductAvailability(4,14);

        Assert.assertEquals(true,result);
    }

}
