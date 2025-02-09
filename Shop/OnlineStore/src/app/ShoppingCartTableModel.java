package app;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ShoppingCartTableModel extends AbstractTableModel {

    private List<ShoppingCart> cartItems;

    private String[] columnNames = { "Product Name", "Price", "Quantity", "Total", "In Stock", "Actions" };

    public ShoppingCartTableModel(List<ShoppingCart> cartItems) {
        this.cartItems = (cartItems == null) ? new ArrayList<>() : cartItems;
    }

    @Override
    public int getRowCount() {
        return cartItems != null ? cartItems.size() : 0; 
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (cartItems == null || cartItems.isEmpty()) {
            return null;
        }

        ShoppingCart item = cartItems.get(rowIndex);
        Product product = item.getProduct();

        switch (columnIndex) {
            case 0:
                return product.getProductName();
            case 1:
                return product.getPrice();
            case 2:
                return item.getQuantity();
            case 3:
                return product.getPrice() * item.getQuantity();
            case 4:
                return product.getInStock() >= item.getQuantity() ? "In Stock" : "Out of Stock";
            case 5:
                return "Edit/Delete";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2 || columnIndex == 5; 
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (columnIndex == 2) {
        try {
            int newQuantity = Integer.parseInt(aValue.toString());
            ShoppingCart item = cartItems.get(rowIndex);
            item.setQuantity(newQuantity);

            fireTableRowsUpdated(rowIndex, rowIndex); 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    public void removeProduct(int rowIndex) {
        if (cartItems != null && rowIndex >= 0 && rowIndex < cartItems.size()) {
            cartItems.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
