MERGE INTO users KEY(username) VALUES
(1,'admin','$2a$10$DowJonesExampleHashReplaceBeforeUse','ADMIN');
-- The application creates the reliable admin hash at startup if absent.

MERGE INTO products(name, category, price, stock, image_url, description)
KEY(name)
VALUES
('Classic White T-Shirt', 'Men', 499.00, 25, 'https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=500&q=80', '100% pure cotton breathable everyday wear'),
('Slim Fit Denim Jeans', 'Men', 1299.00, 18, 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=500&q=80', 'Stretchable comfortable blue denim jeans'),
('Casual Linen Shirt', 'Men', 899.00, 15, 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500&q=80', 'Full-sleeve formal and casual wear shirt'),
('Black Fleece Hoodie', 'Men', 1499.00, 12, 'https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=500&q=80', 'Warm fleece oversized winter wear hoodie'),
('Formal Party Blazer', 'Men', 3499.00, 8, 'https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=500&q=80', 'Premium slim-fit stylish blazer for events'),
('Sporty Track Pants', 'Men', 699.00, 20, 'https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=500&q=80', 'Quick-dry activewear track pants for gym & run'),
('Striped Polo T-Shirt', 'Men', 16, 749.00, 'https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=500&q=80', 'Collared premium polo shirt for weekend outings'),
('Leather Bomber Jacket', 'Men', 2899.00, 10, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&q=80', 'Classic vintage black faux leather jacket');

