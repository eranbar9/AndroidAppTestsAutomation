import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EmailTests {
    private static AndroidDriver driver;

    @BeforeClass
    public static void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.gotit");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

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
