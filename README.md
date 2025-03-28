# Online Store

## 📌 Project Overview
This project is a **Java-based online store** designed to manage products, shopping carts, and user accounts efficiently. It provides a user-friendly interface for customers, administrators, and warehouse staff to interact with products and orders.

## 📖 Table of Contents
- [📌 Project Overview](#-project-overview)
- [🎯 Key Features](#-key-features)
  - [🛒 Product Management](#-product-management)
  - [🛍️ Shopping Cart](#-shopping-cart)
  - [👥 User Management](#-user-management)
  - [🗄️ Database Integration](#-database-integration)
- [🚀 Proposed Solution](#-proposed-solution)
- [🏗️ System Functionality](#-system-functionality)
  - [📦 Product Management](#-product-management-1)
  - [🛍️ Shopping Cart Features](#-shopping-cart-features)
  - [🔐 User Roles & Permissions](#-user-roles--permissions)
  - [🎨 User Interface & Data Visualization](#-user-interface--data-visualization)
- [🛠️ Technologies Used](#-technologies-used)
- 
## 🎯 Key Features
![image](https://github.com/user-attachments/assets/f1d163ba-b72a-4a7e-bcb8-d5d875c61109)


### 🛒 Product Management
- Add, update, and delete products in the database.
- Retrieve product details with filtering and pagination.

### 🛍️ Shopping Cart
- Add, remove, and modify products in the cart.
- Calculate total price dynamically.
- Create and manage orders.

### 👥 User Management
- User registration and authentication.
- Role-based access (Admin, Customer, Warehouse Staff).
- Secure password storage.

### 🗄️ Database Integration
- CRUD operations (Create, Read, Update, Delete).
- SQL abstraction for efficient data handling.

## 🚀 Proposed Solution
Our system provides a **modular and scalable** approach to warehouse and order management:

1. **Flexible Data Model**  
   - Structured entities like `Product`, `User`, and `ShoppingCart` for easy data manipulation.

2. **Interactive User Interface**  
   - A table-based interface for viewing, editing, and removing products.

3. **Scalability**  
   - Easily extendable to support features like reports, payment processing, and third-party logistics.

4. **Security & Flexibility**  
   - Centralized user management with customizable roles.

## 🏗️ System Functionality

### 📦 Product Management
- **Add new products** with details like name, description, price, and stock availability.
- **Update existing products** (price changes, stock updates).
- **Delete products** when out of stock or obsolete.

### 🛍️ Shopping Cart Features
- **Add products to cart** and adjust quantities.
- **Remove products** from the cart.
- **Real-time price calculation**.
- **Order creation** from selected cart items.

### 🔐 User Roles & Permissions
- **Customer:** Browse products, add to cart, and place orders.
- **Warehouse Staff:** Manage stock availability and track orders.
- **Administrator:** Full control over products, users, and orders.

### 🎨 User Interface & Data Visualization
- **Product lists** displayed in an interactive table.
- **Shopping cart and order tables** with real-time updates.
- **Pagination and filtering** for improved user experience.

## 🛠️ Technologies Used
- **Programming Language:** Java  
- **Database:** SQLite  
- **GUI Framework:** Swing 
- **Security:** Role-based authentication  

## 📂 Project Structure
