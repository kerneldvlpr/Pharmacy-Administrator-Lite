package Models;

public class Customer {

    // Unique identifier for the customer
    private Integer customerID;
    // Name of the customer
    private String customerName;
    // Email of the customer
    private String customerEmail;
    // Address of the customer
    private String customerAddress;
    // Telephone number of the customer
    private String customerTelephone;

    // Default constructor
    public Customer() {
    }

    // Parameterized constructor
    public Customer(Integer customerID, String customerName, String customerEmail, String customerAddress, String customerTelephone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerTelephone = customerTelephone;
    }

    // Get the customer's telephone number
    public String getCustomerTelephone() {
        return customerTelephone;
    }

    // Set the customer's telephone number
    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    // Get the customer's ID
    public Integer getCustomerID() {
        return customerID;
    }

    // Set the customer's ID
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    // Get the customer's name
    public String getCustomerName() {
        return customerName;
    }

    // Set the customer's name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Get the customer's email
    public String getCustomerEmail() {
        return customerEmail;
    }

    // Set the customer's email
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // Get the customer's address
    public String getCustomerAddress() {
        return customerAddress;
    }

    // Set the customer's address
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

}
