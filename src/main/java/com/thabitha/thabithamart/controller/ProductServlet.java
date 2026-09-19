package com.thabitha.thabithamart.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.thabitha.thabithamart.util.DB;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "Men";
        }

        List<Map<String, Object>> productList = new ArrayList<>();

        // DB.getConnection()-க்கு பதில் DB.get() பயன்படுத்தப்பட்டுள்ளது
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE category = ?")) {

            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> prod = new HashMap<>();
                    prod.put("id", rs.getInt("id"));
                    prod.put("name", rs.getString("name"));
                    prod.put("description", rs.getString("description"));
                    prod.put("price", rs.getDouble("price"));
                    productList.add(prod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("categoryName", category);
        request.setAttribute("products", productList);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }
}
