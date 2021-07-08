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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AccountTests {
    private static AndroidDriver driver;

    @BeforeClass
    public static void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.gotit");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "false");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        AndroidElement startButton = (AndroidElement) driver.findElementByAccessibilityId("start_button");
        startButton.click();
    }

    @Test
    public void createValidAccount() throws InterruptedException {
        AndroidElement emailInput = (AndroidElement) driver.findElementByAccessibilityId("email_input");
        AndroidElement userNameInput = (AndroidElement) driver.findElementByAccessibilityId("user_name_input");
        AndroidElement passwordInput = (AndroidElement) driver.findElementByAccessibilityId("password_input");
        AndroidElement repeatPasswordInput = (AndroidElement) driver.findElementByAccessibilityId("repeat_password_input");

        String email = "testuser@gmail.com";
        String userName = "testuser";
        String password = "Ab123456";

        emailInput.sendKeys(email);
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(password);
        repeatPasswordInput.sendKeys(password);

        AndroidElement createAccountButton = (AndroidElement) driver.findElementByAccessibilityId("create_account_button");
        createAccountButton.click();

        //AndroidElement homeScreenTitle = (AndroidElement) driver.findElementByAccessibilityId("home_screen_title");
        //WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait.until(ExpectedConditions.visibilityOf(homeScreenTitle));

        Thread.sleep(4000);

        AndroidElement homeScreenTitle = (AndroidElement) driver.findElementByAccessibilityId("home_screen_title");
        assertEquals("gotit", homeScreenTitle.getText().toLowerCase());

        AndroidElement userProfileHub = (AndroidElement) driver.findElementByXPath("//android.widget.ImageView[@index = '0'][1]");
        userProfileHub.click();

        Thread.sleep(2000);

        AndroidElement userProfileName = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView)[5][@index = '1']");
        AndroidElement userProfileEmail = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView)[6][@index = '2']");

        assertEquals(userName, userProfileName.getText());
        assertEquals(email, userProfileEmail.getText());
    }

    @AfterClass
    public static void quit() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
