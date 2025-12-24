package Controllers;

import Models.Employee;
import Models.EmployeeActions;
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

/**
 * The EmployeeController class handles the interactions between the
 * Administration view, the EmployeeActions model, and user actions such as
 * button clicks, table interactions, and key presses. Implements
 * ActionListener, MouseListener, and KeyListener to manage different events.
 */
public class EmployeeController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // Reference to the administration view
    private final EmployeeActions employeeActions; // Model class handling employee-related operations
    private DefaultTableModel model; // Table model for displaying employees in the table

    /**
     * Constructor initializes the controller and sets up event listeners.
     *
     * @param administration Reference to the Administration view
     * @param employeeActions Reference to the EmployeeActions model
     */
    public EmployeeController(Administration administration, EmployeeActions employeeActions) {
        this.administration = administration;
        this.employeeActions = employeeActions;
        addActionListeners(); // Attach action listeners to buttons
        addMouseListeners(); // Attach mouse listeners to table and labels
        addKeyListeners(); // Attach key listeners to the search field
    }

    /**
     * Adds action listeners to the buttons in the administration view.
     */
    private void addActionListeners() {
        administration.btnEmployeeRegister.addActionListener(this);
        administration.btnEmployeeUpdate.addActionListener(this);
        administration.btnEmployeeDelete.addActionListener(this);
        administration.btnEmployeeCancel.addActionListener(this);
    }

    /**
     * Adds mouse listeners to relevant components in the administration view.
     */
    private void addMouseListeners() {
        administration.lblEmployees.addMouseListener(this);
        administration.employeesTable.addMouseListener(this);
    }

    /**
     * Adds key listeners to the search field in the administration view.
     */
    private void addKeyListeners() {
        administration.txtSearchEmployee.addKeyListener(this);
    }

    /**
     * Modifies the selected employee's information in the database and updates
     * the view.
     */
    private void modifyEmployee() {
        try {
            // Retrieve the current email and username of the selected employee
            String currentEmail = administration.employeesTable.getValueAt(administration.employeesTable.getSelectedRow(), 4).toString();
            String currentUser = administration.employeesTable.getValueAt(administration.employeesTable.getSelectedRow(), 2).toString();

            // Validate email format
            if (!employeeActions.emailValidation(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if the email is unique
            else if (!employeeActions.isEmailUnique(administration.txtEmployeeEmail.getText().trim().toLowerCase())
                    && !administration.txtEmployeeEmail.getText().trim().equalsIgnoreCase(currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate username
            else if (!employeeActions.userValidation(administration.txtEmployeeUser.getText().trim())
                    && !administration.txtEmployeeUser.getText().trim().equalsIgnoreCase(currentUser)) {
                JOptionPane.showMessageDialog(null, "Username already in use.");
            } // Proceed with modifying the employee if all validations pass
            else if (employeeActions.modifyEmployee(Integer.valueOf(administration.txtEmployeeID.getText()),
                    administration.txtEmployeeUser.getText().trim(),
                    employeeActions.searchEmployeeByID(Integer.valueOf(administration.txtEmployeeID.getText())).getEmployeePassword(),
                    employeeActions.nameAdditionalValidation(administration.txtEmployeeName.getText()),
                    administration.txtEmployeeAddress.getText().trim(),
                    administration.txtEmployeeEmail.getText().trim().toLowerCase(),
                    administration.cmbEmployeeRol.getSelectedItem().toString())) {
                refreshEmployeeData(); // Refresh the table and input fields
                JOptionPane.showMessageDialog(null, "Employee successfully modified.");
                administration.btnEmployeeRegister.setEnabled(true); // Enable register button after modifying
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process.");
        }
    }

    /**
     * Deletes the selected employee from the database and updates the view.
     */
    private void deleteEmployee() {
        try {
            // Attempt to delete the employee
            if (employeeActions.deleteEmployee(Integer.valueOf(administration.txtEmployeeID.getText()))) {
                refreshEmployeeData(); // Refresh the table and input fields
                JOptionPane.showMessageDialog(null, "Employee successfully removed from the staff.");
                administration.btnEmployeeRegister.setEnabled(true); // Enable register button after deletion
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process.");
        }
    }

    /**
     * Clears all rows from the employee table.
     */
    private void cleanTable() {
        model = (DefaultTableModel) administration.employeesTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove rows one by one
        }
    }

    /**
     * Clears all input fields in the administration view.
     */
    private void cleanFields() {
        administration.txtEmployeeAddress.setText("");
        administration.txtEmployeeEmail.setText("");
        administration.txtEmployeeID.setText("");
        administration.txtEmployeeName.setText("");
        administration.txtEmployeeUser.setText("");
    }

    /**
     * Loads employees from the database and displays them in the table.
     */
    public void loadEmployees() {
        if (isUserAuthorized()) { // Check if the current user is authorized
            Map<Integer, Employee> employees = employeeActions.listEmployees(employeeActions.nameAdditionalValidation(administration.txtSearchEmployee.getText().trim()));
            model = (DefaultTableModel) administration.employeesTable.getModel();
            Object[] row = new Object[7]; // Array to hold data for each row

            for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
                Employee value = entry.getValue();
                // Exclude "Owner" role and current logged-in employee from the table
                if (!value.getEmployeeRol().equals("Owner") && value.getEmployeeID() != employeeActions.getCurrentEmployee().getEmployeeID()) {
                    row[0] = value.getEmployeeID();
                    row[1] = value.getEmployeeName();
                    row[2] = value.getEmployeeUser();
                    row[3] = value.getEmployeeAddress();
                    row[4] = value.getEmployeeEmail();
                    row[5] = value.getEmployeeRol();
                    model.addRow(row); // Add row to the table
                }
            }
        }
    }

    /**
     * Checks if the current user has the required role to perform
     * employee-related actions.
     *
     * @return true if the user is authorized, false otherwise.
     */
    private boolean isUserAuthorized() {
        String role = employeeActions.getCurrentEmployee().getEmployeeRol();
        return role.equals("Administrator") || role.equals("Owner");
    }

    /**
     * Registers a new employee and updates the view.
     */
    private void registerEmployee() {
        try {
            // Validate email format
            if (!employeeActions.emailValidation(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if email is unique
            else if (!employeeActions.isEmailUnique(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate username
            else if (!employeeActions.userValidation(administration.txtEmployeeUser.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Username already in use.");
            } // Proceed to register the employee if all validations pass
            else {
                employeeActions.addEmployee(administration.txtEmployeeUser.getText().trim(),
                        "Welcome@" + administration.txtEmployeeUser.getText().trim(),
                        employeeActions.nameAdditionalValidation(administration.txtEmployeeName.getText().trim()),
                        administration.txtEmployeeAddress.getText().trim(),
                        administration.txtEmployeeEmail.getText().trim().toLowerCase(),
                        administration.cmbEmployeeRol.getSelectedItem().toString());
                refreshEmployeeData(); // Refresh the table and input fields
                JOptionPane.showMessageDialog(null, "Employee registered successfully.");
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process.");
        }
    }

    /**
     * Checks if any input field is empty.
     *
     * @return true if any field is blank, false otherwise.
     */
    private boolean areFieldsClean() {
        return administration.txtEmployeeAddress.getText().isBlank()
                || administration.txtEmployeeEmail.getText().isBlank()
                || administration.txtEmployeeName.getText().isBlank()
                || administration.txtEmployeeUser.getText().isBlank();
    }

    /**
     * Refreshes the employee data by clearing the table, clearing input fields,
     * and reloading employees.
     */
    private void refreshEmployeeData() {
        cleanTable();
        cleanFields();
        loadEmployees();
    }

    /**
     * Handles exceptions by logging them and showing an error message to the
     * user.
     *
     * @param ex The exception to handle
     * @param message The error message to display
     */
    private void handleException(Exception ex, String message) {
        Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Handles action events triggered by button clicks.
     *
     * @param e The action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnEmployeeRegister) {
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                registerEmployee();
            }
        } else if (source == administration.btnEmployeeUpdate) {
            int row = administration.employeesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the employee's row.");
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                modifyEmployee();
            }
        } else if (source == administration.btnEmployeeDelete) {
            int row = administration.employeesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the employee's row.");
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            } else {
                deleteEmployee();
            }
        } else if (source == administration.btnEmployeeCancel) {
            refreshEmployeeData();
            administration.btnEmployeeRegister.setEnabled(true);
        }
    }

    /**
     * Handles mouse click events, such as selecting a row in the table.
     *
     * @param e The mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.employeesTable) {
            int row = administration.employeesTable.rowAtPoint(e.getPoint());
            administration.txtEmployeeID.setText(administration.employeesTable.getValueAt(row, 0).toString());
            administration.txtEmployeeName.setText(administration.employeesTable.getValueAt(row, 1).toString());
            administration.txtEmployeeUser.setText(administration.employeesTable.getValueAt(row, 2).toString());
            administration.txtEmployeeAddress.setText(administration.employeesTable.getValueAt(row, 3).toString());
            administration.txtEmployeeEmail.setText(administration.employeesTable.getValueAt(row, 4).toString());
            administration.cmbEmployeeRol.setSelectedItem(administration.employeesTable.getValueAt(row, 5).toString());
            administration.btnEmployeeRegister.setEnabled(false);
        } else if (e.getSource() == administration.lblEmployees) {
            if (isUserAuthorized()) {
                administration.jTabbedPanePanels.setSelectedIndex(3);
            }
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

    /**
     * Handles key release events, such as updating the table when typing in the
     * search field.
     *
     * @param e The key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == administration.txtSearchEmployee) {
            cleanTable();
            loadEmployees();
        }
    }
}
