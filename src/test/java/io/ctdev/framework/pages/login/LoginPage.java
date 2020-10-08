package io.ctdev.framework.pages.login;

import io.ctdev.framework.config.TestConfig;
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
    private By goToUserProfileElement = By.cssSelector("[aria-label='Go to user profile'] span");
    private String emptyString = "          ";



    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(TestConfig.cfg.baseUrl() + "#/login");
    }

    public void enterEmptyEmailAndPasswordString() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).sendKeys(emptyString);
        getDriver().findElement(passwordElement).sendKeys(emptyString);
    }

    public String getInvalidEmailError() {
        wait.until(ExpectedConditions.presenceOfElementLocated(errorElement));
        return getDriver().findElement(errorElement).getAttribute("innerText").trim();
    }

    public void clearEmailAndPasswordField() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).clear();
        getDriver().findElement(passwordElement).clear();
    }

    public String getCurrentUserName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navBarAccountElement));
        getDriver().findElement(navBarAccountElement).click();
        WebElement userNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(goToUserProfileElement));

        return userNameElement.getAttribute("innerText").trim();
    }

    public void clickOnLoginButton() {
        getDriver().findElement(loginButtonElement).click();
    }

    public void logIn (String email, String password) {
        System.out.println("Log in after registration");
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).sendKeys(email);
        getDriver().findElement(passwordElement).sendKeys(password);
        getDriver().findElement(loginButtonElement).click();
    }
}
