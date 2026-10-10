package com.thabitha.thabithamart.dao;

import com.thabitha.thabithamart.exception.OutOfStockException;
import com.thabitha.thabithamart.model.CartItem;
import com.thabitha.thabithamart.model.Order;
import com.thabitha.thabithamart.model.OrderItem;
import com.thabitha.thabithamart.util.DB;


import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import com.thabitha.thabithamart.model.ShippingInfo;

public class OrderDao {

    /** Places the order in one transaction. Returns the new order id. */
    public long placeOrder(long userId, List<CartItem> items, ShippingInfo ship) throws SQLException, OutOfStockException {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : items) {
            total = total.add(it.subtotal());
        }
        try (Connection c = DB.get()) {
            c.setAutoCommit(false);
            try {
                // 1. reduce stock (fails if someone else bought it first)
                for (CartItem it : items) {
                    try (PreparedStatement ps = c.prepareStatement(
                            "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?")) {
                        ps.setInt(1, it.quantity);
                        ps.setLong(2, it.product.id);
                        ps.setInt(3, it.quantity);
                        if (ps.executeUpdate() == 0) {
                            throw new OutOfStockException("Sorry, \"" + it.product.name
                                    + "\" is no longer available in that quantity. Please update your cart.");
                        }
                    }
                }
                // 2. create the order
                long orderId;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO orders (buyer_id, status, total_amount, ship_name, ship_address, ship_mobile) "
                      + "VALUES (?, 'PENDING', ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, userId);
                    ps.setBigDecimal(2, total);
                    ps.setString(3, ship.name);
                    ps.setString(4, ship.address);
                    ps.setString(5, ship.mobile);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        orderId = rs.getLong(1);
                    }
                }
                // 3. order items (name and price are saved as at purchase time)
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO order_items (order_id, product_id, product_name, size, quantity, unit_price) "
                      + "VALUES (?, ?, ?, ?, ?, ?)")) {
                    for (CartItem it : items) {
                        ps.setLong(1, orderId);
                        ps.setLong(2, it.product.id);
                        ps.setString(3, it.product.name);
                        ps.setString(4, it.size);
                        ps.setInt(5, it.quantity);
                        ps.setBigDecimal(6, it.product.price);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                // 4. empty the cart
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM cart_items WHERE user_id = ?")) {
                    ps.setLong(1, userId);
                    ps.executeUpdate();
                }
                c.commit();
                return orderId;
            } catch (SQLException | OutOfStockException | RuntimeException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    /** Returns the order only if it belongs to this buyer, otherwise null. */
    public Order findById(long orderId, long userId) throws SQLException {
        Order o = null;
        try (Connection c = DB.get()) {
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id, buyer_id, status, total_amount, created_at, ship_name, ship_address, ship_mobile FROM orders WHERE id = ? AND buyer_id = ?")) {
                ps.setLong(1, orderId);
                ps.setLong(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        o = new Order();
                        o.id = rs.getLong("id");
                        o.buyerId = rs.getLong("buyer_id");
                        o.status = rs.getString("status");
                        o.totalAmount = rs.getBigDecimal("total_amount");
                        o.createdAt = rs.getTimestamp("created_at");
                        o.shipName = rs.getString("ship_name");
                        o.shipAddress = rs.getString("ship_address");
                        o.shipMobile = rs.getString("ship_mobile");
                    }
                }
            }
            if (o != null) {
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT product_id, product_name, size, quantity, unit_price "
                      + "FROM order_items WHERE order_id = ? ORDER BY id")) {
                    ps.setLong(1, orderId);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            OrderItem it = new OrderItem();
                            it.productId = rs.getLong("product_id");
                            it.productName = rs.getString("product_name");
                            it.size = rs.getString("size");
                            it.quantity = rs.getInt("quantity");
                            it.unitPrice = rs.getBigDecimal("unit_price");
                            o.items.add(it);
                        }
                    }
                }
            }
        }
        return o;
    }
}
