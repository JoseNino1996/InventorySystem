package com.jbnsoft.inventory.repository.customerinvoice;

import com.jbnsoft.inventory.repository.customer.Customer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class CustomerInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amountDue;
    private Date date;
    private double amountTendered;


    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_invoice_id")
    private List<ProductOrder> productOrderList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public Date getDate() {
        if(this.date == null ) {
            this.date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

//    public double getAmountDue() {
//        for(ProductOrder productOrder : getProductOrderList()) {
//            amountDue += productOrder.getPrice() * productOrder.getOrderedQty() ;
//        }
//
//        return amountDue;
//    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductOrder> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrder> productOrderList) {
        this.productOrderList = productOrderList;
    }


    public double getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(double amountTendered) {
        this.amountTendered = amountTendered;
    }



    @Override
    public String toString() {
        return "CustomerInvoice{" +
                "id=" + id +
                ", amountDue=" + amountDue +
                ", date=" + date +
                ", customer=" + customer +
                ", productOrderList=" + productOrderList +
                '}';
    }
}
