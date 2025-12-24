package Models;

public class Supplier {

    // Attributes for Supplier class
    private String supplierName;
    private String supplierEmail;
    private String supplierAddress;
    private Integer supplierID;
    private String supplierTelephone;
    private String supplierCity;
    private String supplierDescription;

    // Default constructor
    public Supplier() {
    }

    // Parameterized constructor
    public Supplier(String supplierName, String supplierEmail, String supplierAddress, Integer supplierID,
            String supplierTelephone, String supplierCity, String supplierDescription) {
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.supplierAddress = supplierAddress;
        this.supplierID = supplierID;
        this.supplierTelephone = supplierTelephone;
        this.supplierCity = supplierCity;
        this.supplierDescription = supplierDescription;
    }

    // Getter for supplierDescription
    public String getSupplierDescription() {
        return supplierDescription;
    }

    // Setter for supplierDescription
    public void setSupplierDescription(String supplierDescription) {
        this.supplierDescription = supplierDescription;
    }

    // Getter for supplierName
    public String getSupplierName() {
        return supplierName;
    }

    // Setter for supplierName
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter for supplierEmail
    public String getSupplierEmail() {
        return supplierEmail;
    }

    // Setter for supplierEmail
    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    // Getter for supplierAddress
    public String getSupplierAddress() {
        return supplierAddress;
    }

    // Setter for supplierAddress
    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    // Getter for supplierID
    public Integer getSupplierID() {
        return supplierID;
    }

    // Setter for supplierID
    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    // Getter for supplierTelephone
    public String getSupplierTelephone() {
        return supplierTelephone;
    }

    // Setter for supplierTelephone
    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    // Getter for supplierCity
    public String getSupplierCity() {
        return supplierCity;
    }

    // Setter for supplierCity
    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }
}
