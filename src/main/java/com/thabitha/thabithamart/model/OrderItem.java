package com.thabitha.thabithamart.model;
import java.math.BigDecimal;

public class OrderItem {
    public long productId;
    public String productName, size;
    public int quantity;
    public BigDecimal unitPrice;
    public BigDecimal subtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
