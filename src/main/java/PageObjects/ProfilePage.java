package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage extends DriverHolder {

    @FindBy(className = "file_to_upload")
    private WebElement filePathTextField;

    @FindBy(xpath = "//*[contains(text(), 'Choose file')]")
    private WebElement uploadPhotoButton;

    @FindBy(className = "photo_file_name")
    private WebElement photoName;

    public ProfilePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void uploadProfileImage(String pathToFile) {
        filePathTextField.clear();
        filePathTextField.sendKeys(pathToFile);
        uploadPhotoButton.click();
    }

    public String getPhotoFileName() {
        return photoName.getText();
    }
}
