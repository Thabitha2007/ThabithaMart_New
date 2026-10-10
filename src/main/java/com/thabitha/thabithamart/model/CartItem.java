package com.thabitha.thabithamart.model;
import java.math.BigDecimal;
public class CartItem {
    public long id;          // NEW: cart_items row id
    public String size;      // NEW
    public Product product;
    public int quantity;
    public BigDecimal subtotal(){return product.price.multiply(BigDecimal.valueOf(quantity));}
}