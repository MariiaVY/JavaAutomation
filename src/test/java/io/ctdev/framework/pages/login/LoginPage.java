package io.ctdev.framework.pages.login;

import io.ctdev.framework.config.testConfig;
import io.ctdev.framework.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class LoginPage extends AbstractPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private By passwordElement = By.id("password");
    private By emailElement = By.id("email");
    private By loginButtonElement = By.id("loginButton");
    private By errorElement = By.xpath("//*[contains(text(),'Invalid email')]");
    private By navBarAccountElement = By.id("navbarAccount");
    private By GoToUserProfileElement = By.cssSelector("[aria-label='Go to user profile'] span");


    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(testConfig.cfg.baseUrl() + "#/login");
    }

    public void enterInvalidPassword(String invalidPassword) {
        getDriver().findElement(passwordElement).sendKeys(invalidPassword);
    }

    public void enterInvalidEmail(String invalidEmail) {
        getDriver().findElement(emailElement).sendKeys(invalidEmail);
    }

    public void clickOnLoginButton() {
        getDriver().findElement(loginButtonElement).click();
    }

    public void enterEmptyPasswordString(String emptyString) {
        getDriver().findElement(passwordElement).sendKeys(emptyString);
    }

    public void enterEmptyEmailString(String emptyString) {
        getDriver().findElement(emailElement).sendKeys(emptyString);
    }

    public String getInvalidEmailError() {
        wait.until(ExpectedConditions.presenceOfElementLocated(errorElement));
        return getDriver().findElement(errorElement).getAttribute("innerText").trim();
    }

    public void clearPasswordField() {
        getDriver().findElement(passwordElement).clear();
    }

    public void clearEmailField() {
        System.out.println("Negative case for login section");
        getDriver().findElement(emailElement).clear();
    }

    public String getCurrentUserName() {
        getDriver().findElement(navBarAccountElement).click();
        WebElement userNameElement =  wait.until(ExpectedConditions.presenceOfElementLocated(GoToUserProfileElement));

        return userNameElement.getAttribute("innerText").trim();
    }

    public void enterUserPassword(String password) {
        getDriver().findElement(passwordElement).sendKeys(password);
    }

    public void enterUserEmail(String email) {
        System.out.println("Log in after registration");
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).sendKeys(email);
    }
}
