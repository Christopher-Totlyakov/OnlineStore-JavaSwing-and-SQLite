package app;
import java.sql.*;

import java.util.ArrayList;

public class Product {
    private int productID;
    private String productName;
    private int inStock;
    private double price;
    private String description;
    private String imagePath;

    public Product(int productID, String productName, int inStock, double price, String description, String imagePath) {
        this.productID = productID;
        this.productName = productName;
        this.inStock = inStock;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static ArrayList<Product> loadProducts(Connect query, int page, int productsPerPage) {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT productID, productName, Stock, price, imagePath, description FROM products " +
                     "LIMIT " + productsPerPage + " OFFSET " + ((page - 1) * productsPerPage);
      try {
            query.stmt = query.conn.createStatement();
            query.rs = query.stmt.executeQuery(sql);

            while (query.rs.next()) {
                int id = query.rs.getInt("productID");
                String name = query.rs.getString("productName");
                int stock = query.rs.getInt("Stock");
                double price = query.rs.getDouble("price");
                String imagePath = query.rs.getString("imagePath");
                String description = query.rs.getString("description");

                products.add(new Product(id, name, stock, price, description, imagePath));
            }
             } catch (Exception e) {
                  System.out.println(sql);
        }
       
        return products;
    }
    
     public static ArrayList<Product> loadProducts(Connect query) {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT productID, productName, Stock, price, imagePath, description FROM products";
      try {
            query.stmt = query.conn.createStatement();
            query.rs = query.stmt.executeQuery(sql);

            while (query.rs.next()) {
                int id = query.rs.getInt("productID");
                String name = query.rs.getString("productName");
                int stock = query.rs.getInt("Stock");
                double price = query.rs.getDouble("price");
                String imagePath = query.rs.getString("imagePath");
                String description = query.rs.getString("description");

                products.add(new Product(id, name, stock, price, description, imagePath));
            }
             } catch (Exception e) {
                  System.out.println(sql);
        }
       
        return products;
    }
     public static ArrayList<Product> loadProducts(Connect query, String searchTerm) {
    ArrayList<Product> products = new ArrayList<>();
    
    String sql = "SELECT productID, productName, Stock, price, imagePath, description FROM products" +
                 (searchTerm != null && !searchTerm.isEmpty() ? " WHERE productName LIKE '%" + searchTerm + "%'" : "");

    try {
        query.stmt = query.conn.createStatement();
        query.rs = query.stmt.executeQuery(sql);

        while (query.rs.next()) {
            int id = query.rs.getInt("productID");
            String name = query.rs.getString("productName");
            int stock = query.rs.getInt("Stock");
            double price = query.rs.getDouble("price");
            String imagePath = query.rs.getString("imagePath");
            String description = query.rs.getString("description");

            products.add(new Product(id, name, stock, price, description, imagePath));
        }
    } catch (Exception e) {
        System.out.println("SQL error: " + e.getMessage());
    }

    return products;
}

    public static void addProduct(Connect conn, String productName,int Stock, double price, String description,String imagePath) {
        String[] columns = {"productName", "Stock", "price", "description", "imagePath"};
        String[] values = {
            productName,
            Stock+"",
            price+"",
            description,
            imagePath
        };
        conn.insert("products", columns, values);
    }

   public static void updateProduct(Connect conn, Product product) {
    String sql = "UPDATE products SET productName = ?, Stock = ?, price = ?, description = ?, imagePath = ? WHERE productID = ?";

    try {
        PreparedStatement ps = conn.conn.prepareStatement(sql);
        ps.setString(1, product.getProductName());
        ps.setInt(2, product.getInStock());
        ps.setDouble(3, product.getPrice());
        ps.setString(4, product.getDescription());
        ps.setString(5, product.getImagePath());
        ps.setInt(6, product.getProductID());

        int affectedRows = ps.executeUpdate();
        
        if (affectedRows == 0) {
            System.out.println("No rows updated.");
        } else {
            System.out.println("Product updated successfully.");
        }
    } catch (Exception e) {
        System.out.println("Error while updating product: " + e.getMessage());
    }
}
    public static void deleteProduct(Connect conn, int productID) {
        conn.delete("products", "productID", String.valueOf(productID));
    }
}
