<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${categoryName}'s Wear - ThabithaMart</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f6f3eb;
            color: #2b2b2b;
            padding: 30px 48px;
        }
        .header-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #eae5d9;
            padding-bottom: 16px;
            margin-bottom: 28px;
        }
        .header-bar h2 {
            font-size: 26px;
            font-weight: 700;
        }
        .back-btn {
            text-decoration: none;
            color: #222;
            font-weight: 600;
            background: #eae5d9;
            padding: 9px 16px;
            border-radius: 4px;
            font-size: 14px;
            transition: background 0.2s;
        }
        .back-btn:hover {
            background: #dfd9cb;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
            gap: 24px;
        }
        .product-card {
            background: #ffffff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .product-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 14px rgba(0,0,0,0.08);
        }
        .product-img {
            width: 100%;
            height: 220px;
            object-fit: cover;
            background-color: #f0f0f0;
            display: block;
        }
        .product-info {
            padding: 16px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }
        .product-title {
            font-size: 16px;
            font-weight: bold;
            color: #222;
            margin-bottom: 6px;
        }
        .product-desc {
            font-size: 13px;
            color: #666;
            margin-bottom: 12px;
            flex-grow: 1;
            line-height: 1.4;
        }
        .product-price {
            font-size: 18px;
            font-weight: bold;
            color: #28a745;
            margin-bottom: 14px;
        }
        .cart-btn {
            background-color: #111111;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            width: 100%;
            transition: background 0.2s;
        }
        .cart-btn:hover {
            background-color: #333333;
        }
    </style>
</head>
<body>

    <div class="header-bar">
        <h2>${categoryName}'s Wear</h2>
        <a class="back-btn" href="${pageContext.request.contextPath}/home">← Back to Home</a>
    </div>

    <div class="product-grid">
        <%
            List<Map<String, Object>> products = (List<Map<String, Object>>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
                for (Map<String, Object> p : products) {
                    String img = (String) p.get("imageUrl");
                    
                   
                    if (img == null || img.trim().isEmpty() || img.contains("photo-1521572267360-ee0c2909d518")) {
                        img = "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=500&q=80";
                    }
        %>
            <div class="product-card">
                <img src="<%= img %>" alt="<%= p.get("name") %>" class="product-img">
                <div class="product-info">
                    <div class="product-title"><%= p.get("name") %></div>
                    <div class="product-desc"><%= p.get("description") %></div>
                    <div class="product-price">₹ <%= String.format("%.2f", p.get("price")) %></div>
                    <button class="cart-btn">Add to Cart</button>
                </div>
            </div>
        <%
                }
            } else {
        %>
            <p>No products available in this category yet.</p>
        <%
            }
        %>
    </div>

</body>
</html>