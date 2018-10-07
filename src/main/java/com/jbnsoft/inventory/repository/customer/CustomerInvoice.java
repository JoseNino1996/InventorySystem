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

    private double subtotal;
    private Date date;


    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_invoice_id")
    private List<ProductOrder> productOrderList;
    public CustomerInvoice() {
        date = new Date();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getSubtotal() {
        return subtotal;
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

    @Override
    public String toString() {
        return "CustomerInvoice{" +
                "id=" + id +
                ", subtotal=" + subtotal +
                ", date=" + date +
                ", customer=" + customer +
                ", productOrderList=" + productOrderList +
                '}';
    }
}
