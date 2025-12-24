package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductActions {

    // Map to store products with their IDs
    private Map<Integer, Product> products = new HashMap<>();
    private Integer productCount = 0; // Counter for product IDs

    private static ProductActions instance; // Singleton instance

    // Private constructor to prevent instantiation
    private ProductActions() {
    }

    // Singleton instance getter
    public static synchronized ProductActions getInstance() {
        if (instance == null) {
            instance = new ProductActions();
        }
        return instance;
    }

    // Create a new Product object
    private Product createProduct(Integer productID, String productName, String productDescription,
            double productUnitPrice, Integer productQuantity, String productCategoryName) {
        return new Product(productID, productName, productDescription, productUnitPrice, productQuantity, productCategoryName);
    }

    // List products, optionally filtering by name
    public Map<Integer, Product> listProducts(String productName) {
        if (productName.isBlank()) {
            // Return all products if the name is blank
            return products;
        } else {
            // Filter products by name
            HashMap<Integer, Product> filteredProducts = new HashMap<>();
            for (Product product : products.values()) {
                if (product.getProductName().equalsIgnoreCase(productName)) {
                    filteredProducts.put(product.getProductID(), product);
                }
            }
            // Return filtered products or all products if no match is found
            return filteredProducts.isEmpty() ? products : filteredProducts;
        }
    }

    // Search for a product by ID
    public Product searchProductByID(Integer productID) {
        return products.get(productID); // Retrieve product by ID
    }

    // Validate and format name: capitalize the first letter, rest in lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) {
            return value; // Return as is if null or empty
        } else {
            // Capitalize the first letter and lowercase the rest
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // Check if the input string is numeric
    public boolean isNumericString(String input) {
        String numericRegex = "^[0-9]+$"; // Regular expression for numeric string
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input is numeric
    }

    // Check if the input string is a valid double
    public boolean isDoubleString(String input) {
        String numericRegex = "^[0-9]+(\\.[0-9]+)?$"; // Regular expression for double string
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input is a valid double
    }

    // Add a new product
    public void addProduct(String productName, String productDescription, double productUnitPrice,
            Integer productQuantity, String productCategoryName) {
        Product product = createProduct(productCount, productName, productDescription,
                productUnitPrice, productQuantity, productCategoryName);
        products.put(productCount, product); // Add product to map
        productCount++; // Increment product count
    }

    // Update an existing product
    public boolean updateProduct(Integer productID, String productName, String productDescription,
            double productUnitPrice, Integer productQuantity, String productCategoryName) {
        if (products.containsKey(productID)) {
            // Create and update product if ID exists
            Product product = createProduct(productID, productName, productDescription,
                    productUnitPrice, productQuantity, productCategoryName);
            products.replace(productID, product); // Replace existing product
            return true;
        }
        return false; // Return false if product ID does not exist
    }

    // Delete an existing product
    public boolean deleteProduct(Integer productID) {
        if (products.containsKey(productID)) {
            products.remove(productID); // Remove product by ID
            return true;
        }
        return false; // Return false if product ID does not exist
    }
}
