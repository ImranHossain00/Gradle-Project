package eshoppers.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CartItem cartItem = (CartItem) object;
        return Objects.equals(id, cartItem.id) && Objects.equals(product, cartItem.product) && Objects.equals(quantity, cartItem.quantity) && Objects.equals(price, cartItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, price);
    }
}