package com.jbnsoft.inventory.repository.customer;

import com.jbnsoft.inventory.repository.product.ProductOrder;

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

    private double tenderedChange;


    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_invoice_id")
    private List<ProductOrder> productOrderList;
    public CustomerInvoice() {

    }
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

    public double getAmountDue() {
        return amountDue;
    }

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

    public double getTenderedChange() {
        return tenderedChange;
    }

    public void setTenderedChange(double tenderedChange) {
        this.tenderedChange = tenderedChange;
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
