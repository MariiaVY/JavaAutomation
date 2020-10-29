package io.ctdev.framework.pages.login;

import io.ctdev.framework.config.TestConfig;
import io.ctdev.framework.pages.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
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
    private By logOutElement = By.xpath("//*[@id='navbarLogoutButton']//span");



    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(TestConfig.cfg.baseUrl() + "#/login");
    }

    @Step("Enter empty email and password string")
    public void enterEmptyEmailAndPasswordString() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).sendKeys(emptyString);
        getDriver().findElement(passwordElement).sendKeys(emptyString);
    }

    @Step("Get invalid email error")
    public String getInvalidEmailError() {
        wait.until(ExpectedConditions.presenceOfElementLocated(errorElement));
        return getDriver().findElement(errorElement).getAttribute("innerText").trim();
    }

    @Step("Clear email and password fields")
    public void clearEmailAndPasswordField() {
        getDriver().manage().addCookie(new Cookie("language", "en"));
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).clear();
        getDriver().findElement(passwordElement).clear();
    }

    @Step("Get current user name")
    public String getCurrentUserName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(navBarAccountElement));
        getDriver().findElement(navBarAccountElement).click();
        WebElement userNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(goToUserProfileElement));

        return userNameElement.getAttribute("innerText").trim();
    }

    @Step("Click on login button")
    public void clickOnLoginButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(loginButtonElement));
        WebElement element = driver.findElement(loginButtonElement);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
       // getDriver().findElement(loginButtonElement).click();
    }

    @Step("Enter user email & password")
    public void logIn (String email, String password) {
        System.out.println("Log in after registration");
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        getDriver().findElement(emailElement).sendKeys(email);
        getDriver().findElement(passwordElement).sendKeys(password);
        getDriver().findElement(loginButtonElement).click();
    }

    @Step("User can log out")
    public void logOut() {
        wait.until(ExpectedConditions.presenceOfElementLocated(logOutElement));
        WebElement element = driver.findElement(logOutElement);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        //getDriver().findElement(logOutElement).click();
    }
}
