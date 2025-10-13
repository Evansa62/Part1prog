
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
public class Login {
    
    // --- Fields to store user details ---
    private final String username;
    private final String password;
    private final String cellNumber;
    private final String firstName;
    private final String lastName;

    // --- Constructor ---
    // Initializes the Login object with the user's registration info
    public Login(String username, String password, String cellNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // --- Method to validate username ---
    // Username must contain an underscore (_) and be no more than 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // --- Method to validate password complexity ---
    // Password must:
    // - Be at least 8 characters long
    // - Contain a capital letter
    // - Contain a number
    // - Contain a special character
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Loop through each character and check what it contains
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        // Return true only if all conditions are met
        return hasUppercase && hasDigit && hasSpecialChar;
    }

    // --- Method to validate South African cell phone number ---
    // Must start with +27 and be followed by exactly 9 digits
    public boolean checkCellPhoneNumber() {
        String regex = "^\\+27\\d{9}$"; // Regex pattern: +27 and 9 digits
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellNumber);
        return matcher.matches(); // Returns true if pattern matches the cell number
    }

    // --- Registration process that validates all inputs and returns the appropriate message ---
    public String registerUser() {
        // Check if username is invalid
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // Check if password is invalid
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // Check if cell number is invalid
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain international code, please correct the number and try again.";
        }

        // If all checks pass, return success messages
        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }

    // --- Method to log in a user by comparing entered credentials with stored ones ---
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // --- Returns login status message based on login success or failure ---
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

