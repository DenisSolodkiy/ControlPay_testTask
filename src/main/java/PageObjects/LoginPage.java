package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends DriverHolder {

    @FindBy(name = "email")
    private WebElement emailTextField;

    @FindBy(id = "password")
    private WebElement passwordTextField;

    @FindBy(className = "sign_in")
    private WebElement signInButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    private void fillEmailField(String email) {
        emailTextField.clear();
        emailTextField.sendKeys(email);
    }

    private void fillPasswordField(String password) {
        passwordTextField.clear();
        passwordTextField.sendKeys(password);
    }

    public IntermediatePage performLogin(String email, String password) {
        fillEmailField(email);
        fillPasswordField(password);
        signInButton.click();

        return new IntermediatePage(this.webDriver);
    }
}
