<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        .navbar .nav-items a {
            color: #cfcfcf;
            text-decoration: none;
            margin-left: 28px;
            font-size: 14px;
            transition: color 0.2s;
        }
        .navbar .nav-items a:hover {
            color: #ffffff;
        }

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
            height: 180px; /* நீங்கள் கேட்ட பெரிய சைஸ் */
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
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
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