package Models;

public class Category {

    // Unique identifier for the category
    private Integer categoryID;
    // Name of the category
    private String categoryName;

    // Default constructor
    public Category() {
    }

    // Parameterized constructor to initialize category with ID and name
    public Category(Integer categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    // Get the category name
    public String getCategoryName() {
        return categoryName;
    }

    // Set the category name
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Get the category ID
    public Integer getCategoryID() {
        return categoryID;
    }

    // Set the category ID
    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
}
