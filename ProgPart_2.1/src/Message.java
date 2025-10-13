import javax.swing.JOptionPane;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
final class Message {
   private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private boolean isSent;
    private static int totalMessagesSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.isSent = false;
    }

    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Check if message ID is valid (not more than 10 characters)
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Check recipient cell number format
    public int checkRecipientCell() {
        if (recipient == null) return -1;

        String regex = "^\\+27\\d{9}$"; // South African format: +27 followed by 9 digits
        if (recipient.matches(regex)) {
            return 1; // Valid
        } else {
            return 0; // Invalid
        }
    }

    // Create message hash
    public String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) {
            return "00:0:EMPTY";
        }

        String idPrefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        String[] words = messageText.split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return (idPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Send message with user choice
    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose an action for this message:",
            "Message Action",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        switch (choice) {
            case 0 -> {
                if (sendMessage()) {
                    return "Message successfully sent.";
                } else {
                    return "Failed to send message.";
                }
            }
            case 1 -> {
                return "Message disregarded.";
            }
            case 2 -> {
                return storeMessage();
            }
            default -> {
                return "No action selected.";
            }
        }
    }

    // Send the message
    private boolean sendMessage() {
        if (messageText.length() > 250) {
            JOptionPane.showMessageDialog(null,
                "Message exceeds 250 characters by " + (messageText.length() - 250) +
                ", please reduce size.");
            return false;
        }

        if (checkRecipientCell() != 1) {
            JOptionPane.showMessageDialog(null,
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            return false;
        }

        this.isSent = true;
        totalMessagesSent++;
        sentMessages.add(this);
        printMessageDetails();
        return true;
    }

    // Store message
    private String storeMessage() {
        return "Message successfully stored.";
    }

    // Print message details
    private void printMessageDetails() {
        String details = """
                         Message Details:
                         Message ID: """ + messageID + "\n" +
                        "Message Hash: " + messageHash + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText + "\n";

        JOptionPane.showMessageDialog(null, details);
    }

    // Print all sent messages
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }

        StringBuilder sb = new StringBuilder("All Sent Messages:\n");
        for (Message msg : sentMessages) {
            sb.append("Message ").append(msg.messageNumber)
              .append(": ").append(msg.messageText)
              .append(" (To: ").append(msg.recipient).append(")\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Getters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public boolean isSent() { return isSent; }

    // Store message in JSON (placeholder)
    public String storeMessageJSON() {
        return "Message stored as JSON (implementation required)";
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to the Message Sender!");

        int messageNum = 1;
        String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g., +27821234567):");
        String text = JOptionPane.showInputDialog("Enter your message:");

        Message msg = new Message(messageNum, recipient, text);
        String result = msg.sentMessage();
        JOptionPane.showMessageDialog(null, result);

        JOptionPane.showMessageDialog(null,
                "Total messages sent: " + Message.returnTotalMessages() + "\n\n" +
                Message.printMessages());
    }
}