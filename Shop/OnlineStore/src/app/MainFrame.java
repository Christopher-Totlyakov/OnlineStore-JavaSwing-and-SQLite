/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author user
 */
public class MainFrame extends javax.swing.JFrame {

    private Connect query = new Connect();
    private JPanel productPanel;
    private JButton nextPageButton;
    private JButton prevPageButton;
    private JLabel pageLabel;
    
    private static ArrayList<ShoppingCart> items = new ArrayList<>();
   
    User user;
    private static MainFrame currentInstance;

    private int currentPage = 1;
    private final int PRODUCTS_PER_PAGE = 6;
    
    public MainFrame() {
        initComponents();
        CustomComponents();
        loadProducts();
        user = new User(0,"Anonymous", "Null", "Anonymous");
         updateUserInfo();
         
          if (currentInstance != null) {
            currentInstance.dispose();
        }
        currentInstance = this;

    }
    public MainFrame(int id, String username, String password, String type) {
        initComponents();
        CustomComponents();
        loadProducts();
        user = new User(id, username, password, type);
        updateUserInfo();
        
         if (currentInstance != null) {
            currentInstance.dispose();
        }
        currentInstance = this;
        
        if (!username.equalsIgnoreCase("Anonymous")) {
             Component component = jMenuBar1.getComponent(0);
        component.setVisible(false);
        }
        //jMenuBar1.add(new JMenu());
    }
private void updateUserInfo() {

    JLabel userLabel = new JLabel("Logged in as: " + user.getUsername() + " (" + user.getType()+ ")");
    userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    userLabel.setForeground(Color.BLUE); 
    userLabel.setFont(new Font("Arial", Font.PLAIN, 12)); 

    
    jMenuBar1.add(Box.createHorizontalGlue()); 
    jMenuBar1.add(userLabel);

  
    
    Component component = jMenuBar1.getComponent(2);
    if (!user.getType().contains("Worker")) {
        
        component.setVisible(false);

    }
    else{
         component.setVisible(true);
    }
   
    jMenuBar1.revalidate();
    jMenuBar1.repaint();
}



  private void loadProducts() {
      
        productPanel.removeAll();

        ArrayList<Product> products = Product.loadProducts(query, currentPage, PRODUCTS_PER_PAGE);

        if (products.isEmpty() && currentPage > 1) {
            currentPage--;
            return;
        }

        for (Product product : products) {
            JPanel productCard = createProductCard(product);
            productPanel.add(productCard);
        }

        pageLabel.setText("Page: " + currentPage);

        productPanel.revalidate();
        productPanel.repaint();

        prevPageButton.setEnabled(currentPage > 1);
        nextPageButton.setEnabled(products.size() == PRODUCTS_PER_PAGE);
    }

    

    private JPanel createProductCard(Product product) {
    JPanel productCard = new JPanel();
    productCard.setLayout(new BorderLayout());
    productCard.setPreferredSize(new Dimension(180, 250));
    productCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    try {
        URL imageUrl = new URL(product.getImagePath()); 
        ImageIcon productImageIcon = new ImageIcon(imageUrl);

        
        Image image = productImageIcon.getImage();
        
        Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel productImage = new JLabel(scaledIcon);
        productCard.add(productImage, BorderLayout.NORTH);
    } catch (MalformedURLException e) {
        System.out.println("Invalid image URL: " + product.getImagePath());
    }

    JPanel textPanel = new JPanel();
    textPanel.setLayout(new GridLayout(2, 1));
    textPanel.add(new JLabel(product.getProductName()));
    textPanel.add(new JLabel("Price: $" + product.getPrice()));
    productCard.add(textPanel, BorderLayout.CENTER);

    JButton detailsButton = new JButton("Details");
detailsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));

        try {
            URL imageUrl = new URL(product.getImagePath());
            ImageIcon productImageIcon = new ImageIcon(imageUrl);
            Image image = productImageIcon.getImage();
            Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel productImageLabel = new JLabel(scaledIcon);
            productDetailsPanel.add(productImageLabel);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        productDetailsPanel.add(new JLabel("Product Name: " + product.getProductName()));

        String availability = (product.getInStock()> 0) ? "In Stock" : "Out of Stock";
        productDetailsPanel.add(new JLabel("Availability: " + availability));

        productDetailsPanel.add(new JLabel("Price: $" + product.getPrice()));

        productDetailsPanel.add(new JLabel("Description: " + product.getDescription()));

        JOptionPane.showMessageDialog(null, productDetailsPanel, "Product Details", JOptionPane.INFORMATION_MESSAGE);
    }
});
    productCard.add(detailsButton, BorderLayout.SOUTH);
    
    JButton purchaseButton = new JButton("Buy");
    purchaseButton.addActionListener(new ActionListener() {
        @Override
    public void actionPerformed(ActionEvent e) {
        String quantityString = JOptionPane.showInputDialog(
            null, 
            "Enter quantity for " + product.getProductName() + ":",
            "Purchase Product",
            JOptionPane.QUESTION_MESSAGE
        );

        if (quantityString != null && !quantityString.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityString);
                if (quantity > 0) {
                    boolean productExists = false;

                    for (int i = 0; i < items.size(); i++) {
                        ShoppingCart item = items.get(i);
                        if (item.getProduct().equals(product)) {
                            item.setQuantity(item.getQuantity() + quantity);
                            productExists = true;
                            break;
                        }
                    }

                    if (!productExists) {
                        items.add(new ShoppingCart(product, quantity));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Invalid quantity. Please enter a value more than 1", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Please enter a valid number.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    });
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BorderLayout());
    buttonPanel.add(detailsButton, BorderLayout.WEST);
    buttonPanel.add(purchaseButton, BorderLayout.EAST);
    productCard.add(buttonPanel, BorderLayout.SOUTH);

    return productCard;
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
        jMenuBar1 = new javax.swing.JMenuBar();
        loginButton = new javax.swing.JMenu();
        Checkout = new javax.swing.JMenu();
        AdminArea = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        loginButton.setText("Login");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
        jMenuBar1.add(loginButton);

        Checkout.setText("Checkout");
        Checkout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CheckoutMouseClicked(evt);
            }
        });
        jMenuBar1.add(Checkout);

        AdminArea.setText("Admin Area");
        AdminArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminAreaMouseClicked(evt);
            }
        });
        jMenuBar1.add(AdminArea);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
      private void CustomComponents() {
    setTitle("Product Viewer");
    setSize(600, 400);

    productPanel = new JPanel();
    productPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); 

    JScrollPane scrollPane = new JScrollPane(productPanel);

    jPanel1.setLayout(new BorderLayout());  
    jPanel1.add(scrollPane, BorderLayout.CENTER);

    JPanel paginationPanel = new JPanel();
    prevPageButton = new JButton("Previous");
    nextPageButton = new JButton("Next");
    pageLabel = new JLabel("Page: 1");

    prevPageButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPage > 1) {
                currentPage--;
                loadProducts();
            }
        }
    });

    nextPageButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPage++;
            loadProducts();
        }
    });

    paginationPanel.add(prevPageButton);
    paginationPanel.add(pageLabel);
    paginationPanel.add(nextPageButton);

    jPanel1.add(paginationPanel, BorderLayout.SOUTH); 
}



    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        query.close();
    }//GEN-LAST:event_formWindowClosing

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_loginButtonMouseClicked

    private void CheckoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CheckoutMouseClicked
        this.dispose();
        Checkout checkout = new Checkout(user, items);
        checkout.setVisible(true);
    }//GEN-LAST:event_CheckoutMouseClicked

    private void AdminAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminAreaMouseClicked
        this.dispose();
        Admin checkout = new Admin(user);
        checkout.setVisible(true);
    }//GEN-LAST:event_AdminAreaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AdminArea;
    private javax.swing.JMenu Checkout;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu loginButton;
    // End of variables declaration//GEN-END:variables
}
