# Pharmacy-Administrator-Lite

Official repository of one of my first Java projects, this is Pharmacy Administrator Lite

## Prerequisites

Before running the application, make sure you have:

- Java Runtime Environment (JRE) installed on your computer
  - To check if Java is installed, open a terminal/command prompt and type: `java -version`
  - If Java is not installed, download and install it from the official [Java website](https://www.java.com/es/download/manual.jsp)

## Installation

1. Download the `.jar` file, located at: `Pharmacy Administrator Lite/dist/` in this repository.
2. Save it to a location on your computer where you have read and write permissions

## Running the Application

### Method 1: Double-click (Graphical Interface)

1. Navigate to the folder where you saved the `.jar` file
2. Double-click the file named `filename.jar`
   - If this doesn't work, proceed to Method 2 or Method 3

### Method 2: Command Line (Windows)

1. Open Command Prompt (cmd)
   - Press `Windows + R`
   - Type `cmd` and press Enter
2. Navigate to the folder containing the `.jar` file:
   ```sh
   cd path\to\your\jar\file
   ```
3. Run the application:
   ```sh
   java -jar filename.jar
   ```
   - Replace `filename.jar` with the actual name of the file

### Method 3: Terminal (macOS/Linux)

1. Open Terminal
2. Navigate to the folder containing the `.jar` file:
   ```sh
   cd path/to/your/jar/file
   ```
3. Run the application:
   ```sh
   java -jar filename.jar
   ```
   - Replace `filename.jar` with the actual name of the file

## Troubleshooting

If you encounter any issues:

1. Verify Java installation:

   ```sh
   java -version
   ```

   - If this command fails, reinstall Java

2. Check file permissions:

   - Windows: Right-click the `.jar` file → Properties → Make sure it's not blocked
   - Linux/macOS: Make the file executable:
     ```sh
     chmod +x filename.jar
     ```

3. Try running with console output:
   ```sh
   java -jar filename.jar -console
   ```
   This will show any error messages that might help identify the problem.

## Support

If you continue experiencing issues:

- Check the Issues section of this repository
- Create a new Issue with details about the error and your system configuration

## System Requirements

- Java Runtime Environment 8 or higher
- Operating System: Windows 7/10/11, macOS 10.14+, or Linux
- RAM: Minimum 2GB (4GB recommended)
- Storage: 500MB free space

## About the Application

This desktop application, developed using the Java programming language, serves as a tool to assist in the management of pharmacies. It showcases my current programming skills as of July 15, 2024. The application was created with dedication and love to demonstrate my capabilities up to this point.

### Key Features

- **Pharmacy Management:** The application provides basic functionalities to help manage various aspects of a pharmacy.
- **Data Structures:** The application utilizes Maps and ArrayLists for efficient data handling and storage within the program.

### Current Limitations

At present, the application does not include a MySQL database for persistent data storage. Consequently, all data entered during a session will be lost once the application is closed, reverting to its initial state upon the next launch. This limitation highlights the need for future integration of a database to ensure data persistence.

### Purpose

The primary objective of this application is to exhibit my proficiency in Java programming and my ability to implement essential features using core data structures. While the application serves a practical purpose, it is also a testament to my learning journey and the skills I have developed so far.

I hope this project demonstrates my passion for software development and my commitment to continuous improvement in the field.

# User Guide for Pharmacy Management Application

## Roles and Permissions

### Owner and Administrators

- **Owner**: Full access to all functions.
- **Administrators**: Same permissions as the owner, designated by the owner.

### Auxiliaries

- Limited access to functions related to:
  - Product purchases or inquiries.
  - Queries regarding suppliers, customers, and reports.

## Registration

To start using the application, you need to register. The registration button will be disabled after you register, so ensure you choose a username, password, and other details carefully. Upon registration, you will assume the role of the Owner.

### Steps to Register:

1. Navigate to the login window (the one with the image of a vase).
2. Click the "Sign up" button.
3. In the registration window (the one with the image of a building), fill in your details.
4. Read the terms and conditions (recommended).
5. Press the "Register" button.

## Logging In

### Steps to Log In:

1. Enter your username and password in the login window.
2. Press "Start."

## Navigating the Application

Upon logging in, you will be redirected to the administration panel.

### Navigation:

- A menu on the left side contains various sections.
- To navigate, hover over a section label and click to be redirected to the corresponding panel.

## Cancelling Actions

Each section has a "Cancel" button. Press it to stop the current action and clear the fields.

## Employee Management

### Registering an Employee:

1. Go to the employee panel in the Administration window.
2. Click on the employee section in the left menu.
3. Fill in the employee's details and select their role (Administrator or Auxiliary).
4. Press the "Register" button.

#### Important Notice: Access Credential Management

As part of the new employee registration process, the system will automatically generate a temporary password upon completion of registration. This password will follow the following format:

`Welcome@UserName`.

For example, for a user registered as Carlos123, the temporary password assigned will be:
`Welcome@Carlos123`

##### Mandatory Security Procedure

For institutional security reasons, it is mandatory to follow these steps during your first login:

1. Login to the system using your temporary credentials.
2. Go to the account settings panel
3. Immediately change the temporary password to a new secure password.

##### Security Considerations

The security of your account is the personal responsibility of each user. Please:
- Store your new password in a secure location.
- Do not share your credentials with third parties under any circumstances.
- Use a strong password that meets the established security requirements.


### Modifying an Employee:

1. In the employee panel, select the employee you wish to modify.
2. Update the fields with the new details.
3. Press the "Modify" button.

### Deleting an Employee:

1. In the employee panel, select the employee you wish to delete.
2. Press the "Delete" button.

## Customer Management

### Registering a Customer:

1. Go to the customer panel in the Administration window.
2. Click on the customer section in the left menu.
3. Fill in the customer's details.
4. Press the "Register" button.

### Modifying a Customer:

1. In the customer panel, select the customer you wish to modify.
2. Update the fields with the new details.
3. Press the "Modify" button.

### Deleting a Customer:

1. In the customer panel, select the customer you wish to delete.
2. Press the "Delete" button.

## Supplier Management

### Registering a Supplier:

1. Go to the supplier panel in the Administration window.
2. Click on the supplier section in the left menu.
3. Fill in the supplier's details.
4. Press the "Register" button.

### Modifying a Supplier:

1. In the supplier panel, select the supplier you wish to modify.
2. Update the fields with the new details.
3. Press the "Modify" button.

### Deleting a Supplier:

1. In the supplier panel, select the supplier you wish to delete.
2. Press the "Delete" button.

## Category Management

### Registering a Category:

1. Go to the category panel in the Administration window.
2. Click on the category section in the left menu.
3. Enter the category name and other details.
4. Press the "Register" button.

### Modifying a Category:

1. In the category panel, select the category you wish to modify.
2. Update the fields with the new details.
3. Press the "Modify" button.

### Deleting a Category:

1. In the category panel, select the category you wish to delete.
2. Press the "Delete" button.

## Product Management

### Registering a Product:

1. Ensure you have at least one category registered.
2. Go to the product panel.
3. Fill in the product details and select its category.
4. Press the "Register" button.

### Modifying a Product:

1. In the product panel, select the product you wish to modify.
2. Update the fields with the new details.
3. Press the "Modify" button.

### Deleting a Product:

1. In the product panel, select the product you wish to delete.
2. Press the "Delete" button.

## Purchases

### Registering Purchases in Process:

1. Ensure you have at least one product, customer, and supplier registered.
2. Go to the purchase panel.
3. Select the product, customer, and supplier, then fill in the "Quantity" field.
4. Click "Add" to finalize each purchase.
5. Click "Buy" to complete and record the purchase in the Reports section.

### Deleting a Purchase in Process:

1. Select the purchase in process you wish to delete.
2. Press the "Delete" button.
