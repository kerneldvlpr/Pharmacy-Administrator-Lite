package Controllers;

import Models.Product;
import Models.ProductActions;
import Views.Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // Reference to the Administration view
    private final ProductActions productActions; // Reference to ProductActions model
    private DefaultTableModel model; // Table model for products

    public ProductController(Administration administration, ProductActions productActions) {
        this.administration = administration;
        this.productActions = productActions;
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
        // Add key listeners to search field
        addKeyListeners();
    }

    private void addActionListeners() {
        // Add action listeners to product-related buttons
        administration.btnProductRegister.addActionListener(this);
        administration.btnProductModify.addActionListener(this);
        administration.btnProductDelete.addActionListener(this);
        administration.btnProductCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Add mouse listeners to product-related components
        administration.lblProducts.addMouseListener(this);
        administration.productTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        // Add key listener to the search product field
        administration.txtSearchProduct.addKeyListener(this);
    }

    private boolean areFieldsClean() {
        // Check if any product input field is blank
        return administration.txtProductDescription.getText().isBlank()
                || administration.txtProductName.getText().isBlank()
                || administration.txtProductQuantity.getText().isBlank()
                || administration.txtProductSalesPrice.getText().isBlank();
    }

    private void cleanTableAndCmb() {
        // Clear the product table and combo box
        model = (DefaultTableModel) administration.productTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
        administration.cmbPurchasesProduct.removeAllItems(); // Remove all items from the combo box
    }

    private void cleanFields() {
        // Clear product input fields
        administration.txtProductID.setText("");
        administration.txtProductDescription.setText("");
        administration.txtProductName.setText("");
        administration.txtProductQuantity.setText("");
        administration.txtProductSalesPrice.setText("");
    }

    public void loadProducts() {
        // Load products from database and display in table and combo box
        Map<Integer, Product> products = productActions.listProducts(productActions.nameAdditionalValidation(administration.txtSearchProduct.getText().trim()));
        model = (DefaultTableModel) administration.productTable.getModel();
        Object[] row = new Object[6]; // Array to hold product data for each row
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            Product value = entry.getValue();
            row[0] = value.getProductID();
            row[1] = value.getProductName();
            row[2] = value.getProductDescription();
            row[3] = value.getProductUnitPrice();
            row[4] = value.getProductQuantity();
            row[5] = value.getProductCategoryName();
            model.addRow(row); // Add row to the table
            administration.cmbPurchasesProduct.addItem(row[1] + " (" + row[0] + ")"); // Add product to the combo box
        }
    }

    private void registerProduct() {
        // Register a new product and refresh data
        if (!productActions.isNumericString(administration.txtProductQuantity.getText().trim())) {
            // Show error message if quantity is not numeric
            JOptionPane.showMessageDialog(null, "Remember that in the Quantity field,\nthere can only be numerical characters.");
        } else if (!productActions.isDoubleString(administration.txtProductSalesPrice.getText().trim())) {
            // Show error message if sales price is not a double
            JOptionPane.showMessageDialog(null, "Remember that in the Unit Price field,\nthere can only be double characters.");
        } else {
            // Add product to the database
            productActions.addProduct(productActions.nameAdditionalValidation(administration.txtProductName.getText().trim()),
                    administration.txtProductDescription.getText().trim(),
                    Double.parseDouble(administration.txtProductSalesPrice.getText().trim()),
                    Integer.valueOf(administration.txtProductQuantity.getText()),
                    administration.cmbProductsCategories.getSelectedItem().toString());
            refreshProductData(); // Refresh the product data
            JOptionPane.showMessageDialog(null, "Product registered successfully."); // Show success message
        }
    }

    private void modifyProduct() {
        // Modify an existing product and refresh data
        if (!productActions.isNumericString(administration.txtProductQuantity.getText().trim())) {
            // Show error message if quantity is not numeric
            JOptionPane.showMessageDialog(null, "Remember that in the Quantity field,\nthere can only be numerical characters.");
        } else if (!productActions.isDoubleString(administration.txtProductSalesPrice.getText().trim())) {
            // Show error message if sales price is not a double
            JOptionPane.showMessageDialog(null, "Remember that in the Unit Price field,\nthere can only be double characters.");
        } else if (productActions.updateProduct(Integer.valueOf(administration.txtProductID.getText()),
                productActions.nameAdditionalValidation(administration.txtProductName.getText().trim()),
                administration.txtProductDescription.getText().trim(),
                Double.valueOf(administration.txtProductSalesPrice.getText().trim()),
                Integer.valueOf(administration.txtProductQuantity.getText().trim()),
                administration.cmbProductsCategories.getSelectedItem().toString())) {
            refreshProductData(); // Refresh the product data
            administration.btnProductRegister.setEnabled(true); // Enable the register button
            JOptionPane.showMessageDialog(null, "Product modified successfully."); // Show success message
        }
    }

    private void deleteProduct() {
        // Delete a product and refresh data
        if (productActions.deleteProduct(Integer.valueOf(administration.txtProductID.getText()))) {
            refreshProductData(); // Refresh the product data
            administration.txtPurchaseProductID.setText(""); // Clear the product ID field
            administration.btnProductRegister.setEnabled(true); // Enable the register button
            JOptionPane.showMessageDialog(null, "Product removed successfully."); // Show success message
        }
    }

    private void refreshProductData() {
        // Refresh product table and fields
        cleanTableAndCmb(); // Clear the table and combo box
        cleanFields(); // Clear the input fields
        loadProducts(); // Load products into the table and combo box
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions
        Object source = e.getSource();
        if (source == administration.btnProductRegister) {
            // Handle register product button
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error if fields are blank
            } else {
                registerProduct(); // Register the product
            }
        } else if (source == administration.btnProductModify) {
            // Handle modify product button
            int row = administration.productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the product's row."); // Show error if no row is selected
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error if fields are blank
            } else {
                modifyProduct(); // Modify the product
            }
        } else if (source == administration.btnProductDelete) {
            // Handle delete product button
            int row = administration.productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the product's row."); // Show error if no row is selected
            } else {
                deleteProduct(); // Delete the product
            }
        } else if (source == administration.btnProductCancel) {
            // Handle cancel button
            refreshProductData(); // Refresh the product data
            administration.btnProductRegister.setEnabled(true); // Enable the register button
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle table row click to populate product fields
        if (e.getSource() == administration.productTable) {
            int row = administration.productTable.rowAtPoint(e.getPoint()); // Get the clicked row
            administration.txtProductID.setText(administration.productTable.getValueAt(row, 0).toString());
            administration.txtProductName.setText(administration.productTable.getValueAt(row, 1).toString());
            administration.txtProductDescription.setText(administration.productTable.getValueAt(row, 2).toString());
            administration.txtProductSalesPrice.setText(administration.productTable.getValueAt(row, 3).toString());
            administration.txtProductQuantity.setText(administration.productTable.getValueAt(row, 4).toString());
            administration.cmbProductsCategories.setSelectedItem(administration.productTable.getValueAt(row, 5).toString());
            administration.btnProductRegister.setEnabled(false); // Disable the register button
        } else if (e.getSource() == administration.lblProducts) {
            // Switch to the product management tab
            administration.jTabbedPanePanels.setSelectedIndex(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release event in search field
        if (e.getSource() == administration.txtSearchProduct) {
            cleanTableAndCmb(); // Clear the table and combo box
            loadProducts(); // Load products based on the search input
        }
    }
}
