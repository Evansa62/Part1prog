/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerNGTest {
    
   private MessageManager messageManager;
    
    @BeforeMethod
    public void setUp() {
        messageManager = new MessageManager();
    }
    
    /**
     * MAIN METHOD FOR MANUAL TESTING
     * This allows you to run tests without TestNG
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("=== MESSAGE MANAGER MANUAL TEST RUNNER ===");
        System.out.println("Running tests without TestNG framework...\n");
        
        MessageManagerNGTest testRunner = new MessageManagerNGTest();
        int passedTests = 0;
        int totalTests = 0;
        
        try {
            // Test 1: Setup and initialization
            System.out.println("1. Testing MessageManager initialization...");
            testRunner.setUp();
            totalTests++;
            if (testRunner.messageManager != null) {
                System.out.println("   ✓ PASS - MessageManager created successfully");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - MessageManager is null");
            }
            
            // Test 2: Sent messages array populated
            System.out.println("\n2. Testing sent messages array population...");
            totalTests++;
            if (testRunner.testSentMessagesArrayPopulatedManual()) {
                System.out.println("   ✓ PASS - Sent messages array correctly populated");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Sent messages array test failed");
            }
            
            // Test 3: Search by recipient
            System.out.println("\n3. Testing search by recipient...");
            totalTests++;
            if (testRunner.testSearchByRecipientManual()) {
                System.out.println("   ✓ PASS - Search by recipient working");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Search by recipient test failed");
            }
            
            // Test 4: Display longest message
            System.out.println("\n4. Testing display longest message...");
            totalTests++;
            if (testRunner.testDisplayLongestMessageManual()) {
                System.out.println("   ✓ PASS - Longest message display working");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Longest message test failed");
            }
            
            // Test 5: Statistics
            System.out.println("\n5. Testing statistics...");
            totalTests++;
            if (testRunner.testStatisticsManual()) {
                System.out.println("   ✓ PASS - Statistics working");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Statistics test failed");
            }
            
            // Test 6: Search by message ID
            System.out.println("\n6. Testing search by message ID...");
            totalTests++;
            if (testRunner.testSearchByMessageIDManual()) {
                System.out.println("   ✓ PASS - Search by message ID working");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Search by message ID test failed");
            }
            
            // Test 7: Array contents match test data
            System.out.println("\n7. Testing array contents match test data...");
            totalTests++;
            if (testRunner.testArrayContentsMatchTestDataManual()) {
                System.out.println("   ✓ PASS - All test data correctly loaded");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Test data loading failed");
            }
            
            // Test 8: Display full report
            System.out.println("\n8. Testing display full report...");
            totalTests++;
            if (testRunner.testDisplayFullReportManual()) {
                System.out.println("   ✓ PASS - Full report working");
                passedTests++;
            } else {
                System.out.println("   ❌ FAIL - Full report test failed");
            }
            
            // Final results
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TEST RESULTS: " + passedTests + "/" + totalTests + " tests passed");
            
            if (passedTests == totalTests) {
                System.out.println("🎉 ALL TESTS PASSED SUCCESSFULLY!");
            } else {
                System.out.println("⚠️  Some tests failed. Check the errors above.");
            }
            System.out.println("=".repeat(50));
            
        } catch (Exception e) {
            System.err.println("❌ UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Manual test methods (without TestNG assertions)
    
    private boolean testSentMessagesArrayPopulatedManual() {
        try {
            Message[] sentMessages = messageManager.getSentMessagesArray();
            
            if (sentMessages.length != 2) {
                System.out.println("   Expected 2 sent messages, got: " + sentMessages.length);
                return false;
            }
            
            boolean foundMessage1 = false;
            boolean foundMessage4 = false;
            
            for (Message msg : sentMessages) {
                if (msg.getMessageText().equals("Did you get the cake?")) {
                    foundMessage1 = true;
                }
                if (msg.getMessageText().equals("It is dinner time !")) {
                    foundMessage4 = true;
                }
            }
            
            if (!foundMessage1) {
                System.out.println("   Missing: 'Did you get the cake?'");
                return false;
            }
            if (!foundMessage4) {
                System.out.println("   Missing: 'It is dinner time!'");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testSearchByRecipientManual() {
        try {
            String searchResult = messageManager.searchMessagesByRecipient("+27838884567");
            
            if (searchResult == null || searchResult.isEmpty()) {
                System.out.println("   Search result is null or empty");
                return false;
            }
            
            if (!searchResult.contains("+27838884567")) {
                System.out.println("   Search result doesn't contain recipient");
                return false;
            }
            
            System.out.println("   Found messages for recipient: +27838884567");
            return true;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testDisplayLongestMessageManual() {
        try {
            String result = messageManager.displayLongestSentMessage();
            
            if (result == null || result.isEmpty()) {
                System.out.println("   Longest message result is null or empty");
                return false;
            }
            
            if (!result.contains("LONGEST SENT MESSAGE")) {
                System.out.println("   Result doesn't indicate it's showing longest message");
                return false;
            }
            
            System.out.println("   Longest message displayed successfully");
            return true;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testStatisticsManual() {
        try {
            String stats = messageManager.getStatistics();
            
            if (stats == null || stats.isEmpty()) {
                System.out.println("   Statistics result is null or empty");
                return false;
            }
            
            if (!stats.contains("Total Sent Messages")) {
                System.out.println("   Statistics missing sent messages count");
                return false;
            }
            
            System.out.println("   Statistics: " + stats.replace("\n", " | "));
            return true;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testSearchByMessageIDManual() {
        try {
            Message[] sentMessages = messageManager.getSentMessagesArray();
            
            if (sentMessages.length > 0) {
                String messageId = sentMessages[0].getMessageID();
                String searchResult = messageManager.searchMessageByID(messageId);
                
                if (searchResult.contains("MESSAGE FOUND")) {
                    System.out.println("   Message found by ID: " + messageId);
                    return true;
                } else {
                    System.out.println("   Message not found by ID: " + messageId);
                    return false;
                }
            }
            
            System.out.println("   No sent messages to test ID search");
            return true; // Not a failure, just no data to test
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testArrayContentsMatchTestDataManual() {
        try {
            // Check all 5 test messages are in correct arrays
            boolean allFound = true;
            
            // Message 1: Should be in sent
            if (!isMessageInArray("+27834557896", "Did you get the cake?", messageManager.getSentMessagesArray())) {
                System.out.println("   Missing Message 1 in sent array");
                allFound = false;
            }
            
            // Message 4: Should be in sent  
            if (!isMessageInArray("0838884567", "It is dinner time !", messageManager.getSentMessagesArray())) {
                System.out.println("   Missing Message 4 in sent array");
                allFound = false;
            }
            
            // Message 2: Should be in stored
            if (!isMessageInArray("+27838884567", "Where are you? You are late! I have asked you to be on time.", messageManager.getStoredMessagesArray())) {
                System.out.println("   Missing Message 2 in stored array");
                allFound = false;
            }
            
            // Message 5: Should be in stored
            if (!isMessageInArray("+27838884567", "Ok, I am leaving without you.", messageManager.getStoredMessagesArray())) {
                System.out.println("   Missing Message 5 in stored array");
                allFound = false;
            }
            
            // Message 3: Should be in disregarded
            if (!isMessageInArray("+27834484567", "Yohoooo, I am at your gate.", messageManager.getDisregardedMessagesArray())) {
                System.out.println("   Missing Message 3 in disregarded array");
                allFound = false;
            }
            
            if (allFound) {
                System.out.println("   All 5 test messages correctly placed in arrays");
            }
            
            return allFound;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean testDisplayFullReportManual() {
        try {
            String report = messageManager.displayFullReport();
            
            if (report == null || report.isEmpty()) {
                System.out.println("   Full report is null or empty");
                return false;
            }
            
            if (!report.contains("SENT MESSAGES") || !report.contains("STORED MESSAGES")) {
                System.out.println("   Full report missing required sections");
                return false;
            }
            
            System.out.println("   Full report generated successfully");
            return true;
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean isMessageInArray(String recipient, String messageText, Message[] array) {
        for (Message msg : array) {
            if (msg.getRecipient().equals(recipient) && msg.getMessageText().equals(messageText)) {
                return true;
            }
        }
        return false;
    }
    
    // =========================================================================
    // ORIGINAL TestNG TESTS (keep these for when you have TestNG working)
    // =========================================================================
    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        Message[] sentMessages = messageManager.getSentMessagesArray();
        
        Assert.assertEquals(sentMessages.length, 2, "Sent messages array should contain 2 messages");
        
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals("+27834557896") && 
                msg.getMessageText().equals("Did you get the cake?")) {
                foundMessage1 = true;
            }
            if (msg.getRecipient().equals("0838884567") && 
                msg.getMessageText().equals("It is dinner time !")) {
                foundMessage4 = true;
            }
        }
        
        Assert.assertTrue(foundMessage1, "Should find Message 1 in sent messages array");
        Assert.assertTrue(foundMessage4, "Should find Message 4 in sent messages array");
    }
    
    @Test
    public void testDisplayLongestMessage() {
        String longestMessageResult = messageManager.displayLongestSentMessage();
        
        Assert.assertTrue(longestMessageResult.contains("LONGEST SENT MESSAGE"), 
            "Should display longest message header");
        Assert.assertTrue(longestMessageResult.contains("Recipient:"), 
            "Should contain recipient information");
        Assert.assertTrue(longestMessageResult.contains("Message:"), 
            "Should contain message text");
    }
    
    @Test
    public void testSearchForMessageID() {
        Message[] sentMessages = messageManager.getSentMessagesArray();
        
        if (sentMessages.length > 0) {
            String knownMessageID = sentMessages[0].getMessageID();
            String searchResult = messageManager.searchMessageByID(knownMessageID);
            
            Assert.assertTrue(searchResult.contains("MESSAGE FOUND"), 
                "Should find message with ID: " + knownMessageID);
        }
    }
    
    @Test
    public void testSearchMessagesByRecipient() {
        String recipientToSearch = "+27838884567";
        String searchResult = messageManager.searchMessagesByRecipient(recipientToSearch);
        
        Assert.assertTrue(searchResult.contains(recipientToSearch),
            "Should display the recipient being searched");
        Assert.assertTrue(searchResult.contains("MESSAGES FOR RECIPIENT"),
            "Should indicate it's searching by recipient");
    }
    
    @Test
    public void testAllArraysArePopulated() {
        Assert.assertNotNull(messageManager.getSentMessagesArray(), 
            "Sent messages array should be populated");
        Assert.assertNotNull(messageManager.getStoredMessagesArray(), 
            "Stored messages array should be populated");
        Assert.assertNotNull(messageManager.getDisregardedMessagesArray(), 
            "Disregarded messages array should be populated");
        
        Assert.assertEquals(messageManager.getSentMessagesArray().length, 2,
            "Sent messages array should have 2 items");
        Assert.assertEquals(messageManager.getStoredMessagesArray().length, 2,
            "Stored messages array should have 2 items");
        Assert.assertEquals(messageManager.getDisregardedMessagesArray().length, 1,
            "Disregarded messages array should have 1 item");
    }
    
    @Test
    public void testStatisticsReporting() {
        String statistics = messageManager.getStatistics();
        
        Assert.assertTrue(statistics.contains("Total Sent Messages"),
            "Statistics should include sent messages count");
        Assert.assertTrue(statistics.contains("Total Stored Messages"),
            "Statistics should include stored messages count");
        Assert.assertTrue(statistics.contains("Total Disregarded Messages"),
            "Statistics should include disregarded messages count");
    }
}