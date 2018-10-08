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

    private double payment;

    private double paymentChange;


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


    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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


    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getPaymentChange() {
        return paymentChange;
    }

    public void setPaymentChange(double paymentChange) {
        this.paymentChange = paymentChange;
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
