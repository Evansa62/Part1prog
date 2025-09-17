/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class MainNGTest {
    
    public MainNGTest() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkUserName(), "Username should be valid: contains underscore and max 5 characters.");
    }

    @Test
    public void testInvalidUserName_NoUnderscore() {
        Login login = new Login("kyle1", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkUserName(), "Username without underscore should be invalid.");
    }

    @Test
    public void testInvalidUserName_TooLong() {
        Login login = new Login("kyle_smith", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkUserName(), "Username longer than 5 characters should be invalid.");
    }

    // --- Password Complexity Tests ---
    @Test
    public void testValidPasswordComplexity() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkPasswordComplexity(), "Password meets all complexity requirements.");
    }

    @Test
    public void testInvalidPassword_TooShort() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password shorter than 8 characters should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoUppercase() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without uppercase letter should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoDigit() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without a digit should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without special character should be invalid.");
    }

    // --- Cell Phone Number Tests ---
    @Test
    public void testValidCellNumber() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkCellPhoneNumber(), "Cell number with +27 prefix and 9 digits should be valid.");
    }

    @Test
    public void testInvalidCellNumber_WrongPrefix() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkCellPhoneNumber(), "Cell number missing +27 prefix should be invalid.");
    }

    @Test
    public void testInvalidCellNumber_WrongLength() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith"); // 8 digits after +27
        Assert.assertFalse(login.checkCellPhoneNumber(), "Cell number with incorrect length should be invalid.");
    }

    // --- Registration Tests ---
    @Test
    public void testSuccessfulRegistration() {
        Login login = new Login("kyle_", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertTrue(message.contains("Username successfully captured."), "Registration message should confirm valid username.");
        Assert.assertTrue(message.contains("Password successfully captured."), "Registration message should confirm valid password.");
        Assert.assertTrue(message.contains("Cell number successfully captured."), "Registration message should confirm valid cell number.");
    }

    @Test
    public void testRegistrationFailsInvalidUsername() {
        Login login = new Login("kyle", "Pass123!", "+27123456789", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
            "Registration should fail due to invalid username.");
    }

    @Test
    public void testRegistrationFailsInvalidPassword() {
        Login login = new Login("kyle_", "pass", "+27123456789", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
            "Registration should fail due to invalid password.");
    }

    @Test
    public void testRegistrationFailsInvalidCellNumber() {
        Login login = new Login("kyle_", "Pass123!", "123456789", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Cell number is incorrectly formatted or does not contain international code, please correct the number and try again.",
            "Registration should fail due to invalid cell number.");
    }

    // --- Login Tests ---
    @Test
    public void testLoginSuccess() {
        Login login = new Login("kyle_", "Pass123!", "+27123456789", "Kyle", "Smith");
        boolean success = login.loginUser("kyle_", "Pass123!");
        Assert.assertTrue(success, "Login should succeed with correct credentials.");
        Assert.assertEquals(login.returnLoginStatus(success),
            "Welcome Kyle, Smith it is great to see you again.", "Login success message should be correct.");
    }

    @Test
    public void testLoginFail() {
        Login login = new Login("kyle_", "Pass123!", "+27123456789", "Kyle", "Smith");
        boolean success = login.loginUser("kyle_", "WrongPass");
        Assert.assertFalse(success, "Login should fail with incorrect password.");
        Assert.assertEquals(login.returnLoginStatus(success),
            "Username or password incorrect, please try again.", "Login failure message should be correct.");
    }
}
    
        
    
    