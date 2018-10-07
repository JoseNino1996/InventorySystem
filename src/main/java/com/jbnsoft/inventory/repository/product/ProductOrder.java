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
    private ProductInventory productInventory;

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

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", orderedQty=" + orderedQty +
                ", productInventory=" + productInventory +
                '}';
    }
}
