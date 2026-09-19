package com.thabitha.thabithamart.dao;

import com.thabitha.thabithamart.model.User;
import com.thabitha.thabithamart.util.DB;
import java.sql.*;

public class UserDao {

    public User find(String u) throws SQLException {
        String q = "SELECT * FROM users WHERE username = ?";
        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(q)) {
            p.setString(1, u);
            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) return null;
                User x = new User();
                x.id = r.getLong("id");
                x.username = r.getString("username");
                x.passwordHash = r.getString("password_hash");
                x.email = r.getString("email");
                x.role = r.getString("role");
                return x;
            }
        }
    }

    public void create(String u, String h, String role) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, role) VALUES(?, ?, ?)";
        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u);
            p.setString(2, h);
            p.setString(3, role);
            p.executeUpdate();
        }
    }

    // Email-உடன் சேர்த்துச் சேமிக்க விரும்பினால் பயன்படுத்தும் ஓவர்லோடட் முறை
    public void create(String u, String h, String email, String role) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, email, role) VALUES(?, ?, ?, ?)";
        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u);
            p.setString(2, h);
            p.setString(3, email);
            p.setString(4, role);
            p.executeUpdate();
        }
    }
}