/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class Admin extends javax.swing.JFrame {

    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField imagePathField;
    private JTextArea descriptionField;
    private Connect query = new Connect();
    User user;
    /**
     * Creates new form Admin
     */
     public Admin() {
        initComponents();
        customComponents();
    }
    public Admin(User user) {
        this.user = user;
        initComponents();
        customComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
private void customComponents() {
    setTitle("Admin Area");

    jPanel1.setLayout(new BorderLayout());

    tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Stock", "Price", "Description", "Image Path"}, 0);
    dataTable = new JTable(tableModel);

    JScrollPane tableScrollPane = new JScrollPane(dataTable);

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(tableScrollPane, BorderLayout.CENTER);

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

    JLabel searchLabel = new JLabel("Search Product:");
    searchField = new JTextField(15);
    JButton searchButton = new JButton("Find");
    searchButton.addActionListener(e -> searchProduct());


    JLabel nameLabel = new JLabel("Product Name:");
    nameField = new JTextField(15);

    JLabel priceLabel = new JLabel("Price:");
    priceField = new JTextField(15);

    JLabel quantityLabel = new JLabel("Quantity:");
    quantityField = new JTextField(15);

    JLabel imagePathLabel = new JLabel("Image Path:");
    imagePathField = new JTextField(15);

    JLabel descriptionLabel = new JLabel("Description:");
    descriptionField = new JTextArea(5, 15);
    descriptionField.setLineWrap(true);
    descriptionField.setWrapStyleWord(true);

    JButton saveButton = new JButton("Save Changes");
  saveButton.addActionListener(e -> {
      String productIdString = "";
    try {
         int selectedRow = dataTable.getSelectedRow();
    if (selectedRow != -1) {
         productIdString = tableModel.getValueAt(selectedRow, 0).toString();
       
    }
         int productId = Integer.parseInt(productIdString);
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product Name cannot be empty.");
            return;
        }

        String quantityText = quantityField.getText();
        if (quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Quantity cannot be empty.");
            return;
        }
        int newStock = Integer.parseInt(quantityText); 

        String priceText = priceField.getText();
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty.");
            return;
        }
        double newPrice = Double.parseDouble(priceText); 

        String newDescription = descriptionField.getText();
        String newImagePath = imagePathField.getText();

        Product updatedProduct = new Product(productId, name, newStock, newPrice, newDescription, newImagePath);

        Product.updateProduct(query, updatedProduct);

        JOptionPane.showMessageDialog(null, "Product updated successfully!");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Please enter valid numbers for Price and Quantity.");
    }
});
  
  JButton addNewButton  = new JButton("Add new");
  addNewButton.addActionListener(e -> {
      try {
          
     
          String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product Name cannot be empty.");
            return;
        }

        String quantityText = quantityField.getText();
        if (quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Quantity cannot be empty.");
            return;
        }
        int newStock = Integer.parseInt(quantityText); 

        String priceText = priceField.getText();
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty.");
            return;
        }
        double newPrice = Double.parseDouble(priceText); 

        String newDescription = descriptionField.getText();
        String newImagePath = imagePathField.getText();

         Product.addProduct(query, name, newStock, newPrice, newDescription, newImagePath);
         JOptionPane.showMessageDialog(null, "Product Add successfully!");
        }  catch (Exception err) {
           JOptionPane.showMessageDialog(null, "Please enter valid data."); 
      } 
  });
  JButton backToShopButton = new JButton("back to shop");
backToShopButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        MainFrame mainFrame = new MainFrame(user.getUserId(), user.getUsername(), user.getUsername(), user.getType());
        mainFrame.setVisible(true);
    }
});


    rightPanel.add(searchLabel);
    rightPanel.add(searchField);
    rightPanel.add(searchButton);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(nameLabel);
    rightPanel.add(nameField);
    rightPanel.add(priceLabel);
    rightPanel.add(priceField);
    rightPanel.add(quantityLabel);
    rightPanel.add(quantityField);
    rightPanel.add(imagePathLabel);
    rightPanel.add(imagePathField);
    rightPanel.add(descriptionLabel);
    rightPanel.add(new JScrollPane(descriptionField));
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(saveButton);
    rightPanel.add(addNewButton);
    


    JPanel buttonPanel = new JPanel();
    JButton loadProductsButton = new JButton("Load Products");
    buttonPanel.add(loadProductsButton);

    loadProductsButton.addActionListener(e -> loadProductsData());

    jPanel1.add(buttonPanel, BorderLayout.NORTH);
    jPanel1.add(leftPanel, BorderLayout.CENTER);
    jPanel1.add(rightPanel, BorderLayout.EAST);

JButton deleteButton = new JButton("Delete Product");
deleteButton.addActionListener(e -> {
    int selectedRow = dataTable.getSelectedRow();
    if (selectedRow != -1) {
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete this product?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int productId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                
                Product.deleteProduct(query, productId);

                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Product deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a product to delete.");
    }
});

rightPanel.add(Box.createVerticalStrut(10));
rightPanel.add(deleteButton);
rightPanel.add(backToShopButton);

    dataTable.getSelectionModel().addListSelectionListener(event -> populateFields());
}
    private void loadProductsData() {
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Stock", "Price", "Description", "Image Path"});
        ArrayList<Product> products = Product.loadProducts(query);
        tableModel.setRowCount(0);
        
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                product.getProductID(),
                product.getProductName(),
                product.getInStock(),
                product.getPrice(),
                product.getDescription(),
                product.getImagePath()
            });
        }
        dataTable.setModel(tableModel);  
    }

    private void searchProduct() {
        String searchTerm = searchField.getText().toLowerCase();
        ArrayList<Product> filteredProducts = Product.loadProducts(query, searchTerm);
        tableModel.setRowCount(0);
        for (Product product : filteredProducts) {
                tableModel.addRow(new Object[]{
                    product.getProductID(),
                    product.getProductName(),
                    product.getInStock(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getImagePath()
                });
            
        }
        dataTable.setModel(tableModel);
    }

    private void populateFields() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            quantityField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            imagePathField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            descriptionField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
