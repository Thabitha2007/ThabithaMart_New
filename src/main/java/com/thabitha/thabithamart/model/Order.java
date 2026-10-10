package com.thabitha.thabithamart.model;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public long id, buyerId;
    public String status;
    public BigDecimal totalAmount;
    public Timestamp createdAt;
    public List<OrderItem> items = new ArrayList<>();
    public String shipName, shipAddress, shipMobile;
}
