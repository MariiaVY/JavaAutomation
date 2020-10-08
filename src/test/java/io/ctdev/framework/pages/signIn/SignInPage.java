package io.ctdev.framework.pages.signIn;

import io.ctdev.framework.config.TestConfig;
import io.ctdev.framework.pages.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class SignInPage extends AbstractPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private By emailAddressElement = By.xpath("//*[contains(text(),'Email address')]");
    private By emailControlElement = By.id("emailControl");
    private By passwordControlElement = By.id("passwordControl");
    private By repeatPasswordControlElement = By.id("repeatPasswordControl");
    private By securityAnswerControlElement = By.id("securityAnswerControl");
    private By registrationFormElement = By.id("registration-form");
    private By passwordFieldErrorElement = By.xpath("//*[contains(text(),'Password must be')]");
    private By repeatPasswordErrorElement = By.xpath("//*[contains(text(),'Passwords do not match')]");
    private By exclamationCircleElement = By.xpath("//*[@id='mat-hint-3']");
    private By securityAnswerErrorElement = By.xpath("//*[contains(text(),' Please provide an answer to your security question')]");
    private By registerButtonElement = By.id("registerButton");
    private By getRegisterButtonEnabledElement = By.cssSelector("[aria-label='Button to complete the registration'] span");
    private By securityListElement = By.xpath("//*[@aria-label='Selection list for the security question']");
    private By selectSecurityListElement = By.xpath("//mat-select[@aria-label = 'Selection list for the security question']");
    private By maidenNameElement = By.xpath("//*[contains(text(),'maiden name')]");
    private By emailElement = By.id("email");
    private String invalidPasswordRepeat = "aaaaka";
    private String maidenName = "Test";
    private String id = "registration-for";

    public SignInPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(TestConfig.cfg.baseUrl());
    }

    public String getEmailFieldError() {
        return getDriver().findElement(emailAddressElement).getAttribute("innerText").trim();
    }

    public void enterInvalidEmail(String email) {
        System.out.println("Negative case for Email field");
        getDriver().findElement(emailControlElement).sendKeys(email);
        getDriver().findElement(registrationFormElement).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(emailAddressElement));
    }

    public String getPasswordFieldError() {
        return getDriver().findElement(passwordFieldErrorElement).getAttribute("innerText").trim();
    }

    public void enterInvalidPassword(String password) {
        System.out.println("Negative case for Password field");
        getDriver().findElement(passwordControlElement).sendKeys(password);
        getDriver().findElement(registrationFormElement).click();
    }

    public String getRepeatPasswordFieldError() {
        return getDriver().findElement(repeatPasswordErrorElement).getAttribute("innerText").trim();
    }

    public void enterInvalidRepeatPassword() {
        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(repeatPasswordControlElement).sendKeys(invalidPasswordRepeat);
        getDriver().findElement(registrationFormElement).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(repeatPasswordErrorElement));
    }

    public String getAnswerControlFieldError() {
        getDriver().findElement(exclamationCircleElement).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(securityAnswerErrorElement));
        return getDriver().findElement(securityAnswerErrorElement).getAttribute("innerText").trim();
    }

    public void checkThatRegisterButtonIsDisabled() {
        System.out.println("Attribute 'disabled':" + getDriver().findElement(registerButtonElement).getAttribute("disabled"));
    }

    public void clickOnAnswerControlField() {
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(securityAnswerControlElement));
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(securityAnswerControlElement)));
        wait.until(ExpectedConditions.presenceOfElementLocated(securityAnswerControlElement)).click();
    }

    public void selectSecurityQuestion() {
        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        getDriver().navigate().refresh();
        System.out.println("Negative case for Security section");
        wait.until(ExpectedConditions.presenceOfElementLocated(securityListElement));
        new Actions(getDriver()).moveToElement(getDriver().findElement(selectSecurityListElement)).click().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(maidenNameElement));
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(maidenNameElement));
        getDriver().findElement(maidenNameElement).click();
    }

    public void checkIfRegistrationFormIsNotPresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        System.out.println("Check if the registration form isn't present");
        Assert.assertFalse(existsElement(id), "registration form check");
    }

    public void clickOnRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(getRegisterButtonEnabledElement)));
        getDriver().findElement(getRegisterButtonEnabledElement).click();
    }

    public void selectOptionFromList() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(selectSecurityListElement)).click().perform();
        getDriver().findElement(maidenNameElement).click();
    }

    public void inputAnswerControlText() {
        getDriver().findElement(securityAnswerControlElement).sendKeys(maidenName);
    }

    public void repeatValidPasswordInput(String password) {
        getDriver().findElement(repeatPasswordControlElement).clear();
        getDriver().findElement(repeatPasswordControlElement).sendKeys(password);
    }

    public void inputValidPassword(String password) {
        getDriver().findElement(passwordControlElement).clear();
        getDriver().findElement(passwordControlElement).sendKeys(password);
    }

    public void inputValidEmail(String email) {
        System.out.println("Registration");
        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        getDriver().navigate().refresh();
        getDriver().findElement(emailControlElement).clear();
        getDriver().findElement(emailControlElement).sendKeys(email);
    }

    public boolean existsElement(String id) {
        try {
            getDriver().findElement(By.id(id));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }


}
