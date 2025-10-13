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
public class LoginNGTest {
    
    public LoginNGTest() {
        
    }
     // --- Username Tests ---
    @Test
    public void testValidUserName() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
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
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkPasswordComplexity(), "Password meets all complexity requirements.");
    }

    @Test
    public void testInvalidPassword_TooShort() {
        Login login = new Login("ky_e", "Pas1!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password shorter than 8 characters should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoUppercase() {
        Login login = new Login("ky_e", "pass123!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without uppercase letter should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoDigit() {
        Login login = new Login("ky_e", "Password!", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without a digit should be invalid.");
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        Login login = new Login("ky_e", "Pass1234", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(login.checkPasswordComplexity(), "Password without special character should be invalid.");
    }

    @Test
    public void testValidComplexPassword_SpecialExample() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkPasswordComplexity(), "Password 'Ch&&sec@Ke99!' should be valid and complex.");
    }

    // --- Cell Phone Number Tests ---
    @Test
    public void testValidCellNumber() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkCellPhoneNumber(), "Valid SA cell number should pass.");
    }

    @Test
    public void testInvalidCellNumber_MissingPrefix() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "0823456789", "Kyle", "Smith");
        Assert.assertFalse(login.checkCellPhoneNumber(), "Missing +27 prefix should fail.");
    }

    @Test
    public void testInvalidCellNumber_TooShort() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+2783896897", "Kyle", "Smith");
        Assert.assertFalse(login.checkCellPhoneNumber(), "Incorrect length cell number should fail.");
    }

    @Test
    public void testValidCellNumber_Alternate() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        Assert.assertTrue(login.checkCellPhoneNumber(), "Valid alternate SA number should pass.");
    }

    // --- Registration Tests ---
    @Test
    public void testSuccessfulRegistration() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertTrue(message.contains("Username successfully captured."));
        Assert.assertTrue(message.contains("Password successfully captured."));
        Assert.assertTrue(message.contains("Cell number successfully captured."));
    }

    @Test
    public void testRegistrationFails_InvalidUsername() {
        Login login = new Login("kyle", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
    }

    @Test
    public void testRegistrationFails_InvalidPassword() {
        Login login = new Login("ky_e", "pass", "+27838968976", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }

    @Test
    public void testRegistrationFails_InvalidCellNumber() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "123456789", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Cell number is incorrectly formatted or does not contain international code, please correct the number and try again.");
    }

    @Test
    public void testRegistrationFails_WithInvalidCellNumber089() {
        Login login = new Login("Kyl_1", "Ch&&sec@Ke99!", "08966553", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertEquals(message,
            "Cell number is incorrectly formatted or does not contain international code, please correct the number and try again.");
    }

    @Test
    public void testRegistrationSuccess_WithValidDetails() {
        Login login = new Login("Kyl_1", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        String message = login.registerUser();
        Assert.assertTrue(message.contains("Username successfully captured."));
        Assert.assertTrue(message.contains("Password successfully captured."));
        Assert.assertTrue(message.contains("Cell number successfully captured."));
    }

    // --- Login Tests ---
    @Test
    public void testLogin_Success() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        boolean result = login.loginUser("ky_e", "Ch&&sec@Ke99!");
        Assert.assertTrue(result);
        Assert.assertEquals(login.returnLoginStatus(result),
            "Welcome Kyle, Smith it is great to see you again.");
    }

    @Test
    public void testLogin_Failure() {
        Login login = new Login("ky_e", "Ch&&sec@Ke99!", "+27838968976", "Kyle", "Smith");
        boolean result = login.loginUser("ky_e", "WrongPass123!");
        Assert.assertFalse(result);
        Assert.assertEquals(login.returnLoginStatus(result),
            "Username or password incorrect, please try again.");
    }

    /**
     * Test of checkUserName method, of class Login.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        Login instance = null;
        boolean expResult = false;
        boolean result = instance.checkUserName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        Login instance = null;
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        Login instance = null;
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Login instance = null;
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredUsername = "";
        String enteredPassword = "";
        Login instance = null;
        boolean expResult = false;
        boolean result = instance.loginUser(enteredUsername, enteredPassword);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean loginSuccessful = false;
        Login instance = null;
        String expResult = "";
        String result = instance.returnLoginStatus(loginSuccessful);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}