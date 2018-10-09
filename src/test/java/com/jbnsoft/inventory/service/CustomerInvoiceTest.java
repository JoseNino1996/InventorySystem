package com.jbnsoft.inventory.service;

import com.jbnsoft.inventory.repository.customer.CustomerInvoice;
import com.jbnsoft.inventory.repository.product.ProductOrder;
import com.jbnsoft.inventory.sevice.customer.CustomerInvoiceService;
import com.jbnsoft.inventory.sevice.stock.helper.CreateOrder;
import com.jbnsoft.inventory.sevice.stock.helper.ProcessOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomerInvoiceTest {

    private CustomerInvoiceService customerInvoiceService;

    private List<ProductOrder> productOrderList;

    private ProcessOrder processOrder;


    @Before
    public  void setUp() {
        customerInvoiceService = new CustomerInvoiceService();

        processOrder = new CreateOrder();

        productOrderList = new ArrayList<>();

    }



    @Test
    public void paymentMustGreaterOrEqualSubtotal()  {
        CustomerInvoice customerInvoice = new CustomerInvoice();

        customerInvoice.setAmountDue(1000);
        customerInvoice.setAmountTendered(1100);

        boolean result =   customerInvoiceService.checkTenderedAmount(customerInvoice);

        Assert.assertEquals(true,result);

    }

}
