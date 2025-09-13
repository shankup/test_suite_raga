package learning.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Locators
    private By emailField = By.cssSelector("input[placeholder='Work email address']");
    private By passwordField = By.cssSelector("input[name='password']");
    private By loginButton = By.cssSelector("button[aria-label='Login']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
