package com.thabitha.thabithamart.model;

import java.math.BigDecimal;

public class Product {
    public long id, sellerId;
    public String name, description, category, imageUrl;
    public BigDecimal price;
    public int stock;
    public boolean active;

    // Specifications shown on the cart page (loaded from the products table)
    public String material, color;
}