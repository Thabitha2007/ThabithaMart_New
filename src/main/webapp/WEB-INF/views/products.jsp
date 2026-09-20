<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("categoryName") %> - ThabithaMart</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }

        .navbar {
            background: #1a1a2e;
            padding: 16px 32px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar .brand { color: #fff; font-size: 22px; font-weight: 700; }
        .navbar .links a {
            color: #ddd;
            text-decoration: none;
            margin-left: 22px;
            font-size: 15px;
        }
        .navbar .links a:hover, .navbar .links a.active { color: #ffcb45; }

        .page-header {
            text-align: center;
            padding: 28px 16px 8px;
        }
        .page-header h1 { font-size: 26px; color: #1a1a2e; }
        .page-header p { color: #777; margin-top: 6px; }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
            gap: 24px;
            padding: 24px 32px 48px;
            max-width: 1200px;
            margin: 0 auto;
        }

        .product-card {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            display: flex;
            flex-direction: column;
        }
        .product-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.14);
        }

        .product-card img { width: 100%; height: 220px; object-fit: cover; }

        .product-info {
            padding: 16px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }
        .product-info h3 { font-size: 17px; margin-bottom: 6px; color: #1a1a2e; }
        .product-info p.desc {
            font-size: 13px;
            color: #777;
            flex-grow: 1;
            margin-bottom: 12px;
            line-height: 1.4;
        }
        .price-row { display: flex; justify-content: space-between; align-items: center; }
        .price { font-size: 18px; font-weight: 700; color: #d6336c; }

        .add-btn {
            background: #1a1a2e;
            color: #fff;
            border: none;
            padding: 8px 14px;
            border-radius: 6px;
            font-size: 13px;
            cursor: pointer;
        }
        .add-btn:hover { background: #ffcb45; color: #1a1a2e; }

        .empty-state { text-align: center; padding: 60px 20px; color: #999; font-size: 16px; }
    </style>
</head>
<body>

<%
    String categoryName = (String) request.getAttribute("categoryName");
    List<Map<String, Object>> products = (List<Map<String, Object>>) request.getAttribute("products");
    NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);
%>

    <div class="navbar">
        <div class="brand">ThabithaMart</div>
        <div class="links">
            <a href="products?category=Men"        class="<%= "Men".equals(categoryName) ? "active" : "" %>">Men</a>
            <a href="products?category=Women"       class="<%= "Women".equals(categoryName) ? "active" : "" %>">Women</a>
            <a href="products?category=Footwear"    class="<%= "Footwear".equals(categoryName) ? "active" : "" %>">Footwear</a>
            <a href="products?category=Accessories" class="<%= "Accessories".equals(categoryName) ? "active" : "" %>">Accessories</a>
        </div>
    </div>

    <div class="page-header">
        <h1><%= categoryName %> Collection</h1>
        <p>Handpicked products just for you</p>
    </div>

    <% if (products == null || products.isEmpty()) { %>
        <div class="empty-state">No products found in this category.</div>
    <% } else { %>
        <div class="product-grid">
            <% for (Map<String, Object> p : products) { %>
                <div class="product-card">
                    <img src="<%= p.get("imageUrl") %>" alt="<%= p.get("name") %>">
                    <div class="product-info">
                        <h3><%= p.get("name") %></h3>
                        <p class="desc"><%= p.get("description") %></p>
                        <div class="price-row">
                            <span class="price">&#8377;<%= nf.format(p.get("price")) %></span>
                            <button class="add-btn">Add to Cart</button>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>

</body>
</html>