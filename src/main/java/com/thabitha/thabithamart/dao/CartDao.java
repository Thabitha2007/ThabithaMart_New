package com.thabitha.thabithamart.dao;

import com.thabitha.thabithamart.model.CartItem;
import com.thabitha.thabithamart.model.Product;
import com.thabitha.thabithamart.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    /** Adds 1 of the product in this size. If it is already in the cart, quantity goes up (never above stock). */
    public void add(long userId, long productId, String size) throws SQLException {
        try (Connection c = DB.get()) {
            int updated;
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE cart_items SET quantity = LEAST(quantity + 1, "
                  + "(SELECT stock FROM products WHERE id = cart_items.product_id)) "
                  + "WHERE user_id = ? AND product_id = ? AND size = ?")) {
                ps.setLong(1, userId);
                ps.setLong(2, productId);
                ps.setString(3, size);
                updated = ps.executeUpdate();
            }
            if (updated == 0) {
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO cart_items (user_id, product_id, size, quantity) "
                      + "SELECT ?, id, ?, 1 FROM products WHERE id = ? AND stock > 0")) {
                    ps.setLong(1, userId);
                    ps.setString(2, size);
                    ps.setLong(3, productId);
                    ps.executeUpdate();
                }
            }
        }
    }

    public void updateQuantity(long userId, long cartItemId, int quantity) throws SQLException {
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                "UPDATE cart_items SET quantity = LEAST(?, "
              + "(SELECT stock FROM products WHERE id = cart_items.product_id)) "
              + "WHERE id = ? AND user_id = ?")) {
            ps.setInt(1, quantity);
            ps.setLong(2, cartItemId);
            ps.setLong(3, userId);
            ps.executeUpdate();
        }
    }

    public void remove(long userId, long cartItemId) throws SQLException {
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                "DELETE FROM cart_items WHERE id = ? AND user_id = ?")) {
            ps.setLong(1, cartItemId);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }

    public List<CartItem> findByUser(long userId) throws SQLException {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT ci.id, ci.size, ci.quantity, "
                   + "p.id AS pid, p.name, p.price, p.image_url, p.category, p.stock, p.material, p.color "
                   + "FROM cart_items ci JOIN products p ON p.id = ci.product_id "
                   + "WHERE ci.user_id = ? ORDER BY ci.created_at DESC, ci.id DESC";
        try (Connection c = DB.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.id = rs.getLong("pid");
                    p.name = rs.getString("name");
                    p.price = rs.getBigDecimal("price");
                    p.imageUrl = rs.getString("image_url");
                    p.category = rs.getString("category");
                    p.stock = rs.getInt("stock");
                    p.material = rs.getString("material");
                    p.color = rs.getString("color");
                    p.active = true;

                    CartItem item = new CartItem();
                    item.id = rs.getLong("id");
                    item.size = rs.getString("size");
                    item.quantity = rs.getInt("quantity");
                    item.product = p;
                    items.add(item);
                }
            }
        }
        return items;
    }
}