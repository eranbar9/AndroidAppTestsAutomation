package tests;

import io.appium.java_client.android.AndroidElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EmailTests extends BaseTest {

    @BeforeClass
    public static void initTest() {
        AndroidElement startButton = (AndroidElement) driver.findElementByAccessibilityId("start_button");
        startButton.click();
    }

    @Test
    public void invalidEmailPolicy() {
        AndroidElement emailInput = (AndroidElement) driver.findElementByAccessibilityId("email_input");
        emailInput.clear();

        emailInput.sendKeys("user");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        AndroidElement emailPolicyError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Invalid email format') and @index = '4']");
        assertTrue(emailPolicyError.getText().contains("Invalid email format"));

        emailInput.clear();
        emailInput.sendKeys("user@");
        createAccountButton.click();
        assertTrue(emailPolicyError.getText().contains("Invalid email format"));

        emailInput.clear();
        emailInput.sendKeys("user@gmail");
        createAccountButton.click();
        assertTrue(emailPolicyError.getText().contains("Invalid email format"));
    }

    @Test
    public void validEmailPolicy() {
        AndroidElement emailInput = (AndroidElement) driver.findElementByAccessibilityId("email_input");
        emailInput.clear();

        emailInput.sendKeys("user@gmail.com");

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        try {
            driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Invalid email format') and @index = '4']");
            fail("Email policy error!");
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
