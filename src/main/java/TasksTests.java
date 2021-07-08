import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TasksTests {
    private static AndroidDriver driver;

    @BeforeClass
    public static void setup() throws MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.gotit");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "true");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        Thread.sleep(3000);

        AndroidElement createNewTaskButton = (AndroidElement) driver.findElementByAccessibilityId("bottom_bar_create_button");
        createNewTaskButton.click();
    }

    @Test
    public void createValidTask() throws InterruptedException {
        AndroidElement taskType = (AndroidElement) driver.findElementByXPath("(//android.widget.HorizontalScrollView/android.view.ViewGroup/android.view.ViewGroup)[5]");
        AndroidElement taskDetailsTitleInput = (AndroidElement) driver.findElementByXPath("//android.widget.EditText[@text='Title']");
        AndroidElement taskDetailsSubTaskInput = (AndroidElement) driver.findElementByXPath("//android.widget.EditText[@text='Sub task']");
        AndroidElement taskDetailsSubTaskSubmit = (AndroidElement) driver.findElementByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView");
        AndroidElement taskUploadImage = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Upload images')]");

        String details_title = "Tennis match appointment";
        String details_sub_task = "Tennis";

        taskType.click();
        taskDetailsTitleInput.sendKeys(details_title);
        taskDetailsSubTaskInput.sendKeys(details_sub_task);
        taskDetailsSubTaskSubmit.click();

        taskUploadImage.click();

        Thread.sleep(1000);

        AndroidElement taskSelectFolder = (AndroidElement) driver.findElementById("com.google.android.apps.photos:id/image");
        taskSelectFolder.click();

        Thread.sleep(1000);

        AndroidElement taskSelectPhoto = (AndroidElement) driver.findElementByXPath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[2]");
        taskSelectPhoto.click();

        Thread.sleep(1000);

        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"Create\"))"));

        List<AndroidElement> datePickerCurrent = driver.findElements(By.xpath("//*[@class='android.widget.NumberPicker']/android.widget.EditText"));
        List<AndroidElement> datePickerIncrement = driver.findElements(By.xpath("//*[@class='android.widget.NumberPicker']/android.widget.Button[1]"));
        while (!datePickerCurrent.get(0).getText().equals("Mon Jul 12")) {
            datePickerIncrement.get(0).click();
        }
        while (!datePickerCurrent.get(1).getText().equals("10")) {
            datePickerIncrement.get(1).click();
        }
        while (!datePickerCurrent.get(2).getText().equals("30")) {
            datePickerIncrement.get(2).click();
        }

        AndroidElement createButton = (AndroidElement) driver.findElementByXPath("//android.widget.TextView[@text = 'Create']");
        createButton.click();

        AndroidElement tasksListDate = (AndroidElement) driver.findElementByXPath("(//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup)[1]//android.widget.TextView");
        assertEquals("Monday July 12th", tasksListDate.getText());

        AndroidElement tasksListHour = (AndroidElement) driver.findElementByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[2]");
        assertEquals("22:30pm", tasksListHour.getText().toLowerCase());

        AndroidElement tasksListTitle = (AndroidElement) driver.findElementByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[1]");
        assertEquals("Tennis match appointment", tasksListTitle.getText());

        AndroidElement tasksListTitleSubTask = (AndroidElement) driver.findElementByXPath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView[2]");
        assertEquals("Tennis", tasksListTitleSubTask.getText());
    }

    @AfterClass
    public static void quit() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
