package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class TestsUtils {

    public static final int PAGE_LOADING_TIMEOUT = 10;
    public static final int WEB_ELEMENTS_INTERACTION_TIMEOUT = 10;
    public static final String PROFILE_IMAGE_PATH = "/home/data/photo_for_test_user.jpg";
    public static final String PROFILE_IMAGE_FILE_NAME = "photo_for_test_user.jpg";

    public static WebDriver initializeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER_PATH"));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOADING_TIMEOUT, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(WEB_ELEMENTS_INTERACTION_TIMEOUT, TimeUnit.SECONDS);

        return webDriver;
    }
}
