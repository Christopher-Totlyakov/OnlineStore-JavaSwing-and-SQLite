/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author User
 */
public class Checkout extends javax.swing.JFrame {

    private Connect query = new Connect();
    private JTable cartTable;
    private ShoppingCartTableModel tableModel;
    private List<ShoppingCart> cartItems;
    User user;
    
    public Checkout() {
        initComponents();
        CustomComponents();
        
    }
    
    public Checkout(User user, List<ShoppingCart> cartItems) {
        this.cartItems = cartItems;
        this.user = user;
        initComponents();
        CustomComponents();
        
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
    
    
    private void CustomComponents() {
    setTitle("Checkout");
    setSize(600, 400);

    tableModel = new ShoppingCartTableModel(cartItems);
    cartTable = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(cartTable);

    jPanel1.setLayout(new BorderLayout());

    jPanel1.add(scrollPane, BorderLayout.CENTER);

    JButton checkoutButton = new JButton("Checkout");
    checkoutButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (cartItems == null || cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            String sql = "INSERT INTO sales (CustomerID, ProductID, Quantity, SaleDate) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = query.conn.prepareStatement(sql)) {
                for (ShoppingCart cartItem : cartItems) {
                    stmt.setInt(1, user.getUserId()); 
                    stmt.setInt(2, cartItem.getProduct().getProductID()); 
                    stmt.setInt(3, cartItem.getQuantity());
                    
                    java.util.Date utilDate = new java.util.Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = formatter.format(utilDate);
                    System.out.println("Formatted Date: " + formattedDate);

                    stmt.setDate(4, java.sql.Date.valueOf(formattedDate));

                    stmt.addBatch();
                }

                int[] results = stmt.executeBatch(); 

                JOptionPane.showMessageDialog(null, "Purchase completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                cartItems.clear(); 
                tableModel.fireTableDataChanged(); 
                MainFrame mainFrame = new MainFrame(user.getUserId(), user.getUsername(), user.getUsername() ,user.getType());
                     mainFrame.setVisible(true);
                     dispose();
            }catch( SQLException err)
            {
                System.out.println(err);
            }
    }
});

    
    jPanel1.add(checkoutButton, BorderLayout.SOUTH);

    cartTable.setComponentPopupMenu(createTablePopupMenu());
}


    private JPopupMenu createTablePopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Quantity");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    String newQuantityStr = JOptionPane.showInputDialog("Enter new quantity:");
                    try {
                        int newQuantity = Integer.parseInt(newQuantityStr);
                        tableModel.setValueAt(newQuantity, selectedRow, 2); 
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem deleteItem = new JMenuItem("Delete Product");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeProduct(selectedRow);
                }
            }
        });
        JMenuItem openNewWindowItem = new JMenuItem("back to shop");
    openNewWindowItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    MainFrame mainFrame = new MainFrame(user.getUserId(), user.getUsername(), user.getUsername(), user.getType());
                    mainFrame.setVisible(true);
                }
            });
        }
    });
        

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);
        popupMenu.add(openNewWindowItem);

        return popupMenu;
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
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Checkout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
