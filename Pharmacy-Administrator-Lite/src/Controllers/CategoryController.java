package Controllers;

import Models.Category;
import Models.CategoryActions;
import Models.EmployeeActions;
import Views.Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoryController implements ActionListener, MouseListener {

    private final Administration administration; // The administration view instance
    private final CategoryActions categoryActions; // Handles actions related to categories
    private final EmployeeActions employeeActions; // Handles actions related to employees
    private DefaultTableModel model; // Table model for the categories table

    public CategoryController(Administration administration, CategoryActions categoryActions, EmployeeActions employeeActions) {
        this.administration = administration;
        this.categoryActions = categoryActions;
        this.employeeActions = employeeActions;
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
    }

    private void addActionListeners() {
        // Attach action listeners to category-related buttons
        administration.btnCategoryRegister.addActionListener(this);
        administration.btnCategoryModify.addActionListener(this);
        administration.btnCategoryDelete.addActionListener(this);
        administration.btnCategoryCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Attach mouse listeners to the category table and label
        administration.categoryTable.addMouseListener(this);
        administration.lblCategories.addMouseListener(this);
    }

    private boolean areFieldsClean() {
        // Check if the category name field is blank
        return administration.txtCategoryName.getText().isBlank();
    }

    private void cleanFields() {
        // Clear the category ID and name fields
        administration.txtCategoryID.setText("");
        administration.txtCategoryName.setText("");
    }

    private void cleanTableAndCmb() {
        // Clear the category table and combo box
        model = (DefaultTableModel) administration.categoryTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
        administration.cmbProductsCategories.removeAllItems(); // Clear combo box items
    }

    public void loadCategories() {
        // Load categories if the user is an Administrator or Owner
        if (isUserAuthorized()) {
            Map<Integer, Category> categories = categoryActions.listCategories(); // Get the list of categories
            model = (DefaultTableModel) administration.categoryTable.getModel();
            for (Category category : categories.values()) {
                Object[] row = {category.getCategoryID(), category.getCategoryName()}; // Create a row for the table
                model.addRow(row); // Add row to the table model
                administration.cmbProductsCategories.addItem(category.getCategoryName() + " (" + category.getCategoryID() + ")"); // Add category to combo box
            }
        }
    }

    private boolean isUserAuthorized() {
        // Check if the current employee has the required role (Administrator or Owner)
        String role = employeeActions.getCurrentEmployee().getEmployeeRol();
        return role.equals("Administrator") || role.equals("Owner");
    }

    private void registerCategory() {
        try {
            String categoryName = categoryActions.nameAdditionalValidation(administration.txtCategoryName.getText().trim()); // Validate category name
            if (!categoryActions.isCategoryNameUnique(categoryName)) {
                // Show error message if the category name is not unique
                JOptionPane.showMessageDialog(null, "Name already in use.");
            } else {
                categoryActions.addCategory(categoryName); // Add the new category
                refreshCategoryData(); // Refresh the category data in the view
                JOptionPane.showMessageDialog(null, "Category registered successfully."); // Success message
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle any exceptions
        }
    }

    private void modifyCategory() {
        try {
            String categoryName = categoryActions.nameAdditionalValidation(administration.txtCategoryName.getText().trim()); // Validate category name
            String currentCategoryName = administration.categoryTable.getValueAt(administration.categoryTable.getSelectedRow(), 1).toString(); // Get the currently selected category name
            if (!categoryActions.isCategoryNameUnique(categoryName) && !categoryName.equalsIgnoreCase(currentCategoryName)) {
                // Show error message if the category name is not unique and differs from the current name
                JOptionPane.showMessageDialog(null, "Name already in use.");
            } else {
                int categoryId = Integer.parseInt(administration.txtCategoryID.getText()); // Get the category ID
                categoryActions.modifyCategory(categoryId, categoryName); // Modify the category
                refreshCategoryData(); // Refresh the category data in the view
                administration.btnCategoryRegister.setEnabled(true); // Enable the register button
                JOptionPane.showMessageDialog(null, "Category successfully modified."); // Success message
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle any exceptions
        }
    }

    private void deleteCategory() {
        try {
            int categoryId = Integer.parseInt(administration.txtCategoryID.getText()); // Get the category ID
            if (categoryActions.deleteCategory(categoryId)) { // Attempt to delete the category
                refreshCategoryData(); // Refresh the category data in the view
                JOptionPane.showMessageDialog(null, "Category successfully removed."); // Success message
                administration.btnCategoryRegister.setEnabled(true); // Enable the register button
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle any exceptions
        }
    }

    private void refreshCategoryData() {
        // Refresh the category table and fields
        cleanTableAndCmb(); // Clear the table and combo box
        cleanFields(); // Clear the input fields
        loadCategories(); // Load the categories again
    }

    private void handleException(Exception ex, String message) {
        // Log the exception and show an error message
        Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnCategoryRegister) {
            // Check if fields are clean before registering a new category
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                registerCategory(); // Proceed to register the category
            }
        } else if (source == administration.btnCategoryModify) {
            // Ensure a row is selected before modifying
            if (administration.categoryTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select the category's row."); // Error message for no selection
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                modifyCategory(); // Proceed to modify the category
            }
        } else if (source == administration.btnCategoryDelete) {
            // Ensure a row is selected before deleting
            if (administration.categoryTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select the category's row."); // Error message for no selection
            } else {
                deleteCategory(); // Proceed to delete the category
            }
        } else if (source == administration.btnCategoryCancel) {
            refreshCategoryData(); // Refresh category data and re-enable register button
            administration.btnCategoryRegister.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click events on the category table
        if (e.getSource() == administration.categoryTable) {
            int row = administration.categoryTable.rowAtPoint(e.getPoint()); // Get the clicked row
            administration.txtCategoryID.setText(administration.categoryTable.getValueAt(row, 0).toString()); // Set category ID
            administration.txtCategoryName.setText(administration.categoryTable.getValueAt(row, 1).toString()); // Set category name
            administration.btnCategoryRegister.setEnabled(false); // Disable the register button
        } else if (e.getSource() == administration.lblCategories) {
            // Change the tab to categories if the user is authorized
            if (isUserAuthorized()) {
                administration.jTabbedPanePanels.setSelectedIndex(5);
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
}
