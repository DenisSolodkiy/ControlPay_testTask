package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IntermediatePage extends DriverHolder {

    @FindBy(xpath = "//*[contains(text(), 'My Profile')]")
    private WebElement myProfileButton;

    public IntermediatePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public ProfilePage goToTheProfile() {
        myProfileButton.click();

        return new ProfilePage(this.webDriver);
    }
}
