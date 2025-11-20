
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
public final class Message {

    static List<Message> loadMessagesFromJsonFile(String path) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private boolean sent;
    private static int totalMessages = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient != null ? recipient.trim() : "";
        this.messageText = messageText != null ? messageText : "";
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.sent = false;
    }

    private String generateMessageID() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        if (recipient == null || recipient.isEmpty()) return false;
        if (!recipient.startsWith("+")) return false;
        String digits = recipient.substring(1);
        return digits.matches("\\d+") && digits.length() >= 9 && digits.length() <= 10;
    }

    public String createMessageHash() {
        String idPrefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        String firstLast = "";
        String trimmed = messageText.trim();
        
        if (!trimmed.isEmpty()) {
            String[] parts = trimmed.split("\\s+");
            if (parts.length > 0) {
                String first = parts[0].replaceAll("[^A-Za-z0-9]", "");
                String last = parts[parts.length - 1].replaceAll("[^A-Za-z0-9]", "");
                firstLast = (first + last).toUpperCase();
            }
        }
        
        if (firstLast.isEmpty()) {
            firstLast = "EMPTY";
        }
        
        return String.format("%s:%d:%s", idPrefix, messageNumber, firstLast);
    }

    public String SentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(
            null, 
            "Choose action for the message:\n" + messageText, 
            "Send Message", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]
        );

        switch (choice) {
            case 0 -> {
                this.sent = true;
                totalMessages++;
                JOptionPane.showMessageDialog(null, "Message successfully sent.");
                return "Message successfully sent.";
            }
            case 1 -> {
                JOptionPane.showMessageDialog(null, "Message disregarded.");
                return "Press 0 to delete message.";
            }
            case 2 -> {
                boolean ok = storeMessageAsJson();
                if (ok) JOptionPane.showMessageDialog(null, "Message successfully stored.");
                else JOptionPane.showMessageDialog(null, "Failed to store message.");
                return ok ? "Message successfully stored." : "Failed to store message.";
            }
            default -> {
                JOptionPane.showMessageDialog(null, "Message disregarded.");
                return "Message disregarded.";
            }
        }
    }

    public String printMessages() {
        return String.format("MessageID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s", 
                           messageID, messageHash, recipient, messageText);
    }

    public int returnTotalMessages() {
        return sent ? 1 : 0;
    }

    public static int getTotalMessagesCount() {
        return totalMessages;
    }

    public boolean storeMessageAsJson() {
        File file = new File("messages.json");
        List<String> existingObjects = new ArrayList<>();
        
        try {
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath())).trim();
                if (content.startsWith("[") && content.endsWith("]")) {
                    String inner = content.substring(1, content.length() - 1).trim();
                    if (!inner.isEmpty()) {
                        String[] parts = inner.split("\\},\\s*\\{");
                        if (parts.length == 1 && inner.startsWith("{") && inner.endsWith("}")) {
                            existingObjects.add(parts[0]);
                        } else {
                            for (int i = 0; i < parts.length; i++) {
                                String p = parts[i].trim();
                                if (i != 0) p = "{" + p;
                                if (i != parts.length - 1) p = p + "}";
                                existingObjects.add(p);
                            }
                        }
                    }
                }
            }
            
            String jsonObj = toJsonObject();
            existingObjects.add(jsonObj);
            
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (int i = 0; i < existingObjects.size(); i++) {
                sb.append(existingObjects.get(i));
                if (i < existingObjects.size() - 1) sb.append(",\n");
            }
            sb.append("\n]");
            
            Files.write(file.toPath(), sb.toString().getBytes());
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private String toJsonObject() {
        String escaped = messageText.replace("\\", "\\\\")
                                  .replace("\"", "\\\"")
                                  .replace("\n", "\\n");
        return String.format("{\"messageID\":\"%s\",\"messageHash\":\"%s\",\"recipient\":\"%s\",\"message\":\"%s\",\"sent\":%s}",
                           messageID, messageHash, recipient, escaped, sent);
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }


}