package tests;

import io.appium.java_client.android.AndroidElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTests extends BaseTest {

    @BeforeClass
    public static void initTest() throws InterruptedException {
        AndroidElement loginButtonHomePage = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Login') and @index = '0']");
        loginButtonHomePage.click();

        Thread.sleep(1000);
    }

    @Test
    public void loginInvalidEmail() throws InterruptedException {
        AndroidElement emailInput = (AndroidElement) driver.findElementByXPath("//android.widget.EditText[@text='Email']");
        AndroidElement passwordInput = (AndroidElement) driver.findElementByXPath("//android.widget.EditText[@text='Password']");

        String email = "nouser123@gmail.com";
        String password = "Ab123456";

        emailInput.clear();
        emailInput.sendKeys(email);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        AndroidElement loginButtonLoginPage = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView[@text='Login'])[2]");;
        loginButtonLoginPage.click();

        Thread.sleep(2000);

        AndroidElement loginEmailError = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'There is no user record corresponding to this identifier') and @index = '1']");
        assertTrue(loginEmailError.getText().contains("There is no user record corresponding to this identifier"));
    }

    @AfterClass
    public static void quit() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
