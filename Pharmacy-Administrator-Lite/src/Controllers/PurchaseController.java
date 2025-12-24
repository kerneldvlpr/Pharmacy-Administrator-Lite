package Controllers;

import Models.Product;
import Models.ProductActions;
import Models.Purchase;
import Models.PurchaseActions;
import Views.Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PurchaseController implements ItemListener, KeyListener, ActionListener, MouseListener {

    private final Administration administration; // Reference to the Administration view
    private final PurchaseActions purchaseActions; // Reference to PurchaseActions model
    private final ProductActions productActions; // Reference to ProductActions model
    private DefaultTableModel model; // Table model for purchases
    private Product product; // Reference to the current product

    public PurchaseController(Administration administration, PurchaseActions purchaseActions, ProductActions productActions) {
        this.administration = administration;
        this.purchaseActions = purchaseActions;
        this.productActions = productActions;
        // Add item listener to combo box
        this.administration.cmbPurchasesProduct.addItemListener(this);
        // Add key listener to text field
        this.administration.txtPurchaseProductQuantity.addKeyListener(this);
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
    }

    private void addActionListeners() {
        // Add action listeners to purchase-related buttons
        administration.btnPurchaseAdd.addActionListener(this);
        administration.btnPurchaseBuy.addActionListener(this);
        administration.btnPurchaseDelete.addActionListener(this);
        administration.btnPurchaseCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Add mouse listeners to purchase-related components
        administration.purchaseTable.addMouseListener(this);
        administration.lblPurchases.addMouseListener(this);
        administration.lblReports.addMouseListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Handle item state change in combo box
        if (e.getSource() == administration.cmbPurchasesProduct && e.getStateChange() == ItemEvent.SELECTED) {
            String productSelected = administration.cmbPurchasesProduct.getSelectedItem().toString().trim();
            try {
                // Extract the product ID from the selected item
                String productIdStr = productSelected.substring(productSelected.lastIndexOf("(") + 1, productSelected.length() - 1);
                int purchaseProductID = Integer.parseInt(productIdStr);
                administration.txtPurchaseProductID.setText(String.valueOf(purchaseProductID));
                // Search for the product by ID
                product = productActions.searchProductByID(purchaseProductID);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
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
        // Handle key release event in text field
        if (e.getSource() == administration.txtPurchaseProductQuantity) {
            // Calculate the total price based on the product quantity
            if (!administration.txtPurchaseProductQuantity.getText().isBlank() && purchaseActions.isNumericString(administration.txtPurchaseProductQuantity.getText().trim())) {
                double productPrice = product.getProductUnitPrice();
                double purchaseQuantity = Double.parseDouble(administration.txtPurchaseProductQuantity.getText().trim());
                administration.txtPurchasePrice.setText(String.valueOf(productPrice * purchaseQuantity));
            }
        }
    }

    private boolean areFieldsClean() {
        // Check if any purchase input field is blank
        return administration.cmbPurchasesProduct.getSelectedItem() == null
                || administration.txtPurchaseProductQuantity.getText().isBlank()
                || administration.cmbPurchasesSupplier.getSelectedItem() == null
                || administration.cmbPurchasesCustomer.getSelectedItem() == null;
    }

    private void calculateTotalPurchasesInProcess() {
        // Calculate the total price of purchases in process
        ArrayList<Purchase> purchases = purchaseActions.listPurchasesInProcess();
        double total = 0d;
        for (Purchase purchase : purchases) {
            total += purchase.getPurchasePrice();
        }
        administration.txtPurchaseTotal.setText(String.valueOf(total) + "$");
    }

    private void cleanFields() {
        // Clear purchase input fields
        administration.txtPurchaseProductQuantity.setText("");
        administration.txtPurchasePrice.setText("");
        administration.txtPurchaseID.setText("");
    }

    private void cleanTablePurchases() {
        // Clear the purchases table
        model = (DefaultTableModel) administration.purchaseTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private void cleanTableReports() {
        // Clear the reports table
        model = (DefaultTableModel) administration.reportsTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void loadPurchases() {
        // Load purchases in process and display in table
        ArrayList<Purchase> purchases = purchaseActions.listPurchasesInProcess();
        model = (DefaultTableModel) administration.purchaseTable.getModel();
        Object[] row = new Object[7];
        for (Purchase purchase : purchases) {
            row[0] = purchase.getPurchaseID();
            row[1] = purchase.getPurchaseProductID();
            row[2] = purchase.getPurchaseProduct();
            row[3] = purchase.getPurchaseQuantityToBuy();
            row[4] = purchase.getPurchasePrice();
            row[5] = purchase.getPurchaseSupplier();
            row[6] = purchase.getPurchaseCustomer();
            model.addRow(row);
        }
    }

    public void loadReports() {
        // Load completed purchases and display in reports table
        ArrayList<Purchase> purchases = purchaseActions.listPurchasesCompleted();
        model = (DefaultTableModel) administration.reportsTable.getModel();
        Object[] row = new Object[3];
        for (Purchase purchase : purchases) {
            row[0] = purchase.getPurchaseCustomer();
            row[1] = purchase.getPurchasePrice();
            row[2] = purchase.getPurchaseDateTime();
            model.addRow(row);
        }
    }

    private void cleanFieldsProducts() {
        // Clear product input fields
        administration.txtProductID.setText("");
        administration.txtProductDescription.setText("");
        administration.txtProductName.setText("");
        administration.txtProductQuantity.setText("");
        administration.txtProductSalesPrice.setText("");
    }

    public void loadProducts() {
        // Load products from database and display in table
        Map<Integer, Product> products = productActions.listProducts(productActions.nameAdditionalValidation(administration.txtSearchProduct.getText().trim()));
        model = (DefaultTableModel) administration.productTable.getModel();
        Object[] row = new Object[6];
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            Product value = entry.getValue();
            row[0] = value.getProductID();
            row[1] = value.getProductName();
            row[2] = value.getProductDescription();
            row[3] = value.getProductUnitPrice();
            row[4] = value.getProductQuantity();
            row[5] = value.getProductCategoryName();
            model.addRow(row);
        }
    }

    private void cleanTableProducts() {
        // Clear the products table
        model = (DefaultTableModel) administration.productTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private void purchaseAdd() {
        // Add a purchase in process
        if (!purchaseActions.isNumericString(administration.txtPurchaseProductQuantity.getText().trim())) {
            // Show error message if quantity is not numeric
            JOptionPane.showMessageDialog(null, "The format of Quantity entered is not valid");
        } else if (product.getProductQuantity() == 0) {
            // Show error message if product quantity is zero
            JOptionPane.showMessageDialog(null, "The quantity of product has run out");
        } else if (Integer.valueOf(administration.txtPurchaseProductQuantity.getText().trim()) <= 0) {
            // Show error message if quantity is not valid
            JOptionPane.showMessageDialog(null, "The amount entered is not valid");
        } else if (Integer.valueOf(administration.txtPurchaseProductQuantity.getText().trim()) > product.getProductQuantity()) {
            // Show error message if requested quantity exceeds available quantity
            JOptionPane.showMessageDialog(null, "The quantity requested exceeds the available quantity of the product,\nwhich is: "
                    + product.getProductQuantity() + "\nPlease request a lower quantity.");
        } else {
            // Add purchase to the database
            int productQuantityToRest = Integer.valueOf(administration.txtPurchaseProductQuantity.getText().trim());
            purchaseActions.addPurchase(product.getProductID(),
                    product.getProductName(), administration.cmbPurchasesSupplier.getSelectedItem().toString().trim(),
                    productQuantityToRest, administration.cmbPurchasesCustomer.getSelectedItem().toString(),
                    Double.valueOf(administration.txtPurchasePrice.getText().trim()));
            refreshPurchaseData(); // Refresh purchase data
            product.setProductQuantity(product.getProductQuantity() - productQuantityToRest); // Update product quantity
            refreshProductData(); // Refresh product data
            JOptionPane.showMessageDialog(null, "Successful purchase in process"); // Show success message
        }
    }

    private void buyPurchases() {
        // Finalize purchases in process
        ArrayList<Purchase> purchasesInProcess = purchaseActions.listPurchasesInProcess();
        if (!purchasesInProcess.isEmpty()) {
            purchaseActions.finaliseBuy(); // Finalize all purchases in process
            refreshPurchaseData(); // Refresh purchase data
            refreshReportsData(); // Refresh reports data
            calculateGains(); // Calculate total gains
            cleanFields(); // Clear input fields
            administration.txtPurchaseTotal.setText(""); // Clear total purchase text field
            JOptionPane.showMessageDialog(null, "Successful purchases"); // Show success message
        } else {
            JOptionPane.showMessageDialog(null, "No purchases registered"); // Show error message if no purchases
        }
    }

    private void deletePurchase() {
        // Delete a purchase in process
        int productQuantityToRest = Integer.valueOf(administration.txtPurchaseProductQuantity.getText().trim());
        purchaseActions.deletePurchaseInProcess(Integer.valueOf(administration.txtPurchaseID.getText().trim())); // Delete purchase
        administration.btnPurchaseAdd.setEnabled(true); // Enable add button
        refreshPurchaseData(); // Refresh purchase data
        product.setProductQuantity(product.getProductQuantity() + productQuantityToRest); // Update product quantity
        refreshProductData(); // Refresh product data
        JOptionPane.showMessageDialog(null, "Purchase successfully eliminated"); // Show success message
    }

    private void calculateGains() {
        // Calculate total gains from completed purchases
        ArrayList<Purchase> reports = purchaseActions.listPurchasesCompleted();
        double total = 0d;
        for (Purchase report : reports) {
            total += report.getPurchasePrice();
        }
        administration.txtPurchaseReport.setText(String.valueOf(total) + "$");
    }

    private void refreshPurchaseData() {
        // Refresh purchases table and fields
        cleanTablePurchases(); // Clear purchases table
        cleanFields(); // Clear input fields
        loadPurchases(); // Load purchases
        calculateTotalPurchasesInProcess(); // Calculate total purchases in process
    }

    private void refreshProductData() {
        // Refresh products table and fields
        cleanTableProducts(); // Clear products table
        cleanFieldsProducts(); // Clear product input fields
        loadProducts(); // Load products
    }

    private void refreshReportsData() {
        // Refresh reports table
        cleanTableReports(); // Clear reports table
        loadReports(); // Load reports
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events for buttons
        Object source = e.getSource();
        if (source == administration.btnPurchaseAdd) {
            // Add purchase
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error message if fields are empty
            } else {
                purchaseAdd();
            }
        } else if (source == administration.btnPurchaseBuy) {
            buyPurchases(); // Finalize purchases
        } else if (source == administration.btnPurchaseDelete) {
            // Delete purchase
            int row = administration.purchaseTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the purchase's row."); // Show error message if no row selected
            } else {
                deletePurchase();
            }
        } else if (source == administration.btnPurchaseCancel) {
            administration.btnPurchaseAdd.setEnabled(true); // Enable add button
            refreshPurchaseData(); // Refresh purchase data
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click events
        if (e.getSource() == administration.purchaseTable) {
            // Populate fields with selected purchase data
            int row = administration.purchaseTable.rowAtPoint(e.getPoint());
            administration.txtPurchaseID.setText(administration.purchaseTable.getValueAt(row, 0).toString());
            administration.txtPurchaseProductID.setText(administration.purchaseTable.getValueAt(row, 1).toString());
            administration.cmbPurchasesProduct.setSelectedItem(administration.purchaseTable.getValueAt(row, 2)
                    + " (" + administration.txtPurchaseProductID.getText() + ")");
            administration.txtPurchaseProductQuantity.setText(administration.purchaseTable.getValueAt(row, 3).toString());
            administration.txtPurchasePrice.setText(administration.purchaseTable.getValueAt(row, 4).toString());
            administration.cmbPurchasesSupplier.setSelectedItem(administration.purchaseTable.getValueAt(row, 5).toString());
            administration.cmbPurchasesCustomer.setSelectedItem(administration.purchaseTable.getValueAt(row, 6).toString());
        } else if (e.getSource() == administration.lblPurchases) {
            administration.jTabbedPanePanels.setSelectedIndex(1); // Switch to Purchases tab
        } else if (e.getSource() == administration.lblReports) {
            administration.jTabbedPanePanels.setSelectedIndex(6); // Switch to Reports tab
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
}
