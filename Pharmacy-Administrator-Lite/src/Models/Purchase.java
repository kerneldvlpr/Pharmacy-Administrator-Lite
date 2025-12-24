package Models;

public class Purchase {

    private Integer purchaseID; // Unique identifier for the purchase
    private Integer purchaseProductID; // ID of the product being purchased
    private String purchaseProduct; // Name of the product being purchased (Combo Box)
    private String purchaseSupplier; // Supplier of the product (Combo Box)
    private Integer purchaseQuantityToBuy; // Quantity of the product to buy
    private String purchaseCustomer; // Customer associated with the purchase
    private double purchasePrice; // Price of the product for this purchase
    private String purchaseDateTime; // Date and time of the purchase

    // Default constructor
    public Purchase() {
    }

    // Parameterized constructor to initialize a Purchase object
    public Purchase(Integer purchaseID, Integer purchaseProductID, String purchaseProduct,
            String purchaseSupplier, Integer purchaseQuantityToBuy, String purchaseCustomer,
            double purchasePrice, String purchaseDateTime) {
        this.purchaseID = purchaseID;
        this.purchaseProductID = purchaseProductID;
        this.purchaseProduct = purchaseProduct;
        this.purchaseSupplier = purchaseSupplier;
        this.purchaseQuantityToBuy = purchaseQuantityToBuy;
        this.purchaseCustomer = purchaseCustomer;
        this.purchasePrice = purchasePrice;
        this.purchaseDateTime = purchaseDateTime;
    }

    // Get the purchase date and time
    public String getPurchaseDateTime() {
        return purchaseDateTime;
    }

    // Set the purchase date and time
    public void setPurchaseDateTime(String purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    // Get the purchase ID
    public Integer getPurchaseID() {
        return purchaseID;
    }

    // Set the purchase ID
    public void setPurchaseID(Integer purchaseID) {
        this.purchaseID = purchaseID;
    }

    // Get the product ID associated with the purchase
    public Integer getPurchaseProductID() {
        return purchaseProductID;
    }

    // Set the product ID associated with the purchase
    public void setPurchaseProductID(Integer purchaseProductID) {
        this.purchaseProductID = purchaseProductID;
    }

    // Get the product name associated with the purchase
    public String getPurchaseProduct() {
        return purchaseProduct;
    }

    // Set the product name associated with the purchase
    public void setPurchaseProduct(String purchaseProduct) {
        this.purchaseProduct = purchaseProduct;
    }

    // Get the supplier of the product
    public String getPurchaseSupplier() {
        return purchaseSupplier;
    }

    // Set the supplier of the product
    public void setPurchaseSupplier(String purchaseSupplier) {
        this.purchaseSupplier = purchaseSupplier;
    }

    // Get the quantity of the product to buy
    public Integer getPurchaseQuantityToBuy() {
        return purchaseQuantityToBuy;
    }

    // Set the quantity of the product to buy
    public void setPurchaseQuantityToBuy(Integer purchaseQuantityToBuy) {
        this.purchaseQuantityToBuy = purchaseQuantityToBuy;
    }

    // Get the customer associated with the purchase
    public String getPurchaseCustomer() {
        return purchaseCustomer;
    }

    // Set the customer associated with the purchase
    public void setPurchaseCustomer(String purchaseCustomer) {
        this.purchaseCustomer = purchaseCustomer;
    }

    // Get the price of the product for this purchase
    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Set the price of the product for this purchase
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
