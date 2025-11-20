
import java.util.*;
import java.io.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManager {
    // Lists for dynamic operations
    private final List<Message> sentMessages;
    private final List<Message> storedMessages;
    private final List<Message> disregardedMessages;
    private final List<String> messageHashes;
    private final List<String> messageIDs;
    
    // Arrays for Part 3 requirements
    private Message[] sentMessagesArray;
    private Message[] storedMessagesArray;
    private Message[] disregardedMessagesArray;
    private String[] messageHashArray;
    private String[] messageIDArray;
    
    private int messageCounter;
    
    public MessageManager() {
        this.sentMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.messageHashes = new ArrayList<>();
        this.messageIDs = new ArrayList<>();
        this.messageCounter = 0;
        
        // Initialize arrays
        populateArrays();
        
        // Load test data
        addTestData();
        
        System.out.println("MessageManager initialized successfully!");
        System.out.println("Sent messages: " + sentMessages.size());
        System.out.println("Stored messages: " + storedMessages.size());
        System.out.println("Disregarded messages: " + disregardedMessages.size());
    }
    
    /**
     * MAIN METHOD FOR TESTING
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("=== MESSAGE MANAGER TEST ===");
        System.out.println("Starting MessageManager test...\n");
        
        try {
            // Create MessageManager instance
            System.out.println("1. Creating MessageManager instance...");
            MessageManager manager = new MessageManager();
            System.out.println("✓ MessageManager created successfully!\n");
            
            // Test 1: Display all sent messages
            System.out.println("2. Testing: Display all sent messages");
            String sentMessages = manager.displayAllSentMessages();
            System.out.println(sentMessages);
            
            // Test 2: Display longest message
            System.out.println("3. Testing: Display longest sent message");
            String longestMessage = manager.displayLongestSentMessage();
            System.out.println(longestMessage + "\n");
            
            // Test 3: Search by recipient
            System.out.println("4. Testing: Search messages by recipient (+27838884567)");
            String recipientSearch = manager.searchMessagesByRecipient("+27838884567");
            System.out.println(recipientSearch);
            
            // Test 4: Search by message ID
            System.out.println("5. Testing: Search by message ID");
            Message[] sentArray = manager.getSentMessagesArray();
            if (sentArray.length > 0) {
                String messageId = sentArray[0].getMessageID();
                String idSearch = manager.searchMessageByID(messageId);
                System.out.println(idSearch + "\n");
            }
            
            // Test 5: Display full report
            System.out.println("6. Testing: Display full report");
            String fullReport = manager.displayFullReport();
            System.out.println(fullReport);
            
            // Test 6: Display statistics
            System.out.println("7. Testing: Display statistics");
            String stats = manager.getStatistics();
            System.out.println(stats + "\n");
            
            // Test 7: Test message creation and validation
            System.out.println("8. Testing: Message creation and validation");
            Message newMessage = manager.createMessage("+27839998877", "This is a new test message");
            System.out.println("✓ New message created:");
            System.out.println("  Recipient: " + newMessage.getRecipient());
            System.out.println("  Message: " + newMessage.getMessageText());
            System.out.println("  ID: " + newMessage.getMessageID());
            System.out.println("  Hash: " + newMessage.getMessageHash());
            
            // Test validation
            System.out.println("\n9. Testing: Validation methods");
            System.out.println("  Valid recipient +27831234567: " + manager.validateRecipient("+27831234567"));
            System.out.println("  Invalid recipient 0821234567: " + manager.validateRecipient("0821234567"));
            System.out.println("  Valid message length: " + manager.validateMessageLength("Short message"));
            System.out.println("  Long message (251 chars): " + manager.validateMessageLength("A".repeat(251)));
            
            // Test 8: Test message processing
            System.out.println("\n10. Testing: Message processing actions");
            Message testMessage = manager.createMessage("+27837776655", "Test message for processing");
            manager.processMessageAction(testMessage, "SENT");
            System.out.println("✓ Message processed as SENT");
            System.out.println("  New sent count: " + manager.getSentMessagesArray().length);
            
            // Test 9: Test delete functionality
            System.out.println("\n11. Testing: Delete message by hash");
            if (manager.getStoredMessagesArray().length > 0) {
                String hashToDelete = manager.getStoredMessagesArray()[0].getMessageHash();
                String deleteResult = manager.deleteMessageByHash(hashToDelete);
                System.out.println("Delete result: " + deleteResult);
                System.out.println("  New stored count: " + manager.getStoredMessagesArray().length);
            }
            
            System.out.println("\n=== ALL TESTS COMPLETED SUCCESSFULLY! ===");
            System.out.println("Final Statistics:");
            System.out.println(manager.getStatistics());
            
        } catch (Exception e) {
            System.err.println("❌ ERROR during testing: " + e.getMessage());
        }
    }
    
    // === ARRAY MANAGEMENT METHODS ===
    
    private void populateArrays() {
        sentMessagesArray = sentMessages.toArray(Message[]::new);
        storedMessagesArray = storedMessages.toArray(Message[]::new);
        disregardedMessagesArray = disregardedMessages.toArray(Message[]::new);
        messageHashArray = messageHashes.toArray(String[]::new);
        messageIDArray = messageIDs.toArray(String[]::new);
    }
    
    public void refreshArrays() {
        populateArrays();
    }
    
    // === TEST DATA METHODS ===
    
    private void addTestData() {
        System.out.println("Loading test data...");
        
        // Test Data Message 1: +27834557896 "Did you get the cake?" Sent.
        addTestMessage(1, "+27834557896", "Did you get the cake?", "SENT");
        
        // Test Data Message 2: +27838884567 "Where are you? You are late! I have asked you to be on time." Stored
        addTestMessage(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "STORED");
        
        // Test Data Message 3: +27834484567 "Yohoooo, I am at your gate." Disregard
        addTestMessage(3, "+27834484567", "Yohoooo, I am at your gate.", "DISREGARDED");
        
        // Test Data Message 4: 0838884567 "It is dinner time !" Sent.
        addTestMessage(4, "0838884567", "It is dinner time !", "SENT");
        
        // Test Data Message 5: +27838884567 "Ok, I am leaving without you." Stored.
        addTestMessage(5, "+27838884567", "Ok, I am leaving without you.", "STORED");
        
        refreshArrays();
        System.out.println("Test data loaded: " + (sentMessages.size() + storedMessages.size() + disregardedMessages.size()) + " messages");
    }
    
    private void addTestMessage(int messageNumber, String recipient, String messageText, String status) {
        try {
            Message message = new Message(messageNumber, recipient, messageText);
            
            switch (status) {
                case "SENT" -> {
                    message.setSent(true);
                    sentMessages.add(message);
                }
                case "STORED" -> storedMessages.add(message);
                case "DISREGARDED" -> disregardedMessages.add(message);
            }
            
            messageHashes.add(message.getMessageHash());
            messageIDs.add(message.getMessageID());
            
            System.out.println("  Added test message " + messageNumber + ": " + status);
        } catch (Exception e) {
            System.err.println("Error adding test message " + messageNumber + ": " + e.getMessage());
        }
    }
    
    // === MESSAGE OPERATIONS ===
    
    public Message createMessage(String recipient, String messageText) {
        messageCounter++;
        System.out.println("Creating message #" + messageCounter + " for recipient: " + recipient);
        return new Message(messageCounter, recipient, messageText);
    }
    
    public boolean validateRecipient(String recipient) {
        if (recipient == null || recipient.trim().isEmpty()) {
            System.out.println("Recipient validation failed: null or empty");
            return false;
        }
        
        // Simple validation without creating Message object
        if (!recipient.startsWith("+")) {
            System.out.println("Recipient validation failed: missing '+' prefix");
            return false;
        }
        
        String digits = recipient.substring(1);
        boolean isValid = digits.matches("\\d+") && digits.length() >= 9 && digits.length() <= 10;
        
        System.out.println("Recipient validation: " + recipient + " -> " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }
    
    public boolean validateMessageLength(String messageText) {
        boolean isValid = messageText != null && messageText.length() <= 250;
        System.out.println("Message length validation: " + (messageText != null ? messageText.length() : "null") + " chars -> " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }
    
    public void processMessageAction(Message message, String action) {
        System.out.println("Processing message action: " + action);
        
        switch (action) {
            case "SENT" -> {
                message.setSent(true);
                sentMessages.add(message);
                System.out.println("Message marked as SENT");
            }
            case "DISREGARDED" -> {
                disregardedMessages.add(message);
                System.out.println("Message marked as DISREGARDED");
            }
            case "STORED" -> {
                storedMessages.add(message);
                System.out.println("Message marked as STORED");
                // Store to file without JSON
                storeMessageToFile(message);
            }
        }
        
        messageHashes.add(message.getMessageHash());
        messageIDs.add(message.getMessageID());
        refreshArrays();
        
        System.out.println("Message processing completed. Current counts - Sent: " + sentMessages.size() + 
                          ", Stored: " + storedMessages.size() + ", Disregarded: " + disregardedMessages.size());
    }
    
    // Simple file storage without JSON
    private void storeMessageToFile(Message message) {
        try {
            File file = new File("messages.txt");
            try (FileWriter writer = new FileWriter(file, true) // append mode
            ) {
                writer.write("MessageID: " + message.getMessageID() + "\n");
                writer.write("Hash: " + message.getMessageHash() + "\n");
                writer.write("Recipient: " + message.getRecipient() + "\n");
                writer.write("Message: " + message.getMessageText() + "\n");
                writer.write("Sent: " + message.isSent() + "\n");
                writer.write("---\n");
            }
            
            System.out.println("Message stored to file: messages.txt");
        } catch (IOException e) {
            System.err.println("Error storing message to file: " + e.getMessage());
        }
    }
    
    // === ARRAY OPERATIONS FOR PART 3 ===
    
    public String displayAllSentMessages() {
        System.out.println("Displaying all sent messages...");
        
        if (sentMessagesArray.length == 0) {
            return "No sent messages found.";
        }
        
        StringBuilder sb = new StringBuilder("=== ALL SENT MESSAGES ===\n\n");
        for (int i = 0; i < sentMessagesArray.length; i++) {
            Message m = sentMessagesArray[i];
            sb.append(i + 1).append(". Recipient: ").append(m.getRecipient())
              .append("\n   Message: ").append(m.getMessageText())
              .append("\n   ID: ").append(m.getMessageID())
              .append("\n   Hash: ").append(m.getMessageHash())
              .append("\n\n");
        }
        return sb.toString();
    }
    
    public String displayLongestSentMessage() {
        if (sentMessagesArray.length == 0) {
            return "No sent messages found.";
        }
        
        Message longest = sentMessagesArray[0];
        for (Message m : sentMessagesArray) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        
        return """
               === LONGEST SENT MESSAGE ===
               
               Recipient: """ + longest.getRecipient() + "\n" +
               "Message: " + longest.getMessageText() + "\n" +
               "Length: " + longest.getMessageText().length() + " characters\n" +
               "ID: " + longest.getMessageID() + "\n" +
               "Hash: " + longest.getMessageHash();
    }
    
    public String searchMessageByID(String messageID) {
        if (messageID == null || messageID.trim().isEmpty()) {
            return "Please provide a valid Message ID.";
        }
        
        String searchID = messageID.trim();
        
        // Search in sent messages
        for (Message m : sentMessagesArray) {
            if (m.getMessageID().equals(searchID)) {
                return """
                       === MESSAGE FOUND (SENT) ===
                       
                       Message ID: """ + m.getMessageID() + "\n" +
                       "Recipient: " + m.getRecipient() + "\n" +
                       "Message: " + m.getMessageText() + "\n" +
                       "Hash: " + m.getMessageHash();
            }
        }
        
        // Search in stored messages
        for (Message m : storedMessagesArray) {
            if (m.getMessageID().equals(searchID)) {
                return """
                       === MESSAGE FOUND (STORED) ===
                       
                       Message ID: """ + m.getMessageID() + "\n" +
                       "Recipient: " + m.getRecipient() + "\n" +
                       "Message: " + m.getMessageText() + "\n" +
                       "Hash: " + m.getMessageHash();
            }
        }
        
        return "Message ID '" + searchID + "' not found.";
    }
    
    public String searchMessagesByRecipient(String recipient) {
        if (recipient == null || recipient.trim().isEmpty()) {
            return "Please provide a valid recipient number.";
        }
        
        String searchRecipient = recipient.trim();
        List<Message> foundMessages = new ArrayList<>();
        
        // Search in sent messages
        for (Message m : sentMessagesArray) {
            if (m.getRecipient().equals(searchRecipient)) {
                foundMessages.add(m);
            }
        }
        
        // Search in stored messages
        for (Message m : storedMessagesArray) {
            if (m.getRecipient().equals(searchRecipient)) {
                foundMessages.add(m);
            }
        }
        
        if (foundMessages.isEmpty()) {
            return "No messages found for recipient: " + searchRecipient;
        }
        
        StringBuilder sb = new StringBuilder("=== MESSAGES FOR RECIPIENT: " + searchRecipient + " ===\n\n");
        for (int i = 0; i < foundMessages.size(); i++) {
            Message m = foundMessages.get(i);
            sb.append(i + 1).append(". ").append(m.isSent() ? "[SENT] " : "[STORED] ")
              .append(m.getMessageText())
              .append("\n   ID: ").append(m.getMessageID())
              .append("\n   Hash: ").append(m.getMessageHash())
              .append("\n\n");
        }
        
        return sb.toString();
    }
    
    public String deleteMessageByHash(String messageHash) {
        if (messageHash == null || messageHash.trim().isEmpty()) {
            return "Please provide a valid message hash.";
        }
        
        String hashToDelete = messageHash.trim();
        Message messageToDelete = null;
        String messageType = "";
        
        // Search in sent messages
        for (Message m : sentMessagesArray) {
            if (m.getMessageHash().equals(hashToDelete)) {
                messageToDelete = m;
                messageType = "sent";
                break;
            }
        }
        
        // Search in stored messages
        if (messageToDelete == null) {
            for (Message m : storedMessagesArray) {
                if (m.getMessageHash().equals(hashToDelete)) {
                    messageToDelete = m;
                    messageType = "stored";
                    break;
                }
            }
        }
        
        // Search in disregarded messages
        if (messageToDelete == null) {
            for (Message m : disregardedMessagesArray) {
                if (m.getMessageHash().equals(hashToDelete)) {
                    messageToDelete = m;
                    messageType = "disregarded";
                    break;
                }
            }
        }
        
        if (messageToDelete != null) {
            String deletedMessageText = messageToDelete.getMessageText();
            
            // Remove from appropriate list
            switch (messageType) {
                case "sent" -> sentMessages.remove(messageToDelete);
                case "stored" -> storedMessages.remove(messageToDelete);
                case "disregarded" -> disregardedMessages.remove(messageToDelete);
            }
            
            // Remove from hash and ID lists
            messageHashes.remove(messageToDelete.getMessageHash());
            messageIDs.remove(messageToDelete.getMessageID());
            
            refreshArrays();
            
            return "Message successfully deleted from " + messageType + " messages.\n" +
                   "Deleted message: \"" + deletedMessageText + "\"\n" +
                   "Message ID: " + messageToDelete.getMessageID() + "\n" +
                   "Recipient: " + messageToDelete.getRecipient();
        } else {
            return "Message hash '" + hashToDelete + "' not found.";
        }
    }
    
    public String displayFullReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== QUICKCHAT COMPREHENSIVE REPORT ===\n\n");
        
        // Sent Messages
        sb.append("SENT MESSAGES (").append(sentMessagesArray.length).append("):\n");
        for (int i = 0; i < sentMessagesArray.length; i++) {
            Message m = sentMessagesArray[i];
            sb.append(i + 1).append(". Hash: ").append(m.getMessageHash())
              .append("\n   Recipient: ").append(m.getRecipient())
              .append("\n   Message: ").append(m.getMessageText())
              .append("\n   ID: ").append(m.getMessageID())
              .append("\n\n");
        }
        
        // Stored Messages
        sb.append("STORED MESSAGES (").append(storedMessagesArray.length).append("):\n");
        for (int i = 0; i < storedMessagesArray.length; i++) {
            Message m = storedMessagesArray[i];
            sb.append(i + 1).append(". Hash: ").append(m.getMessageHash())
              .append("\n   Recipient: ").append(m.getRecipient())
              .append("\n   Message: ").append(m.getMessageText())
              .append("\n   ID: ").append(m.getMessageID())
              .append("\n\n");
        }
        
        // Disregarded Messages
        sb.append("DISREGARDED MESSAGES (").append(disregardedMessagesArray.length).append("):\n");
        for (int i = 0; i < disregardedMessagesArray.length; i++) {
            Message m = disregardedMessagesArray[i];
            sb.append(i + 1).append(". Hash: ").append(m.getMessageHash())
              .append("\n   Recipient: ").append(m.getRecipient())
              .append("\n   Message: ").append(m.getMessageText())
              .append("\n   ID: ").append(m.getMessageID())
              .append("\n\n");
        }
        
        return sb.toString();
    }
    
    public String getStatistics() {
        return """
               === MESSAGE STATISTICS ===
               Total Sent Messages: """ + sentMessagesArray.length + "\n" +
               "Total Stored Messages: " + storedMessagesArray.length + "\n" +
               "Total Disregarded Messages: " + disregardedMessagesArray.length + "\n" +
               "Total Message Hashes: " + messageHashArray.length + "\n" +
               "Total Message IDs: " + messageIDArray.length + "\n" +
               "Total All Messages: " + (sentMessagesArray.length + storedMessagesArray.length + disregardedMessagesArray.length);
    }
    
    // === GETTER METHODS FOR UNIT TESTING ===
    
    public Message[] getSentMessagesArray() { 
        return sentMessagesArray; 
    }
    
    public Message[] getStoredMessagesArray() { 
        return storedMessagesArray; 
    }
    
    public Message[] getDisregardedMessagesArray() { 
        return disregardedMessagesArray; 
    }
    
    public String[] getMessageHashArray() { return messageHashArray; }
    public String[] getMessageIDArray() { return messageIDArray; }
    public List<Message> getSentMessages() { return sentMessages; }
    public List<Message> getStoredMessages() { return storedMessages; }
    public List<Message> getDisregardedMessages() { return disregardedMessages; }
    public int getTotalMessageCount() { return messageCounter; }

    void loadStoredMessagesFromJsonFile(String messagesjson) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void addMessage(Message m, String sent) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<String> displaySenderAndRecipientOfSent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String getLongestSentMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Message findByMessageID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<Message> findByRecipient(String rcpt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean deleteByHash(String hash) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object displayReportOfSent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}