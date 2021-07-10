package tests;

import io.appium.java_client.android.AndroidElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.*;

public class PasswordTests extends BaseTest {

    @BeforeClass
    public static void initTest() {
        AndroidElement startButton = (AndroidElement) driver.findElementByAccessibilityId("start_button");
        startButton.click();
    }

    @Test
    public void invalidPasswordPolicy() {
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        passwordInput.clear();

        passwordInput.sendKeys("123");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement passwordPolicyError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Password should be at least') and @index = '8']");
        assertTrue(passwordPolicyError.getText().contains("Password should be at least"));
    }

    @Test
    public void validPasswordPolicy() {
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        passwordInput.clear();

        passwordInput.sendKeys("123456");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        try {
            driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Password should be at least') and @index = '8']");
            fail("Password length is less than policy!");
        } catch (NoSuchElementException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void mismatchPassword() {
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        passwordInput.clear();
        AndroidElement repeatPasswordInput = (AndroidElement) driver.findElementByAccessibilityId("repeat_password_input");
        repeatPasswordInput.clear();

        passwordInput.sendKeys("Ab123456");
        repeatPasswordInput.sendKeys("Cd9876543");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement mismatchPasswordError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Password not match') and @index = '9']");
        assertTrue(mismatchPasswordError.getText().contains("Password not match"));
    }

    @Test
    public void matchedPasswords() {
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        passwordInput.clear();
        AndroidElement repeatPasswordInput = (AndroidElement) driver.findElementByAccessibilityId("repeat_password_input");
        repeatPasswordInput.clear();

        passwordInput.sendKeys("Ab123456");
        repeatPasswordInput.sendKeys("Ab123456");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        try {
            driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Password not match') and @index = '9']");
            fail("Password not match!");
        } catch (NoSuchElementException exception) {
            assertTrue(true);
        }
    }

    @AfterClass
    public static void quit() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
