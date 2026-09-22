<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.thabitha.thabithamart.model.Product, com.thabitha.thabithamart.model.User, java.text.NumberFormat, java.util.Locale" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Products - ThabithaMart Seller</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }
        .navbar { background: #1a1a2e; padding: 16px 32px; display: flex; justify-content: space-between; align-items: center; }
        .navbar .brand { color: #fff; font-size: 20px; font-weight: 700; }
        .navbar a { color: #ffcb45; text-decoration: none; font-size: 14px; margin-left: 16px; }
        .container { max-width: 1100px; margin: 0 auto; padding: 24px 20px; }
        .top-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .add-link { background: #1a1a2e; color: #fff; padding: 10px 18px; border-radius: 6px; text-decoration: none; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        th, td { padding: 12px 14px; text-align: left; font-size: 14px; border-bottom: 1px solid #eee; }
        th { background: #f0f0f5; }
        img.thumb { width: 50px; height: 50px; object-fit: cover; border-radius: 6px; }
        .price { color: #d6336c; font-weight: 700; }
        .actions a { margin-right: 10px; font-size: 13px; text-decoration: none; }
        .edit-link { color: #1a73e8; }
        .delete-link { color: #d6336c; }
        .empty { text-align: center; padding: 50px; color: #999; }
    </style>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    List<Product> products = (List<Product>) request.getAttribute("products");
    NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
    nf.setMinimumFractionDigits(2);
%>
<div class="navbar">
    <div class="brand">ThabithaMart Seller Panel</div>
    <div>
        <span style="color:#ddd;font-size:14px;">Hi, <%= user.username %></span>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="top-row">
        <h1>My Products</h1>
        <a class="add-link" href="<%= request.getContextPath() %>/seller/add-product">+ Add New Product</a>
    </div>
    <% if (products == null || products.isEmpty()) { %>
        <div class="empty">இன்னும் products எதுவும் add பண்ணல. "+ Add New Product" க்ளிக் பண்ணுங்க.</div>
    <% } else { %>
        <table>
            <tr><th>Image</th><th>Name</th><th>Category</th><th>Price</th><th>Stock</th><th>Actions</th></tr>
            <% for (Product p : products) { %>
                <tr>
                    <td><img class="thumb" src="<%= p.imageUrl %>" onerror="this.src='https://placehold.co/50x50?text=No+Img';"></td>
                    <td><%= p.name %></td>
                    <td><%= p.category %></td>
                    <td class="price">&#8377;<%= nf.format(p.price) %></td>
                    <td><%= p.stock %></td>
                    <td class="actions">
                        <a class="edit-link" href="<%= request.getContextPath() %>/seller/product/edit?id=<%= p.id %>">Edit</a>
                        <a class="delete-link" href="<%= request.getContextPath() %>/seller/product/delete?id=<%= p.id %>" onclick="return confirm('Delete this product?');">Delete</a>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>
</div>
</body>
</html>