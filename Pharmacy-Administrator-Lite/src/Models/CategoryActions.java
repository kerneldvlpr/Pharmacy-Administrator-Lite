package Models;

import java.util.HashMap;
import java.util.Map;

public class CategoryActions {

    // Map to store category objects with their IDs
    private Map<Integer, Category> categories = new HashMap<>();
    // Counter to keep track of the number of categories
    private Integer categoryCount = 0;

    // Singleton instance of CategoryActions
    private static CategoryActions instance;

    // Private constructor to prevent instantiation
    private CategoryActions() {
    }

    // Singleton instance getter
    public static synchronized CategoryActions getInstance() {
        // Initialize the instance if it's null
        if (instance == null) {
            instance = new CategoryActions();
        }
        return instance;
    }

    // Create a new Category object
    private Category createCategory(Integer categoryID, String categoryName) {
        // Return a new Category instance with the given ID and name
        return new Category(categoryID, categoryName);
    }

    // List all categories
    public Map<Integer, Category> listCategories() {
        // Return the map of categories
        return categories;
    }

    // Check if a category name is unique
    public boolean isCategoryNameUnique(String categoryName) {
        // Check if no category has the given name
        return categories.values().stream()
                .noneMatch(category -> category.getCategoryName().equals(categoryName));
    }

    // Validate and format category name
    public String nameAdditionalValidation(String value) {
        // If the value is blank, return it as is
        if (value.isBlank()) {
            return value;
        } else {
            // Capitalize the first letter and make the rest lowercase
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // Add a new category
    public void addCategory(String categoryName) {
        // Create a new category and add it to the map
        Category category = createCategory(categoryCount, categoryName);
        categories.put(categoryCount, category);
        // Increment the category count
        categoryCount++;
    }

    // Modify an existing category
    public boolean modifyCategory(Integer categoryID, String categoryName) {
        // Check if the category exists
        if (categories.containsKey(categoryID)) {
            // Create a new category and replace the existing one
            Category category = createCategory(categoryID, categoryName);
            categories.replace(categoryID, category);
            return true;
        }
        return false;
    }

    // Delete an existing category
    public boolean deleteCategory(Integer categoryID) {
        // Check if the category exists
        if (categories.containsKey(categoryID)) {
            // Remove the category from the map
            categories.remove(categoryID);
            return true;
        }
        return false;
    }
}
