package com.thabitha.thabithamart.dao;

import com.thabitha.thabithamart.model.Product;
import com.thabitha.thabithamart.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Returns only the products that belong to the given seller
    public List<Product> listBySeller(long sellerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE seller_id = ? ORDER BY id DESC";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // Returns one product, but only if it belongs to this seller
    // (prevents a seller from editing another seller's product)
    public Product getByIdAndSeller(long productId, long sellerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ? AND seller_id = ?";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            ps.setLong(2, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Returns one product by id, regardless of seller (used by cart and buy now)
    public Product getById(long productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public void add(Product p) throws SQLException {
        String sql = "INSERT INTO products (seller_id, name, description, price, stock, category, image_url, material, color) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, p.sellerId);
            ps.setString(2, p.name);
            ps.setString(3, p.description);
            ps.setBigDecimal(4, p.price);
            ps.setInt(5, p.stock);
            ps.setString(6, p.category);
            ps.setString(7, p.imageUrl);
            ps.setString(8, p.material);
            ps.setString(9, p.color);
            ps.executeUpdate();
        }
    }

    // seller_id is part of the WHERE clause, so only the seller's own product gets updated
    public boolean update(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category=?, image_url=?, material=?, color=? " +
                     "WHERE id=? AND seller_id=?";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.name);
            ps.setString(2, p.description);
            ps.setBigDecimal(3, p.price);
            ps.setInt(4, p.stock);
            ps.setString(5, p.category);
            ps.setString(6, p.imageUrl);
            ps.setString(7, p.material);
            ps.setString(8, p.color);
            ps.setLong(9, p.id);
            ps.setLong(10, p.sellerId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(long productId, long sellerId) throws SQLException {
        String sql = "DELETE FROM products WHERE id=? AND seller_id=?";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            ps.setLong(2, sellerId);
            return ps.executeUpdate() > 0;
        }
    }

    // Converts one database row into a Product object
    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.id = rs.getLong("id");
        p.sellerId = rs.getLong("seller_id");
        p.name = rs.getString("name");
        p.description = rs.getString("description");
        p.price = rs.getBigDecimal("price");
        p.stock = rs.getInt("stock");
        p.category = rs.getString("category");
        p.imageUrl = rs.getString("image_url");
        p.material = rs.getString("material");
        p.color = rs.getString("color");
        return p;
    }

    /**
     * Buyer-side search across the products of all sellers.
     * If category or keyword is empty, that filter is skipped.
     */
    public List<Product> search(String category, String keyword) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All")) {
            sql.append(" AND LOWER(category) = LOWER(?)");
            params.add(category.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (LOWER(name) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?))");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        sql.append(" ORDER BY id DESC");

        List<Product> list = new ArrayList<>();
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }
}