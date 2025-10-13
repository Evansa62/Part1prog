/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
/**
 *
 * @author RC_Student_lab
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
          try (Scanner input = new Scanner(System.in)) {
            System.out.println("=== User Registration ===");

            System.out.print("Enter username: ");
            String username = input.nextLine();

            System.out.print("Enter password: ");
            String password = input.nextLine();

            System.out.print("Enter South African cell phone number (with country code +27): ");
            String cellNumber = input.nextLine();

            System.out.print("Enter first name: ");
            String firstName = input.nextLine();

            System.out.print("Enter last name: ");
            String lastName = input.nextLine();

            // Create user
            Login user = new Login(username, password, cellNumber, firstName, lastName);
            String registrationMessage = user.registerUser();
            System.out.println("\n" + registrationMessage);

            if (registrationMessage.contains("Username successfully captured.") &&
                registrationMessage.contains("Password successfully captured.") &&
                registrationMessage.contains("Cell number successfully captured.")) {

                System.out.println("\n=== User Login ===");
                System.out.print("Enter username to login: ");
                String loginUsername = input.nextLine();

                System.out.print("Enter password to login: ");
                String loginPassword = input.nextLine();

                boolean loginSuccess = user.loginUser(loginUsername, loginPassword);
                System.out.println(user.returnLoginStatus(loginSuccess));

                if (loginSuccess) {
                    startMessagingFeature();
                }
            } else {
                System.out.println("Fix the errors above before trying to login.");
            }
        }
    }

    private static void startMessagingFeature() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        boolean running = true;

        while (running) {
            String[] options = {"Send Messages", "Show recently sent messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option:",
                    "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> sendMessages();
                case 1 -> JOptionPane.showMessageDialog(null, Message.printMessages());
                case 2, JOptionPane.CLOSED_OPTION -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat!");
                }
            }
        }
    }

    private static void sendMessages() {
        try {
            String numMessagesStr = JOptionPane.showInputDialog("How many messages do you want to send?");
            if (numMessagesStr == null) return;

            int numMessages = Integer.parseInt(numMessagesStr);

            for (int i = 1; i <= numMessages; i++) {
                JOptionPane.showMessageDialog(null, "Creating message " + i + " of " + numMessages);

                String recipient = JOptionPane.showInputDialog("Enter recipient cell number (e.g., +27831234567):");
                if (recipient == null) continue;

                String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
                if (messageText == null) continue;

                Message message = new Message(i, recipient, messageText);
                String result = message.sentMessage();
                JOptionPane.showMessageDialog(null, result);
            }

            JOptionPane.showMessageDialog(null,
                    "All messages processed!\nTotal messages sent: " + Message.returnTotalMessages());
            JOptionPane.showMessageDialog(null, Message.printMessages());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number for messages.");
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
        }
    }
}