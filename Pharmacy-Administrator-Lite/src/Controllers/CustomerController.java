package Controllers;

import Models.Customer;
import Models.CustomerActions;
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

public class CustomerController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // The administration view instance
    private final CustomerActions customerActions; // Handles actions related to customers
    private DefaultTableModel model; // Table model for the customers table

    public CustomerController(Administration administration, CustomerActions customerActions) {
        this.administration = administration;
        this.customerActions = customerActions;
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
        // Add key listeners to search field
        addKeyListeners();
    }

    private void addActionListeners() {
        // Attach action listeners to customer-related buttons
        administration.btnCustomerRegister.addActionListener(this);
        administration.btnCustomerModify.addActionListener(this);
        administration.btnCustomerDelete.addActionListener(this);
        administration.btnCustomerCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Attach mouse listeners to the customers table and label
        administration.customersTable.addMouseListener(this);
        administration.lblCustomers.addMouseListener(this);
    }

    private void addKeyListeners() {
        // Attach key listeners to the search customer input field
        administration.txtSearchCustomer.addKeyListener(this);
    }

    private void cleanFields() {
        // Clear customer input fields
        administration.txtCustomerAddress.setText("");
        administration.txtCustomerID.setText("");
        administration.txtCustomerTelephone.setText("");
        administration.txtCustomerMail.setText("");
        administration.txtCustomerName.setText("");
    }

    private boolean areFieldsClean() {
        // Check if any customer input field is blank
        return administration.txtCustomerAddress.getText().isBlank()
                || administration.txtCustomerMail.getText().isBlank()
                || administration.txtCustomerName.getText().isBlank()
                || administration.txtCustomerTelephone.getText().isBlank();
    }

    private void cleanTableAndCmb() {
        // Clear the customer table and combo box
        model = (DefaultTableModel) administration.customersTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
        administration.cmbPurchasesCustomer.removeAllItems(); // Clear combo box items
    }

    public void loadCustomers() {
        // Load customers from the database and display in table and combo box
        Map<Integer, Customer> customers = customerActions.listCustomers(customerActions.nameAdditionalValidation(administration.txtSearchCustomer.getText().trim()));
        model = (DefaultTableModel) administration.customersTable.getModel();
        Object[] row = new Object[5]; // Array to hold customer data for a row
        for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
            Customer value = entry.getValue();
            row[0] = value.getCustomerID(); // Customer ID
            row[1] = value.getCustomerName(); // Customer Name
            row[2] = value.getCustomerAddress(); // Customer Address
            row[3] = value.getCustomerTelephone(); // Customer Telephone
            row[4] = value.getCustomerEmail(); // Customer Email
            model.addRow(row); // Add row to the table model
            administration.cmbPurchasesCustomer.addItem(row[1] + " (" + row[0] + ")"); // Add customer to combo box
        }
    }

    private void deleteCustomer() {
        // Delete a customer and refresh data
        try {
            if (customerActions.deleteCustomer(Integer.valueOf(administration.txtCustomerID.getText()))) {
                refreshCustomerData(); // Refresh customer data in the view
                JOptionPane.showMessageDialog(null, "Customer successfully removed."); // Success message
                administration.btnCustomerRegister.setEnabled(true); // Enable the register button
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private void registerCustomer() {
        // Register a new customer and refresh data
        try {
            // Validate email format
            if (!customerActions.emailValidationOne(administration.txtCustomerMail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if email is unique
            else if (!customerActions.isEmailUnique(administration.txtCustomerMail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate telephone format
            else if (!customerActions.isNumericString(administration.txtCustomerTelephone.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Invalid telephone format.");
            } // Check if telephone is unique
            else if (!customerActions.isTelephoneUnique(administration.txtCustomerTelephone.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Telephone already in use.");
            } // Add customer if all validations pass
            else {
                customerActions.addCustomer(customerActions.nameAdditionalValidation(administration.txtCustomerName.getText().trim()),
                        administration.txtCustomerMail.getText().trim().toLowerCase(),
                        administration.txtCustomerAddress.getText().trim(),
                        administration.txtCustomerTelephone.getText().trim());
                refreshCustomerData(); // Refresh customer data in the view
                JOptionPane.showMessageDialog(null, "Customer registered successfully."); // Success message
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private void modifyCustomer() {
        // Modify an existing customer and refresh data
        try {
            String currentEmail = administration.customersTable.getValueAt(administration.customersTable.getSelectedRow(), 4).toString(); // Get current email
            String currentTelephone = administration.customersTable.getValueAt(administration.customersTable.getSelectedRow(), 3).toString(); // Get current telephone
            // Validate email format
            if (!customerActions.emailValidationOne(administration.txtCustomerMail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if email is unique
            else if (!customerActions.isEmailUnique(administration.txtCustomerMail.getText().trim().toLowerCase())
                    && !administration.txtCustomerMail.getText().trim().equalsIgnoreCase(currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate telephone format
            else if (!customerActions.isNumericString(administration.txtCustomerTelephone.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Invalid telephone format.");
            } // Check if telephone is unique
            else if (!customerActions.isTelephoneUnique(administration.txtCustomerTelephone.getText().trim())
                    && !administration.txtCustomerTelephone.getText().trim().equalsIgnoreCase(currentTelephone)) {
                JOptionPane.showMessageDialog(null, "Telephone already in use.");
            } // Modify customer if all validations pass
            else if (customerActions.modifyCustomer(Integer.valueOf(administration.txtCustomerID.getText()),
                    customerActions.nameAdditionalValidation(administration.txtCustomerName.getText().trim()),
                    administration.txtCustomerMail.getText().trim().toLowerCase(),
                    administration.txtCustomerAddress.getText().trim(),
                    administration.txtCustomerTelephone.getText().trim())) {
                refreshCustomerData(); // Refresh customer data in the view
                JOptionPane.showMessageDialog(null, "Customer successfully modified."); // Success message
                administration.btnCustomerRegister.setEnabled(true); // Enable the register button
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private void refreshCustomerData() {
        // Refresh customer table and fields
        cleanTableAndCmb(); // Clear the table and combo box
        cleanFields(); // Clear the input fields
        loadCustomers(); // Load customers again
    }

    private void handleException(Exception ex, String message) {
        // Log the exception and show an error message
        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions
        Object source = e.getSource();
        if (source == administration.btnCustomerRegister) {
            // Check if fields are clean before registering a new customer
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                registerCustomer(); // Proceed to register the customer
            }
        } else if (source == administration.btnCustomerModify) {
            // Ensure a row is selected before modifying
            int row = administration.customersTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the customer's row."); // Error message for no selection
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                modifyCustomer(); // Proceed to modify the customer
            }
        } else if (source == administration.btnCustomerDelete) {
            // Ensure a row is selected before deleting
            int row = administration.customersTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the customer's row."); // Error message for no selection
            } else {
                deleteCustomer(); // Proceed to delete the customer
            }
        } else if (source == administration.btnCustomerCancel) {
            refreshCustomerData(); // Refresh customer data on cancel
            administration.btnCustomerRegister.setEnabled(true); // Enable the register button
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle table row click to populate customer fields
        if (e.getSource() == administration.customersTable) {
            int row = administration.customersTable.rowAtPoint(e.getPoint());
            // Populate fields with selected customer data
            administration.txtCustomerID.setText(administration.customersTable.getValueAt(row, 0).toString());
            administration.txtCustomerName.setText(administration.customersTable.getValueAt(row, 1).toString());
            administration.txtCustomerAddress.setText(administration.customersTable.getValueAt(row, 2).toString());
            administration.txtCustomerTelephone.setText(administration.customersTable.getValueAt(row, 3).toString());
            administration.txtCustomerMail.setText(administration.customersTable.getValueAt(row, 4).toString());
            administration.btnCustomerRegister.setEnabled(false); // Disable register button when editing
        } else if (e.getSource() == administration.lblCustomers) {
            // Switch to the customers tab
            administration.jTabbedPanePanels.setSelectedIndex(2);
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
        // Handle key release event in the search field
        if (e.getSource() == administration.txtSearchCustomer) {
            cleanTableAndCmb(); // Clear table and combo box before loading new results
            loadCustomers(); // Load customers based on search input
        }
    }
}
