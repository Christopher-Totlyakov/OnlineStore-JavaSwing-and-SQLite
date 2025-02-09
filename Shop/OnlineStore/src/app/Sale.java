package app;

import java.util.ArrayList;

public class Sale {
    private int saleID;
    private int customerID;
    private int productID;
    private int quantity;
    private String saleDate;

    public Sale(int saleID, int customerID, int productID, int quantitySold, String saleDate) {
        this.saleID = saleID;
        this.customerID = customerID;
        this.productID = productID;
        this.quantity = quantitySold;
        this.saleDate = saleDate;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

     public int getCustomerID() {
        return customerID;
    }
     
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantitySold() {
        return quantity;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantity = quantitySold;
    }

   

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public static ArrayList<Sale> loadSales(Connect query) {
        ArrayList<Sale> sales = new ArrayList<>();

        String sql = "SELECT saleID, customerID, productID, quantity, saleDate FROM sales";

        try {
            query.stmt = query.conn.createStatement();
            query.rs = query.stmt.executeQuery(sql);

            while (query.rs.next()) {
                int id = query.rs.getInt("saleID");
                int customerID = query.rs.getInt("customerID");
                int productID = query.rs.getInt("productID");
                int quantity = query.rs.getInt("quantity");
                String saleDate = query.rs.getString("saleDate");

                sales.add(new Sale(id,customerID, productID, quantity, saleDate));
            }
        } catch (Exception e) {
            System.out.println("Error loading sales: " + e.getMessage());
            System.out.println(sql);
        }

        return sales;
    }
}
