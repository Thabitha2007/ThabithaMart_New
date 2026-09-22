<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; margin: 0; }
        .navbar { background: #1a1a2e; padding: 16px 32px; }
        .navbar a { color: #ffcb45; text-decoration: none; }
        .card { max-width: 480px; margin: 40px auto; background: #fff; padding: 28px 32px; border-radius: 10px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); }
        label { font-size: 13px; color: #555; display: block; margin-top: 14px; margin-bottom: 4px; }
        input, textarea, select { width: 100%; padding: 9px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; font-family: inherit; }
        button { width: 100%; margin-top: 22px; padding: 11px; background: #1a1a2e; color: #fff; border: none; border-radius: 6px; font-size: 15px; cursor: pointer; }
        .error { color: #d6336c; font-size: 13px; margin-top: 12px; }
    </style>
</head>
<body>
<div class="navbar">
    <a href="<%= request.getContextPath() %>/seller/products">&larr; Back to My Products</a>
    &nbsp;|&nbsp;
    <a href="<%= request.getContextPath() %>/home"> Home</a>
</div>
<div class="card">
    <h2>Add New Product</h2>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error"><%= request.getAttribute("errorMessage") %></div>
    <% } %>
    <form action="<%= request.getContextPath() %>/seller/add-product" method="post">
        <label>Product Name</label>
        <input type="text" name="name" required>
        <label>Description</label>
        <textarea name="description" required></textarea>
        <label>Price (&#8377;)</label>
        <input type="number" step="0.01" min="0" name="price" required>
        <label>Stock Quantity</label>
        <input type="number" min="0" name="stock" required>
        <label>Category</label>
        <select name="category" required>
            <option value="Men">Men</option>
            <option value="Women">Women</option>
            <option value="Footwear">Footwear</option>
            <option value="Accessories">Accessories</option>
        </select>
        <label>Image URL</label>
        <input type="text" name="imageUrl" placeholder="https://..." required>
        <button type="submit">Add Product</button>
    </form>
</div>
</body>
</html>