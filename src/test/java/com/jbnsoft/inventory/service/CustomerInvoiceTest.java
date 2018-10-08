package com.jbnsoft.inventory.service;

import com.jbnsoft.inventory.repository.customer.CustomerInvoice;
import com.jbnsoft.inventory.sevice.customer.CustomerInvoiceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerInvoiceTest {

    private CustomerInvoiceService customerInvoiceService;

    @Before
    public  void setUp() {
        customerInvoiceService = new CustomerInvoiceService();

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
