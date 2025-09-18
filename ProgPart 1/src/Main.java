/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
/**
 *
 * @author RC_Student_lab
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
         // Using try-with-resources to automatically close the Scanner resource
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("=== User Registration ===");
            
            // Prompt the user to enter a username
            System.out.print("Enter username: ");
            String username = input.nextLine();
            
             // Prompt the user to enter a password
            System.out.print("Enter password: ");
            String password = input.nextLine();
            
            // Prompt the user to enter their South African cell phone number (with international code)
            System.out.print("Enter South African cell phone number (with country code +27): ");
            String cellNumber = input.nextLine();
            
            // Prompt the user to enter their first name
            System.out.print("Enter first name: ");
            String firstName = input.nextLine();
            
            // Prompt the user to enter their last name
            System.out.print("Enter last name: ");
            String lastName = input.nextLine();
            
            // Create a new Login object using the user input
            Login user = new Login(username, password, cellNumber, firstName, lastName);
            
            // Register the user and store the registration messag
            String registrationMessage = user.registerUser();
            
            // Print the registration feedback message(s) to the user
            System.out.println("\n" + registrationMessage);
            
            // Check if all registration conditions are met by verifying if all success messages are present
            if (registrationMessage.contains("Username successfully captured.") &&
                    registrationMessage.contains("Password successfully captured.") &&
                    registrationMessage.contains("Cell number successfully captured.")) {
                 
            // Proceed with login since registration was successful
                System.out.println("\n=== User Login ===");
                
                // Prompt user to enter their username for login
                System.out.print("Enter username to login: ");
                
                String loginUsername = input.nextLine();
                 // Prompt user to enter their password for login
                System.out.print("Enter password to login: ");
                
                String loginPassword = input.nextLine();
                
                 // Attempt to login by checking credentials against the registered details
                boolean loginSuccess = user.loginUser(loginUsername, loginPassword);
                
                 // Display the appropriate login status message to the user
                System.out.println(user.returnLoginStatus(loginSuccess));
            } else {
                // If registration failed, prompt the user to fix the errors before trying to login
                System.out.println("Fix the errors above before trying to login.");
            }
        }
    }
}