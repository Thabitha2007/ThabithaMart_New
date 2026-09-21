<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.thabitha.thabithamart.model.User" %>
<%
    // Session-ல் இருந்து லாகின் செய்த பயனர் தகவல்களைப் பெறுதல்
    User currentUser = (User) session.getAttribute("user");
    String role = (String) session.getAttribute("role");
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ThabithaMart - Home</title>
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
        }

        /* Top Navigation Bar */
        .navbar {
            background-color: #111111;
            padding: 16px 48px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar .brand {
            font-size: 19px;
            font-weight: bold;
            color: #ffffff;
            letter-spacing: 0.5px;
        }
        .navbar .nav-items {
            display: flex;
            align-items: center;
        }
        .navbar .nav-items a {
            color: #cfcfcf;
            text-decoration: none;
            margin-left: 24px;
            font-size: 14px;
            transition: color 0.2s;
        }
        .navbar .nav-items a:hover {
            color: #ffffff;
        }

        /* User Profile & Role Badges */
        .user-greeting {
            color: #e0e0e0;
            font-size: 14px;
            margin-left: 20px;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .role-tag {
            font-size: 11px;
            font-weight: bold;
            padding: 3px 8px;
            border-radius: 12px;
            text-transform: uppercase;
        }
        .role-BUYER { background-color: #17a2b8; color: #fff; }
        .role-SELLER { background-color: #28a745; color: #fff; }
        .role-ADMIN { background-color: #dc3545; color: #fff; }

        /* Hero Text Area */
        .hero {
            padding: 80px 48px 40px 48px;
        }
        .hero h1 {
            font-size: 58px;
            font-weight: 800;
            line-height: 1.1;
            letter-spacing: -1px;
            margin-bottom: 20px;
        }
        .hero p {
            font-size: 15px;
            color: #666666;
            margin-bottom: 24px;
        }
        .explore-btn {
            display: inline-block;
            background-color: #222222;
            color: #ffffff;
            padding: 12px 22px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }
        .explore-btn:hover {
            background-color: #383838;
        }

        /* Large Category Cards Section */
        .categories-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            padding: 20px 48px 80px 48px;
        }
        .category-item {
            text-decoration: none;
            color: inherit;
            display: block;
        }
        .category-box {
            background-color: #eae5d9;
            border-radius: 8px;
            height: 180px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            font-weight: 600;
            color: #333333;
            transition: background-color 0.2s ease, transform 0.2s ease;
            box-shadow: 0 2px 5px rgba(0,0,0,0.03);
            text-align: center;
        }
        .category-box:hover {
            background-color: #dfd9cb;
            transform: translateY(-4px);
        }

        /* Responsive for mobile */
        @media (max-width: 900px) {
            .categories-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }
    </style>
</head>
<body>

    <!-- Header Navbar -->
    <div class="navbar">
        <div class="brand">ThabithaMart</div>
        <div class="nav-items">
            <a href="${pageContext.request.contextPath}/home">Browse</a>

            <%-- Seller அல்லது Admin லாகின் செய்திருந்தால் மட்டும் தெரியும் ஆப்ஷன்கள் --%>
           <% if ("SELLER".equals(role) || "ADMIN".equals(role)) { %>
                <a href="${pageContext.request.contextPath}/seller/add-product" style="color: #28a745; font-weight: 600;">+ Add Product</a>
            <% } %>
            <%-- Admin-க்கு மட்டும் பிரத்யேக ஆப்ஷன் --%>
            <% if ("ADMIN".equals(role)) { %>
                <a href="${pageContext.request.contextPath}/admin/dashboard" style="color: #ffc107; font-weight: 600;">Admin Panel</a>
            <% } %>

            <a href="${pageContext.request.contextPath}/cart">Cart</a>

            <%-- பயனர் லாகின் செய்துள்ளாரா என்பதைச் சரிபார்த்தல் --%>
            <% if (currentUser != null || username != null) { %>
                <span class="user-greeting">
                    <span>Hi, <strong><%= (username != null) ? username : currentUser.username %></strong></span>
                    <span class="role-tag role-<%= role %>"><%= role %></span>
                </span>
                <a href="${pageContext.request.contextPath}/logout" style="color: #ff6b6b;">Logout</a>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/login" style="font-weight: 600; color: #ffffff;">Login / Register</a>
            <% } %>
        </div>
    </div>

    <!-- Hero Section -->
    <div class="hero">
        <h1>Wear your<br>confidence.</h1>
        <p>Discover fashion from independent sellers.</p>
        <a href="${pageContext.request.contextPath}/products?category=Men" class="explore-btn">Explore collection</a>
    </div>

    <!-- Large Category Blocks -->
    <div class="categories-grid">
        <a href="${pageContext.request.contextPath}/products?category=Men" class="category-item">
            <div class="category-box">
                Men's Wear
            </div>
        </a>

        <a href="${pageContext.request.contextPath}/products?category=Women" class="category-item">
            <div class="category-box">
                Women's Wear
            </div>
        </a>

        <a href="${pageContext.request.contextPath}/products?category=Footwear" class="category-item">
            <div class="category-box">
                Footwear
            </div>
        </a>

        <a href="${pageContext.request.contextPath}/products?category=Accessories" class="category-item">
            <div class="category-box">
                Accessories
            </div>
        </a>
    </div>

</body>
</html>