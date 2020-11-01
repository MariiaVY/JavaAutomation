package io.ctdev.cucumber.pages;

import io.ctdev.framework.config.TestConfig;
import io.ctdev.framework.pages.AbstractPage;
import io.qameta.allure.Step;
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
    private By closeDialogElement = By.cssSelector("[class*='close-dialog']");

    public SignInPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(TestConfig.cfg.baseUrl() + "/#/register");
    }

    @Step("Get email field error")
    public String getEmailFieldError() {
        return getDriver().findElement(emailAddressElement).getAttribute("innerText").trim();
    }

    @Step("Close Dialog")
    public void closeDialog() {
        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        wait.until(ExpectedConditions.presenceOfElementLocated(closeDialogElement));
        getDriver().findElement(closeDialogElement).click();
    }

    @Step("Click on navbar Account")
    public void clickOnNavbarAccount() {
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();
    }

    @Step("Click on new customer link button")
    public void clickOnNewCustomerLinkButton() {
        getDriver().findElement(By.id("navbarLoginButton")).click();
        getDriver().findElement(By.id("newCustomerLink")).click();
    }

    @Step("Enter invalid email")
    public void enterInvalidEmail(String email) {
        System.out.println("Negative case for Email field");
        getDriver().findElement(emailControlElement).sendKeys(email);
        getDriver().findElement(registrationFormElement).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(emailAddressElement));
    }

    @Step("Get password field error")
    public String getPasswordFieldError() {
        return getDriver().findElement(passwordFieldErrorElement).getAttribute("innerText").trim();
    }

    @Step("Enter invalid password")
    public void enterInvalidPassword(String password) {
        System.out.println("Negative case for Password field");
        getDriver().findElement(passwordControlElement).sendKeys(password);
        getDriver().findElement(registrationFormElement).click();
    }

    @Step("Get repeat password field error")
    public String getRepeatPasswordFieldError() {
        return getDriver().findElement(repeatPasswordErrorElement).getAttribute("innerText").trim();
    }

    @Step("Repeat invalid password")
    public void enterInvalidRepeatPassword() {
        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(repeatPasswordControlElement).sendKeys(invalidPasswordRepeat);
        getDriver().findElement(registrationFormElement).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(repeatPasswordErrorElement));
    }

    @Step("Get answer control field error")
    public String getAnswerControlFieldError() {
        getDriver().findElement(exclamationCircleElement).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(securityAnswerErrorElement));
        return getDriver().findElement(securityAnswerErrorElement).getAttribute("innerText").trim();
    }

    @Step("Check that register button is disabled")
    public void checkThatRegisterButtonIsDisabled() {
        System.out.println("Attribute 'disabled':" + getDriver().findElement(registerButtonElement).getAttribute("disabled"));
    }

    @Step("Click on answer control field")
    public void clickOnAnswerControlField() {
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(securityAnswerControlElement));
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(securityAnswerControlElement)));
        wait.until(ExpectedConditions.presenceOfElementLocated(securityAnswerControlElement)).click();
    }

    @Step("Select security question")
    public void selectSecurityQuestion() {
//        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
//        getDriver().manage().addCookie(new Cookie ("language", "en"));
        wait.until(ExpectedConditions.presenceOfElementLocated(selectSecurityListElement));
        WebElement element = driver.findElement(selectSecurityListElement);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.presenceOfElementLocated(maidenNameElement));
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(maidenNameElement));
        getDriver().findElement(maidenNameElement).click();
    }

    @Step("Check if registration form is not present")
    public void checkIfRegistrationFormIsNotPresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailElement));
        System.out.println("Check if the registration form isn't present");
        Assert.assertFalse(existsElement(id), "registration form check");
    }

    @Step("Click on register button")
    public void clickOnRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(getRegisterButtonEnabledElement)));
        getDriver().findElement(getRegisterButtonEnabledElement).click();
    }

    @Step("Select option from the list")
    public void selectOptionFromList() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(selectSecurityListElement)).click().perform();
        getDriver().findElement(maidenNameElement).click();
    }

    @Step("Enter control answer")
    public void inputAnswerControlText(String answer) {
        getDriver().findElement(securityAnswerControlElement).sendKeys(maidenName);
    }

    @Step("Repeat valid password")
    public void repeatValidPasswordInput(String password) {
        getDriver().findElement(repeatPasswordControlElement).clear();
        getDriver().findElement(repeatPasswordControlElement).sendKeys(password);
    }

    @Step("Enter valid password")
    public void inputValidPassword(String password) {
        getDriver().findElement(passwordControlElement).clear();
        getDriver().findElement(passwordControlElement).sendKeys(password);
    }

    @Step("Enter valid email")
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
