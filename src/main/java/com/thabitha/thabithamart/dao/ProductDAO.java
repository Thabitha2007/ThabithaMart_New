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

    // இந்த seller-ஓட products மட்டும் திரும்பும்
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

    // ஒரு product-ஐ, அது இந்த seller-க்கு சொந்தமா இருந்தா மட்டும் திருப்பும்
    // (வேற seller-ஓட product-ஐ edit பண்ண முடியாம தடுக்குறதுக்காக)
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

    public void add(Product p) throws SQLException {
        String sql = "INSERT INTO products (seller_id, name, description, price, stock, category, image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, p.sellerId);
            ps.setString(2, p.name);
            ps.setString(3, p.description);
            ps.setBigDecimal(4, p.price);
            ps.setInt(5, p.stock);
            ps.setString(6, p.category);
            ps.setString(7, p.imageUrl);
            ps.executeUpdate();
        }
    }

    // seller_id-யும் WHERE-ல வெச்சிருக்கோம் - சொந்த product மட்டும் update ஆகும்
    public boolean update(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category=?, image_url=? " +
                     "WHERE id=? AND seller_id=?";
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.name);
            ps.setString(2, p.description);
            ps.setBigDecimal(3, p.price);
            ps.setInt(4, p.stock);
            ps.setString(5, p.category);
            ps.setString(6, p.imageUrl);
            ps.setLong(7, p.id);
            ps.setLong(8, p.sellerId);
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
        return p;
    }
}
