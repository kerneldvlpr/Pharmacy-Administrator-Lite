package Models;

public class Product {

    // Attributes of the Product class
    private Integer productID; // Unique identifier for the product
    private String productName; // Name of the product
    private String productDescription; // Description of the product
    private double productUnitPrice; // Unit price of the product
    private Integer productQuantity; // Quantity of the product available
    private String productCategoryName; // Category name to which the product belongs

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(Integer productID, String productName, String productDescription,
            double productUnitPrice, Integer productQuantity, String productCategoryName) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUnitPrice = productUnitPrice;
        this.productQuantity = productQuantity;
        this.productCategoryName = productCategoryName;
    }

    // Get the product ID
    public Integer getProductID() {
        return productID;
    }

    // Set the product ID
    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    // Get the product name
    public String getProductName() {
        return productName;
    }

    // Set the product name
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Get the product description
    public String getProductDescription() {
        return productDescription;
    }

    // Set the product description
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    // Get the product unit price
    public double getProductUnitPrice() {
        return productUnitPrice;
    }

    // Set the product unit price
    public void setProductUnitPrice(double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    // Get the product quantity
    public Integer getProductQuantity() {
        return productQuantity;
    }

    // Set the product quantity
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    // Get the product category name
    public String getProductCategoryName() {
        return productCategoryName;
    }

    // Set the product category name
    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

}
