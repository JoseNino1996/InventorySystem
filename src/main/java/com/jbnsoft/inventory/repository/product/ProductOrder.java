package com.jbnsoft.inventory.repository.product;


import com.jbnsoft.inventory.repository.stock.ProductInventory;


import javax.persistence.*;

@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long orderedQty;


    @OneToOne( fetch = FetchType.EAGER)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(long orderedQty) {
        this.orderedQty = orderedQty;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", orderedQty=" + orderedQty +
                ", product=" + product +
                '}';
    }
}
