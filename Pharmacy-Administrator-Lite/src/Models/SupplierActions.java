package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierActions {

    // Map to store Supplier objects with their IDs as keys
    private Map<Integer, Supplier> suppliers = new HashMap<>();
    // Counter to keep track of the number of suppliers
    private Integer supplierCount = 0;

    // Singleton instance of SupplierActions
    private static SupplierActions instance;

    // Private constructor to prevent instantiation
    private SupplierActions() {
    }

    // Singleton instance getter
    public static synchronized SupplierActions getInstance() {
        if (instance == null) {
            instance = new SupplierActions();
        }
        return instance;
    }

    // Create a new Supplier object
    private Supplier createSupplier(String supplierName, String supplierEmail, String supplierAddress,
            Integer supplierID, String supplierTelephone, String supplierCity,
            String supplierDescription) {
        return new Supplier(supplierName, supplierEmail, supplierAddress, supplierID, supplierTelephone,
                supplierCity, supplierDescription);
    }

    // Validate and format name: capitalize the first letter, rest in lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) {
            return value; // Return as is if the string is null or empty
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // List suppliers, optionally filtering by name
    public Map<Integer, Supplier> listSuppliers(String supplierName) {
        if (supplierName.isBlank()) {
            return suppliers; // Return all suppliers if no filter is provided
        } else {
            Map<Integer, Supplier> filteredSuppliers = new HashMap<>();
            for (Supplier supplier : suppliers.values()) {
                if (supplier.getSupplierName().equalsIgnoreCase(supplierName)) {
                    filteredSuppliers.put(supplier.getSupplierID(), supplier);
                }
            }
            return filteredSuppliers.isEmpty() ? suppliers : filteredSuppliers;
        }
    }

    // Validate email format
    public boolean emailValidationOne(String supplierEmail) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(supplierEmail);
        return matcher.matches();
    }

    // Check if email is unique
    public boolean isEmailUnique(String supplierEmail) {
        return suppliers.values().stream()
                .noneMatch(supplier -> supplier.getSupplierEmail().equals(supplierEmail));
    }

    // Check if telephone is unique
    public boolean isTelephoneUnique(String supplierTelephone) {
        return suppliers.values().stream()
                .noneMatch(supplier -> supplier.getSupplierTelephone().equals(supplierTelephone));
    }

    // Check if the input string is numeric
    public boolean isNumericString(String input) {
        String numericRegex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    // Add a new supplier
    public void addSupplier(String supplierName, String supplierEmail, String supplierAddress,
            String supplierTelephone, String supplierCity, String supplierDescription) {
        Supplier supplier = createSupplier(supplierName, supplierEmail, supplierAddress, supplierCount,
                supplierTelephone, supplierCity, supplierDescription);
        suppliers.put(supplierCount, supplier); // Add supplier to the map
        supplierCount++; // Increment the supplier count
    }

    // Modify an existing supplier
    public boolean modifySupplier(String supplierName, String supplierEmail, String supplierAddress,
            Integer supplierID, String supplierTelephone, String supplierCity,
            String supplierDescription) {
        if (suppliers.containsKey(supplierID)) {
            Supplier supplier = createSupplier(supplierName, supplierEmail, supplierAddress, supplierID,
                    supplierTelephone, supplierCity, supplierDescription);
            suppliers.replace(supplierID, supplier); // Replace the existing supplier
            return true;
        }
        return false;
    }

    // Delete an existing supplier
    public boolean deleteSupplier(Integer supplierID) {
        if (suppliers.containsKey(supplierID)) {
            suppliers.remove(supplierID); // Remove supplier from the map
            return true;
        }
        return false;
    }
}
