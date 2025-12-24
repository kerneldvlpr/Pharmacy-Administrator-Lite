package Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PurchaseActions {

    // List to hold purchases that are currently in process
    private ArrayList<Purchase> purchasesInProcess = new ArrayList<>();

    // List to hold completed purchases
    private ArrayList<Purchase> purchasesCompleted = new ArrayList<>();

    // Date format for purchase date and time
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Current date to be used for purchases
    private Date date = new Date();

    // Counter for purchase IDs
    private Integer purchaseCount = 0;

    // Singleton instance of PurchaseActions
    private static PurchaseActions instance;

    // Private constructor to prevent instantiation
    private PurchaseActions() {
    }

    // Singleton instance getter
    public static synchronized PurchaseActions getInstance() {
        if (instance == null) {
            instance = new PurchaseActions();
        }
        return instance;
    }

    // List all purchases that are currently in process
    public ArrayList<Purchase> listPurchasesInProcess() {
        return purchasesInProcess;
    }

    // List all completed purchases
    public ArrayList<Purchase> listPurchasesCompleted() {
        return purchasesCompleted;
    }

    // Check if the input string is numeric
    public boolean isNumericString(String input) {
        String numericRegex = "^[0-9]+$"; // Regex pattern for numeric strings
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input matches the pattern
    }

    // Create a new Purchase object
    private Purchase createPurchase(Integer purchaseID, Integer purchaseProductID, String purchaseProduct,
            String purchaseSupplier, Integer purchaseQuantityToBuy,
            String purchaseCustomer, double purchasePrice, String purchaseDateTime) {
        return new Purchase(purchaseID, purchaseProductID, purchaseProduct, purchaseSupplier, purchaseQuantityToBuy, purchaseCustomer, purchasePrice, purchaseDateTime);
    }

    // Add a new purchase to the list of purchases in process
    public void addPurchase(Integer purchaseProductID, String purchaseProduct,
            String purchaseSupplier, Integer purchaseQuantityToBuy,
            String purchaseCustomer, double purchasePrice) {
        // Create a new Purchase object with the provided details and current date
        Purchase purchase = createPurchase(purchaseCount, purchaseProductID, purchaseProduct, purchaseSupplier, purchaseQuantityToBuy, purchaseCustomer, purchasePrice, dateFormat.format(date));
        // Add the new purchase to the list of purchases in process
        purchasesInProcess.add(purchase);
        // Increment the purchase count
        purchaseCount++;
    }

    // Finalize the purchase, moving all purchases in process to completed
    public ArrayList<Purchase> finaliseBuy() {
        if (!purchasesInProcess.isEmpty()) {
            // Move all purchases in process to the list of completed purchases
            purchasesCompleted.addAll(purchasesInProcess);
            // Clear the list of purchases in process
            purchasesInProcess.clear();
            return purchasesCompleted; // Return the list of completed purchases
        }
        return null; // Return null if there are no purchases in process
    }

    // Delete a purchase in process by its ID
    public boolean deletePurchaseInProcess(Integer purchaseID) {
        for (int i = 0; i < purchasesInProcess.size(); i++) {
            if (purchasesInProcess.get(i).getPurchaseID().equals(purchaseID)) {
                // Remove the purchase from the list if the ID matches
                purchasesInProcess.remove(i);
                return true; // Return true if the purchase was removed
            }
        }
        return false; // Return false if no matching purchase was found
    }
}
