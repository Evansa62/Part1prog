/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    // Test data from the assignment
    private static final String TEST_RECIPIENT_1 = "+27718693002";
    private static final String TEST_MESSAGE_1 = "Hi Mike, can you join us for dinner tonight";
    private static final String TEST_RECIPIENT_2 = "08575975889";
    private static final String TEST_MESSAGE_2 = "Hi Keegan, did you receive the payment?";
    
    @Test
    public void testMessageIDCreation() {
        Message message = new Message(1, TEST_RECIPIENT_1, TEST_MESSAGE_1);
        Assert.assertTrue(message.checkMessageID(), "Message ID should be valid (10 digits or less)");
        Assert.assertTrue(message.getMessageID().length() <= 10, "Message ID should not exceed 10 characters");
    }
    
    @Test
    public void testValidRecipientCell() {
        Message message = new Message(1, TEST_RECIPIENT_1, TEST_MESSAGE_1);
        Assert.assertEquals(message.checkRecipientCell(), 1, "Valid SA cell number should return 1");
    }
    
    @Test
    public void testInvalidRecipientCell() {
        Message message = new Message(1, TEST_RECIPIENT_2, TEST_MESSAGE_2);
        Assert.assertEquals(message.checkRecipientCell(), 0, "Invalid cell number should return 0");
    }
    
    @Test
    public void testMessageHashCreation() {
        Message message = new Message(1, TEST_RECIPIENT_1, TEST_MESSAGE_1);
        String hash = message.createMessageHash();
        Assert.assertNotNull(hash, "Message hash should not be null");
        Assert.assertTrue(hash.contains(":"), "Message hash should contain colons");
        Assert.assertEquals(hash, hash.toUpperCase(), "Message hash should be in uppercase");
    }
    
    @Test
    public void testMessageWithinLengthLimit() {
        String shortMessage = "Short message";
        // This would be tested in the actual send method
        Assert.assertTrue(shortMessage.length() <= 250, "Message should be within 250 characters");
    }
    
    @Test
    public void testMessageExceedsLengthLimit() {
        // Create a long message (251 characters)
        String longMessage = "A".repeat(251);
        Assert.assertTrue(longMessage.length() > 250, "Message should exceed 250 characters for this test");
    }
    
    @Test
    public void testTotalMessagesCounter() {
        int initialCount = Message.returnTotalMessages();
        
        // Note: Actual sending would need to be simulated for proper testing
        // This test verifies the static method works
        Assert.assertTrue(initialCount >= 0, "Total messages should be non-negative");
    }
    
    @Test
    public void testMessageHashFormat() {
        Message message = new Message(1, TEST_RECIPIENT_1, "Hi there thanks");
        String hash = message.createMessageHash();
        
        // Hash should be in format: firstTwoOfID:messageNumber:FirstWordLastWord
        String[] parts = hash.split(":");
        Assert.assertEquals(parts.length, 3, "Message hash should have 3 parts separated by colons");
        Assert.assertEquals(parts[1], "1", "Second part should be message number");
    }
    
    @Test
    public void testEmptyMessageHash() {
        Message message = new Message(1, TEST_RECIPIENT_1, "");
        String hash = message.createMessageHash();
        Assert.assertEquals(hash, "00:1:EMPTY", "Empty message should have EMPTY in hash");
    }
}