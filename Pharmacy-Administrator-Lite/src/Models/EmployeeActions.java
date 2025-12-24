package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeActions {

    // Singleton instance of EmployeeActions
    private static EmployeeActions instance;

    // Map to store employees with their ID as the key
    private Map<Integer, Employee> employees = new HashMap<>();

    // Variable to store the current logged-in employee
    private Employee currentEmployee;

    // Counter to keep track of the total number of users
    private Integer userCount = 0;

    // Private constructor to prevent instantiation
    private EmployeeActions() {
    }

    // Singleton instance getter method. Ensures that only one instance of this class exists.
    public static synchronized EmployeeActions getInstance() {
        if (instance == null) {
            instance = new EmployeeActions();
        }
        return instance;
    }

    // Returns the current logged-in employee
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    // Returns the total number of users (employees) currently stored
    public Integer getUserCount() {
        return userCount;
    }

    // Creates a new Employee object with the provided details
    private Employee createEmployee(Integer employeeID, String employeeUser, String employeePassword,
            String employeeName, String employeeAddress, String employeeEmail, String employeeRole) {
        return new Employee(employeeID, employeeUser, employeePassword,
                employeeName, employeeAddress, employeeEmail, employeeRole);
    }

    // Validates and formats a name by capitalizing the first letter and making the rest lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) { // Check if the value is null or empty
            return value; // Return the input as-is if it's blank
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // Lists all employees, or filters by name if a name is provided
    public Map<Integer, Employee> listEmployees(String employeeName) {
        if (employeeName.isBlank()) {
            return employees; // Return all employees if no filter is provided
        } else {
            Map<Integer, Employee> filteredEmployees = new HashMap<>();
            for (Employee employee : employees.values()) {
                if (employee.getEmployeeName().equalsIgnoreCase(employeeName)) { // Case-insensitive name matching
                    filteredEmployees.put(employee.getEmployeeID(), employee);
                }
            }
            // Return filtered list if not empty, otherwise return all employees
            return filteredEmployees.isEmpty() ? employees : filteredEmployees;
        }
    }

    // Searches for an employee by their unique ID
    public Employee searchEmployeeByID(Integer employeeID) {
        for (Employee employee : employees.values()) {
            if (employee.getEmployeeID().equals(employeeID)) { // Compare the ID with each employee's ID
                return employee; // Return the matching employee
            }
        }
        return null; // Return null if no employee matches the given ID
    }

    // Validates the format of an email address using a regular expression
    public boolean emailValidation(String employeeEmail) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Regex pattern for a valid email
        Pattern pattern = Pattern.compile(emailRegex); // Compile the pattern
        Matcher matcher = pattern.matcher(employeeEmail); // Match the email against the pattern
        return matcher.matches(); // Return true if email matches the regex
    }

    // Checks if an email is unique among all employees
    public boolean isEmailUnique(String employeeEmail) {
        return employees.values().stream()
                .noneMatch(employee -> employee.getEmployeeEmail().equals(employeeEmail)); // Ensure no employee has the same email
    }

    // Checks if a username is unique among all employees
    public boolean userValidation(String employeeUser) {
        return employees.values().stream()
                .noneMatch(employee -> employee.getEmployeeUser().equals(employeeUser)); // Ensure no employee has the same username
    }

    // Adds a new employee to the system
    public void addEmployee(String employeeUser, String employeePassword, String employeeName,
            String employeeAddress, String employeeEmail, String employeeRole) {
        Employee employee = createEmployee(userCount, employeeUser, employeePassword,
                employeeName, employeeAddress, employeeEmail, employeeRole); // Create new Employee object
        employees.put(userCount, employee); // Store the employee in the map with their ID
        userCount++; // Increment the user count
    }

    // Modifies the details of an existing employee
    public boolean modifyEmployee(Integer employeeID, String employeeUser, String employeePassword,
            String employeeName, String employeeAddress, String employeeEmail, String employeeRole) {
        // Check if the employee exists and the username is either unique or belongs to the current employee
        if (employees.containsKey(employeeID) && (userValidation(employeeUser) || employees.get(employeeID).getEmployeeUser().equals(employeeUser))) {
            Employee employee = createEmployee(employeeID, employeeUser, employeePassword,
                    employeeName, employeeAddress, employeeEmail, employeeRole); // Create updated Employee object
            employees.replace(employeeID, employee); // Replace the existing employee with the updated one
            return true; // Return true indicating successful modification
        }
        return false; // Return false if validation fails
    }

    // Updates the password of an existing employee
    public boolean modifyPassword(Integer employeeID, String newPassword) {
        if (employees.containsKey(employeeID)) { // Check if the employee exists
            Employee employee = employees.get(employeeID); // Retrieve the employee
            employee.setEmployeePassword(newPassword); // Set the new password
            employees.replace(employeeID, employee); // Update the employee in the map
            return true; // Return true indicating success
        }
        return false; // Return false if the employee doesn't exist
    }

    // Deletes an employee by their ID
    public boolean deleteEmployee(Integer employeeID) {
        return employees.remove(employeeID) != null; // Remove the employee and return true if successful
    }

    // Authenticates an employee using their username and password
    public boolean employeeLogin(String employeeUser, String employeePassword) {
        for (Employee employee : employees.values()) {
            if (employee.getEmployeeUser().equals(employeeUser) && employee.getEmployeePassword().equals(employeePassword)) {
                currentEmployee = employee; // Set the current logged-in employee if credentials match
                return true; // Return true indicating successful login
            }
        }
        return false; // Return false if no match is found
    }
}
