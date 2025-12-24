package Controllers;

import Models.Supplier;
import Models.SupplierActions;
import Views.Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SupplierController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // Reference to the Administration view
    private final SupplierActions supplierActions; // Reference to SupplierActions model
    private DefaultTableModel model; // Table model for supplier table

    public SupplierController(Administration administration, SupplierActions supplierActions) {
        this.administration = administration;
        this.supplierActions = supplierActions;
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
        // Add key listeners to search field
        addKeyListeners();
    }

    private void addActionListeners() {
        // Add action listeners to the supplier management buttons
        administration.btnSupplierRegister.addActionListener(this);
        administration.btnSupplierModify.addActionListener(this);
        administration.btnSupplierDelete.addActionListener(this);
    }

    private void addMouseListeners() {
        // Add mouse listeners to the supplier label and table
        administration.lblSuppliers.addMouseListener(this);
        administration.supplierTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        // Add key listener to the supplier search text field
        administration.txtSearchSupplier.addKeyListener(this);
    }

    private boolean areFieldsClean() {
        // Check if any supplier input field is blank
        return administration.txtSupplierAddress.getText().isBlank()
                || administration.txtSupplierCity.getText().isBlank()
                || administration.txtSupplierDescription.getText().isBlank()
                || administration.txtSupplierMail.getText().isBlank()
                || administration.txtSupplierName.getText().isBlank()
                || administration.txtSupplierTelephone.getText().isBlank();
    }

    private void cleanFields() {
        // Clear all supplier input fields
        administration.txtSupplierID.setText("");
        administration.txtSupplierAddress.setText("");
        administration.txtSupplierCity.setText("");
        administration.txtSupplierDescription.setText("");
        administration.txtSupplierMail.setText("");
        administration.txtSupplierName.setText("");
        administration.txtSupplierTelephone.setText("");
    }

    private void cleanTableAndCmb() {
        // Clear the supplier table and combo box
        model = (DefaultTableModel) administration.supplierTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        administration.cmbPurchasesSupplier.removeAllItems();
    }

    public void loadSuppliers() {
        // Load suppliers from the database and display in the table and combo box
        Map<Integer, Supplier> suppliers = supplierActions.listSuppliers(supplierActions.nameAdditionalValidation(administration.txtSearchSupplier.getText().trim()));
        model = (DefaultTableModel) administration.supplierTable.getModel();
        Object[] row = new Object[7];
        for (Map.Entry<Integer, Supplier> entry : suppliers.entrySet()) {
            Supplier value = entry.getValue();
            row[0] = value.getSupplierID();
            row[1] = value.getSupplierName();
            row[2] = value.getSupplierDescription();
            row[3] = value.getSupplierAddress();
            row[4] = value.getSupplierTelephone();
            row[5] = value.getSupplierEmail();
            row[6] = value.getSupplierCity();
            model.addRow(row); // Add supplier data to the table
            administration.cmbPurchasesSupplier.addItem(row[1] + " (" + row[0] + ")"); // Add supplier to combo box
        }
    }

    private void supplierModify() {
        // Modify an existing supplier
        try {
            String email = administration.txtSupplierMail.getText().trim().toLowerCase();
            String telephone = administration.txtSupplierTelephone.getText().trim();
            String currentEmail = administration.supplierTable.getValueAt(administration.supplierTable.getSelectedRow(), 5).toString();
            String currentTelephone = administration.supplierTable.getValueAt(administration.supplierTable.getSelectedRow(), 4).toString();
            if (!supplierActions.emailValidationOne(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } else if (!supplierActions.isEmailUnique(email) && !email.equalsIgnoreCase(currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } else if (!supplierActions.isNumericString(telephone)) {
                JOptionPane.showMessageDialog(null, "Invalid telephone format.");
            } else if (!supplierActions.isTelephoneUnique(telephone) && !telephone.equalsIgnoreCase(currentTelephone)) {
                JOptionPane.showMessageDialog(null, "Telephone already in use.");
            } else if (supplierActions.modifySupplier(supplierActions.nameAdditionalValidation(administration.txtSupplierName.getText().trim()),
                    email, administration.txtSupplierAddress.getText().trim(),
                    Integer.valueOf(administration.txtSupplierID.getText()), telephone, administration.txtSupplierCity.getText().trim(), administration.txtSupplierDescription.getText().trim())) {
                refreshSupplierData(); // Refresh supplier data
                JOptionPane.showMessageDialog(null, "Supplier modified successfully.");
                administration.btnSupplierRegister.setEnabled(true); // Enable register button
            }
        } catch (Exception ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred during the process.");
        }
    }

    private void supplierRegister() {
        // Register a new supplier
        try {
            String email = administration.txtSupplierMail.getText().trim().toLowerCase();
            String telephone = administration.txtSupplierTelephone.getText().trim();
            if (!supplierActions.emailValidationOne(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } else if (!supplierActions.isEmailUnique(email)) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } else if (!supplierActions.isNumericString(telephone)) {
                JOptionPane.showMessageDialog(null, "Invalid telephone format.");
            } else if (!supplierActions.isTelephoneUnique(telephone)) {
                JOptionPane.showMessageDialog(null, "Telephone already in use.");
            } else {
                supplierActions.addSupplier(supplierActions.nameAdditionalValidation(administration.txtSupplierName.getText().trim()),
                        email, administration.txtSupplierAddress.getText().trim(),
                        telephone, administration.txtSupplierCity.getText().trim(), administration.txtSupplierDescription.getText().trim());
                refreshSupplierData(); // Refresh supplier data
                JOptionPane.showMessageDialog(null, "Supplier registered successfully.");
            }
        } catch (Exception ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred during the process.");
        }
    }

    private void deleteSupplier() {
        // Delete a supplier
        try {
            if (supplierActions.deleteSupplier(Integer.valueOf(administration.txtSupplierID.getText()))) {
                refreshSupplierData(); // Refresh supplier data
                JOptionPane.showMessageDialog(null, "Supplier successfully removed.");
                administration.btnSupplierRegister.setEnabled(true); // Enable register button
            }
        } catch (Exception ex) {
            Logger.getLogger(SupplierController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred during the process.");
        }
    }

    private void refreshSupplierData() {
        // Refresh supplier table and fields
        cleanTableAndCmb(); // Clear table and combo box
        cleanFields(); // Clear input fields
        loadSuppliers(); // Load suppliers from the database
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events for supplier management buttons
        Object source = e.getSource();
        if (source == administration.btnSupplierRegister) {
            // Register supplier if fields are not blank
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                supplierRegister();
            }
        } else if (source == administration.btnSupplierModify) {
            // Modify supplier if a row is selected and fields are not blank
            int row = administration.supplierTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the supplier's row.");
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                supplierModify();
            }
        } else if (source == administration.btnSupplierDelete) {
            // Delete supplier if a row is selected
            int row = administration.supplierTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the supplier's row.");
            } else {
                deleteSupplier();
            }
        } else if (source == administration.btnSupplierCancel) {
            // Cancel supplier action and refresh data
            refreshSupplierData();
            administration.btnSupplierRegister.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse clicks on the supplier table
        if (e.getSource() == administration.supplierTable) {
            // Populate supplier fields when a row in the table is clicked
            int row = administration.supplierTable.rowAtPoint(e.getPoint());
            administration.txtSupplierID.setText(administration.supplierTable.getValueAt(row, 0).toString());
            administration.txtSupplierName.setText(administration.supplierTable.getValueAt(row, 1).toString());
            administration.txtSupplierDescription.setText(administration.supplierTable.getValueAt(row, 2).toString());
            administration.txtSupplierAddress.setText(administration.supplierTable.getValueAt(row, 3).toString());
            administration.txtSupplierTelephone.setText(administration.supplierTable.getValueAt(row, 4).toString());
            administration.txtSupplierMail.setText(administration.supplierTable.getValueAt(row, 5).toString());
            administration.txtSupplierCity.setText(administration.supplierTable.getValueAt(row, 6).toString());
            administration.btnSupplierRegister.setEnabled(false); // Disable register button
        } else if (e.getSource() == administration.lblSuppliers) {
            // Switch to suppliers tab when label is clicked
            administration.jTabbedPanePanels.setSelectedIndex(4);
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
        // Handle key release events for the search field
        if (e.getSource() == administration.txtSearchSupplier) {
            // Filter suppliers when a key is released in the search field
            cleanTableAndCmb(); // Clear table and combo box
            loadSuppliers(); // Load filtered suppliers
        }
    }
}
