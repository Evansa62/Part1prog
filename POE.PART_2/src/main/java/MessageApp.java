
import java.util.List;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
public class MessageApp {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        // Simulated Part 1 user credentials (replace with real registration data if available)
        Login user = new Login("student_user", "Password1!"); // default simulated credential
        boolean loggedIn = simulateLogin(user);
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again. Exiting.");
            System.exit(0);
        }

        int totalMessagesToEnter = promptForNumberOfMessages();

        MessageManager manager = new MessageManager();
        int messageCounter = 0;
        boolean running = true;

        // Load any previously stored messages from JSON (so the app can operate on them)
        manager.loadStoredMessagesFromJsonFile("messages.json");

        while (running) {
            String menu = "Choose an option:\n1) Send Messages\n2) Show recently sent messages\n3) Show Part 3 Tools\n4) Quit";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) { // user closed dialog
                continue;
            }
            input = input.trim();
            switch (input) {
                case "1" -> {
                    for (int i = 0; i < totalMessagesToEnter; i++) {
                        messageCounter++;
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (international code required, e.g. +27#########):");
                        if (recipient == null) recipient = "";
                        String messageText = JOptionPane.showInputDialog("Enter message (max 250 characters):");
                        if (messageText == null) messageText = "";

                        // Validate message length (<=250)
                        if (messageText.length() > 250) {
                            int excess = messageText.length() - 250;
                            JOptionPane.showMessageDialog(null,
                                    String.format("Message exceeds 250 characters by %d, please reduce size.", excess));
                            i--; messageCounter--; // allow re-entry for this message
                            continue;
                        }

                        Message m = new Message(messageCounter, recipient, messageText);

                        // Validate recipient
                        if (!m.checkRecipientCell()) {
                            JOptionPane.showMessageDialog(null,
                                    "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            i--; messageCounter--;
                            continue;
                        } else {
                            JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
                        }

                        // Ask user to send/store/disregard
                        String actionResult = m.SentMessage(); // returns exact rubric strings
                        if (null != actionResult) switch (actionResult) {
                            case "Message successfully sent." -> manager.addMessage(m, "sent");
                            case "Press 0 to delete message." -> manager.addMessage(m, "disregarded");
                            case "Message successfully stored." -> manager.addMessage(m, "stored");
                            default -> {}
                        }

                        // Show full details as required
                        JOptionPane.showMessageDialog(null, m.printMessages());
                    }

                    // After all messages processed
                    JOptionPane.showMessageDialog(null, "Message ready to send.");
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + manager.getSentMessages().size());
                }

                case "2" -> {
                    List<Message> sent = manager.getSentMessages();
                    if (sent.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No sent messages yet.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (Message m : sent) {
                            sb.append(m.printMessages()).append("\n---\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                }

                case "3" -> {
                    String toolsMenu = "Part 3 Tools\n1) Display sender & recipient of sent messages\n2) Display longest sent message\n3) Search by message ID\n4) Search by recipient\n5) Delete by message hash\n6) Show Sent Report\n7) Load stored messages from messages.json\n8) Back";
                    String choice = JOptionPane.showInputDialog(toolsMenu);
                    if (choice == null) break;
                    switch (choice.trim()) {
                        case "1" -> {
                            List<String> list = manager.displaySenderAndRecipientOfSent();
                            if (list.isEmpty()) JOptionPane.showMessageDialog(null, "No sent messages.");
                            else JOptionPane.showMessageDialog(null, String.join("\n", list));
                        }
                        case "2" -> {
                            String longest = manager.getLongestSentMessage();
                            if (longest.isEmpty()) JOptionPane.showMessageDialog(null, "No sent messages.");
                            else JOptionPane.showMessageDialog(null, "Longest sent message:\n" + longest);
                        }
                        case "3" -> {
                            String id = JOptionPane.showInputDialog("Enter Message ID to search:");
                            Message res = manager.findByMessageID(id);
                            JOptionPane.showMessageDialog(null, res == null ? "Not found." : res.printMessages());
                        }
                        case "4" -> {
                            String rcpt = JOptionPane.showInputDialog("Enter recipient to search (+country...):");
                            List<Message> results = manager.findByRecipient(rcpt);
                            if (results.isEmpty()) JOptionPane.showMessageDialog(null, "No messages found for recipient.");
                            else {
                                StringBuilder sb = new StringBuilder();
                                for (Message m : results) sb.append(m.printMessages()).append("\n---\n");
                                JOptionPane.showMessageDialog(null, sb.toString());
                            }
                        }
                        case "5" -> {
                            String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                            boolean ok = manager.deleteByHash(hash);
                            JOptionPane.showMessageDialog(null, ok ? "Message deleted." : "Message not found.");
                        }
                        case "6" -> JOptionPane.showMessageDialog(null, manager.displayReportOfSent());
                        case "7" -> {
                            manager.loadStoredMessagesFromJsonFile("messages.json");
                            JOptionPane.showMessageDialog(null, "Loaded stored messages from messages.json. Count: " + manager.getStoredMessages().size());
                        }
                        default -> { /* back */ }
                    }
                }

                case "4" -> running = false;

                default -> JOptionPane.showMessageDialog(null, "Please enter 1, 2, 3 or 4.");
            }
        }

        // Optional summary report before exiting
        displaySummary(manager.getSentMessages(), manager.getDisregardedMessages(), manager.getStoredMessages());
        System.exit(0);
    }

    private static boolean simulateLogin(Login user) {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        boolean ok = user.loginUser(username, password);
        if (ok) JOptionPane.showMessageDialog(null, "Welcome " + username + ", it is great to see you again.");
        else JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
        return ok;
    }

    private static int promptForNumberOfMessages() {
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages will you enter?");
            if (input == null) continue;
            try {
                int n = Integer.parseInt(input.trim());
                if (n <= 0) {
                    JOptionPane.showMessageDialog(null, "Enter a positive integer.");
                    continue;
                }
                return n;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
            }
        }
    }

    private static void displaySummary(List<Message> sent, List<Message> disregarded, List<Message> stored) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- QuickChat Report ---\n");
        sb.append("Sent messages: ").append(sent.size()).append("\n");
        for (Message m : sent) {
            sb.append(m.getMessageHash()).append(" | ").append(m.getRecipient()).append(" | ").append(m.getMessageText()).append("\n");
        }
        sb.append("\nStored messages: ").append(stored.size()).append("\n");
        for (Message m : stored) {
            sb.append(m.getMessageHash()).append(" | ").append(m.getRecipient()).append(" | ").append(m.getMessageText()).append("\n");
        }
        sb.append("\nDisregarded messages: ").append(disregarded.size()).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
