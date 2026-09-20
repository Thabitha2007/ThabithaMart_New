package com.thabitha.thabithamart.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "Men";
        }

        String cat = category.trim().toLowerCase();
        List<Map<String, Object>> productList = new ArrayList<>();

        // 1. Footwear
        if (cat.contains("foot") || cat.contains("shoe")) {
            productList.add(product(201, "Classic White Sneakers",
                    "Comfortable everyday casual white sneakers.", 1499.00,
                    "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(202, "Running Sports Shoes",
                    "Lightweight breathable shoes built for daily runs.", 1999.00,
                    "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(203, "Leather Formal Shoes",
                    "Genuine leather lace-up shoes for office and formal wear.", 2499.00,
                    "https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=600&auto=format&fit=crop&q=80"));
        }
        // 2. Women's Wear
        else if (cat.contains("women")) {
            productList.add(product(101, "Floral Summer Dress",
                    "Elegant lightweight floral print summer dress.", 1199.00,
                    "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(102, "Casual Kurti",
                    "Soft cotton kurti, perfect for daily wear.", 799.00,
                    "https://images.pexels.com/photos/11974169/pexels-photo-11974169.jpeg?w=600&auto=compress&cs=tinysrgb"));
            productList.add(product(103, "Women's Denim Jacket",
                    "Trendy blue denim jacket for a stylish layered look.", 1599.00,
                    "https://images.unsplash.com/photo-1544022613-e87ca75a784a?w=600&auto=format&fit=crop&q=80"));
        }
        // 3. Accessories  (previously missing — was falling into the Men's else block)
        else if (cat.contains("access")) {
            productList.add(product(301, "Leather Wrist Watch",
                    "Classic analog leather strap wrist watch.", 1799.00,
                    "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(302, "Sunglasses - UV Protected",
                    "Stylish UV protected unisex sunglasses.", 699.00,
                    "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(303, "Leather Wallet",
                    "Slim genuine leather bi-fold wallet.", 599.00,
                    "https://images.unsplash.com/photo-1627123424574-724758594e93?w=600&auto=format&fit=crop&q=80"));
        }
        // 4. Men's Wear (default)
        else {
            productList.add(product(1, "Classic Black T-Shirt",
                    "100% cotton crewneck black t-shirt.", 499.00,
                    "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(2, "Slim Fit Denim Jeans",
                    "Comfortable stretchable slim fit denim jeans.", 1299.00,
                    "https://images.unsplash.com/photo-1542272604-787c3835535d?w=600&auto=format&fit=crop&q=80"));
            productList.add(product(3, "Casual Linen Shirt",
                    "Breathable casual linen shirt for all-day comfort.", 899.00,
                    "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=80"));
        }

        request.setAttribute("categoryName", category);
        request.setAttribute("products", productList);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    private Map<String, Object> product(int id, String name, String description,
                                         double price, String imageUrl) {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id);
        p.put("name", name);
        p.put("description", description);
        p.put("price", price);
        p.put("imageUrl", imageUrl);
        return p;
    }
}