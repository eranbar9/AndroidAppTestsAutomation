package tests;

import io.appium.java_client.android.AndroidElement;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlankTests extends BaseTest {

    @BeforeClass
    public static void initTest() {
        AndroidElement startButton = (AndroidElement) driver.findElementByAccessibilityId("start_button");
        startButton.click();
    }


    @Test
    public void blankEmail() {
        AndroidElement emailInput = (AndroidElement) driver.findElementByAccessibilityId("email_input");
        emailInput.clear();

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement emailError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Invalid email format') and @index = '4']");
        assertEquals("Invalid email format", emailError.getText());
    }

    @Test
    public void blankUserName() {
        AndroidElement userNameInput = (AndroidElement) driver.findElementByAccessibilityId("user_name_input");
        userNameInput.clear();

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement userNameError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Please add you name') and @index = '6']");
        assertEquals("Please add you name", userNameError.getText());
    }

    @Test
    public void blankPassword() {
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        passwordInput.clear();

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement passwordError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Password should be at least') and @index = '8']");
        assertTrue(passwordError.getText().contains("Password should be at least"));
    }

    @AfterClass
    public static void quit() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
