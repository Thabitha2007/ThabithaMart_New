<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${categoryName}'s Wear - ThabithaMart</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f6f3eb;
            margin: 0;
            padding: 30px 48px;
        }
        .header-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #eae5d9;
            padding-bottom: 16px;
            margin-bottom: 24px;
        }
        .back-btn {
            text-decoration: none;
            color: #222;
            font-weight: 600;
            background: #eae5d9;
            padding: 8px 14px;
            border-radius: 4px;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
        }
        .product-card {
            background: #ffffff;
            border-radius: 8px;
            padding: 16px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 240px;
        }
        .product-title {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            margin-bottom: 8px;
        }
        .product-desc {
            font-size: 13px;
            color: #666;
            margin-bottom: 12px;
            flex-grow: 1;
        }
        .product-price {
            font-size: 18px;
            font-weight: bold;
            color: #28a745;
            margin-bottom: 12px;
        }
        .cart-btn {
            background-color: #111;
            color: white;
            border: none;
            padding: 9px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
        }
        .cart-btn:hover {
            background-color: #333;
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
        %>
            <div class="product-card">
                <div class="product-title"><%= p.get("name") %></div>
                <div class="product-desc"><%= p.get("description") %></div>
                <div class="product-price">₹ <%= String.format("%.2f", p.get("price")) %></div>
                <button class="cart-btn">Add to Cart</button>
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