package com.thabitha.thabithamart.model;
import java.math.BigDecimal;
public class CartItem { public Product product; public int quantity; public BigDecimal subtotal(){return product.price.multiply(BigDecimal.valueOf(quantity));} }