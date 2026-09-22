<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.thabitha.thabithamart.model.Product, java.text.NumberFormat, java.util.Locale" %>
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
            flex-wrap: wrap;
            gap: 12px;
        }
        .navbar .brand { color: #fff; font-size: 22px; font-weight: 700; }

        .search-form {
            display: flex;
            gap: 8px;
            flex: 1;
            max-width: 420px;
            margin: 0 20px;
        }
        .search-form input[type="text"] {
            flex: 1;
            padding: 9px 14px;
            border: none;
            border-radius: 20px;
            font-size: 14px;
        }
        .search-form button {
            background: #ffcb45;
            color: #1a1a2e;
            border: none;
            padding: 9px 18px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
        }

        .links a {
            color: #ddd;
            text-decoration: none;
            margin-left: 16px;
            font-size: 14px;
            padding: 6px 12px;
            border-radius: 16px;
        }
        .links a:hover, .links a.active { background: #ffcb45; color: #1a1a2e; }

        .page-header { text-align: center; padding: 24px 16px 8px; }
        .page-header h1 { font-size: 24px; color: #1a1a2e; }
        .page-header p { color: #777; margin-top: 6px; font-size: 14px; }

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
        .product-card:hover { transform: translateY(-4px); box-shadow: 0 8px 20px rgba(0,0,0,0.14); }
        .product-card img { width: 100%; height: 220px; object-fit: cover; }

        .product-info { padding: 16px; display: flex; flex-direction: column; flex-grow: 1; }
        .product-info h3 { font-size: 17px; margin-bottom: 6px; color: #1a1a2e; }
        .product-info p.desc { font-size: 13px; color: #777; flex-grow: 1; margin-bottom: 12px; line-height: 1.4; }
        .price-row { display: flex; justify-content: space-between; align-items: center; }
        .price { font-size: 18px; font-weight: 700; color: #d6336c; }
        .add-btn { background: #1a1a2e; color: #fff; border: none; padding: 8px 14px; border-radius: 6px; font-size: 13px; cursor: pointer; }
        .add-btn:hover { background: #ffcb45; color: #1a1a2e; }

        .empty-state { text-align: center; padding: 60px 20px; color: #999; font-size: 16px; }
    </style>
</head>
<body>

<%
    String categoryName = (String) request.getAttribute("categoryName");
    String keyword = (String) request.getAttribute("keyword");
    List<Product> products = (List<Product>) request.getAttribute("products");
    NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);
%>

<div class="navbar">
    <a href="<%= request.getContextPath() %>/home" style="text-decoration:none;"><div class="brand">ThabithaMart</div></a>

    <form class="search-form" action="<%= request.getContextPath() %>/products" method="get">
        <input type="text" name="keyword" placeholder="Search products..."
               value="<%= keyword != null ? keyword : "" %>">
        <button type="submit">Search</button>
    </form>

    <div class="links">
        <a href="<%= request.getContextPath() %>/products" class="<%= "All".equals(categoryName) ? "active" : "" %>">All</a>
        <a href="<%= request.getContextPath() %>/products?category=Men" class="<%= "Men".equals(categoryName) ? "active" : "" %>">Men</a>
        <a href="<%= request.getContextPath() %>/products?category=Women" class="<%= "Women".equals(categoryName) ? "active" : "" %>">Women</a>
        <a href="<%= request.getContextPath() %>/products?category=Footwear" class="<%= "Footwear".equals(categoryName) ? "active" : "" %>">Footwear</a>
        <a href="<%= request.getContextPath() %>/products?category=Accessories" class="<%= "Accessories".equals(categoryName) ? "active" : "" %>">Accessories</a>
    </div>
</div>

<div class="page-header">
    <h1><%= categoryName %> Collection</h1>
    <% if (keyword != null && !keyword.trim().isEmpty()) { %>
        <p>Search results for "<%= keyword %>"</p>
    <% } else { %>
        <p>Handpicked products just for you</p>
    <% } %>
</div>

<% if (products == null || products.isEmpty()) { %>
    <div class="empty-state">No products found. Try a different category or keyword.</div>
<% } else { %>
    <div class="product-grid">
        <% for (Product p : products) { %>
            <div class="product-card">
                <img src="<%= p.imageUrl %>" alt="<%= p.name %>"
                     onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=No+Image';">
                <div class="product-info">
                    <h3><%= p.name %></h3>
                    <p class="desc"><%= p.description %></p>
                    <div class="price-row">
                        <span class="price">&#8377;<%= nf.format(p.price) %></span>
                        <button class="add-btn">Add to Cart</button>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
<% } %>

</body>
</html>