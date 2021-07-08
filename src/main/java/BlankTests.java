import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlankTests {
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
