package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerActions {

    // Map to store customer data
    private Map<Integer, Customer> customers = new HashMap<>();
    // Counter to assign unique IDs to customers
    private Integer customerCount = 0;

    // Singleton instance of CustomerActions
    private static CustomerActions instance;

    // Private constructor for singleton pattern
    private CustomerActions() {
    }

    // Singleton instance getter
    public static synchronized CustomerActions getInstance() {
        if (instance == null) {
            instance = new CustomerActions();
        }
        return instance;
    }

    // Create a new Customer object
    private Customer createCustomer(Integer customerID, String customerName, String customerEmail, String customerAddress, String customerTelephone) {
        return new Customer(customerID, customerName, customerEmail, customerAddress, customerTelephone);
    }

    // Validate email format
    public boolean emailValidationOne(String customerEmail) {
        // Regular expression for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(customerEmail);
        return matcher.matches(); // Returns true if the email format is valid
    }

    // Check if email is unique
    public boolean isEmailUnique(String customerEmail) {
        return customers.values().stream()
                .noneMatch(customer -> customer.getCustomerEmail().equals(customerEmail));
    }

    // Check if the input string is numeric
    public boolean isNumericString(String input) {
        // Regular expression for numeric validation
        String numericRegex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Returns true if the input string is numeric
    }

    // Formats the name: capitalizes the first letter, rest in lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) {
            return value; // Return as is if null or empty
        } else {
            // Capitalize the first letter and lowercase the rest
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // Check if telephone is unique
    public boolean isTelephoneUnique(String customerTelephone) {
        return customers.values().stream()
                .noneMatch(customer -> customer.getCustomerTelephone().equals(customerTelephone));
    }

    // Add a new customer
    public void addCustomer(String customerName, String customerEmail, String customerAddress, String customerTelephone) {
        // Create a new customer and add to the map
        Customer customer = createCustomer(customerCount, customerName, customerEmail, customerAddress, customerTelephone);
        customers.put(customerCount, customer);
        customerCount++; // Increment the customer count
    }

    // Modify an existing customer
    public boolean modifyCustomer(Integer customerID, String customerName, String customerEmail, String customerAddress, String customerTelephone) {
        if (customers.containsKey(customerID)) {
            // Create a new customer object with the same ID but updated details
            Customer customer = createCustomer(customerID, customerName, customerEmail, customerAddress, customerTelephone);
            customers.replace(customerID, customer);
            return true; // Return true if customer was found and updated
        }
        return false; // Return false if customer ID was not found
    }

    // Delete an existing customer
    public boolean deleteCustomer(Integer customerID) {
        if (customers.containsKey(customerID)) {
            customers.remove(customerID);
            return true; // Return true if customer was found and deleted
        }
        return false; // Return false if customer ID was not found
    }

    // List customers, optionally filtering by name
    public Map<Integer, Customer> listCustomers(String customerName) {
        if (customerName.isBlank()) {
            return customers; // Return all customers if no filter is provided
        } else {
            // Create a filtered map based on the customer name
            Map<Integer, Customer> customersBased = new HashMap<>();
            for (Customer customer : customers.values()) {
                if (customer.getCustomerName().equalsIgnoreCase(customerName)) {
                    customersBased.put(customer.getCustomerID(), customer);
                }
            }
            return customersBased.isEmpty() ? customers : customersBased; // Return filtered map or all customers if none match
        }
    }
}
