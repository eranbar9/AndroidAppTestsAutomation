import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTests {
    private static AndroidDriver driver;

    @BeforeClass
    public static void setup() throws MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.gotit");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "false");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        AndroidElement loginButton = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Login') and @index = '0']");
        loginButton.click();

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

        AndroidElement loginButton = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView[@text='Login'])[2]");;
        loginButton.click();

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
