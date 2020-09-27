//Navigate to Juice Shop  http://18.217.145.6/.
//Create a test for customer sign-up
//Create tests for fields validation on the register page

package io.ctdev;

import io.ctdev.framework.WebDriverSingleton;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class CustomerSignUpToJuiceShop {

    public String email = "test" + System.currentTimeMillis() + "@gmail.com";
    public String password = "123456789Test!";
    public String maidenName = "Test";
    public String invalidEmail = "test@test.com////";
    public String invalidEmailText = "Email address is not valid.";
    public String invalidPassword = "aaaaa";
    public String invalidPasswordText = "Password must be 5-20 characters long.";
    public String invalidPasswordRepeat = "322";
    public String invalidPasswordRepeatText = "Passwords do not match";
    public String answerErrorText = "Please provide an answer to your security question.";
    public String id = "registration-form";
    WebDriverWait wait;


    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 7);
        getDriver().get("http://18.217.145.6/");
        getDriver().findElement(By.cssSelector("[class*='close-dialog']")).click();
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();
        getDriver().findElement(By.id("navbarLoginButton")).click();
        getDriver().findElement(By.id("newCustomerLink")).click();
    }

    @Test
    public void negativeCasesForRegistrationEmailField() {
        enterInvalidEmail();
        String actualInvalidEmail = getEmailFieldError();
        Assert.assertEquals(actualInvalidEmail, invalidEmailText, "Email Error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationPasswordField() {
        enterInvalidPassword();
        String actualInvalidPassword = getPasswordFieldError();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationRepeatPasswordField() {
        enterInvalidRepeatPassword();
        String actualInvalidPasswordRepeat = getRepeatPasswordFieldError();
        Assert.assertEquals(actualInvalidPasswordRepeat, invalidPasswordRepeatText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationSecuritySection() {
        JavascriptExecutor jsx = selectSecurityQuestion();
        clickOnAnswerControlField(jsx);
        checkThatRegisterButtonIsDisabled();
        String actualAnswerError = getAnswerControlFieldError();
        Assert.assertEquals(actualAnswerError, answerErrorText, "Error text doesn't match");
    }

    @Test
    public void userRegistration() {
        inputValidEmail();
        inputValidPassword();
        repeatValidPasswordInput();
        selectOptionFromList();
        inputAnswerControlText();
        clickOnRegisterButton();
        checkIfRegistrationFormIsNotPresent();
    }

    private String getEmailFieldError() {
        return getDriver().findElement(By.xpath("//*[contains(text(),'Email address')]")).getAttribute("innerText").trim();
    }

    private void enterInvalidEmail() {
        System.out.println("Negative case for Email field");
        getDriver().findElement(By.id("emailControl")).sendKeys(invalidEmail);
        getDriver().findElement(By.id("registration-form")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Email address')]")));
    }

    private String getPasswordFieldError() {
        return getDriver().findElement(By.xpath("//*[contains(text(),'Password must be')]")).getAttribute("innerText").trim();
    }

    private void enterInvalidPassword() {
        System.out.println("Negative case for Password field");
        getDriver().findElement(By.id("passwordControl")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("registration-form")).click();
    }

    private String getRepeatPasswordFieldError() {
        return getDriver().findElement(By.xpath("//*[contains(text(),'Passwords do not match')]")).getAttribute("innerText").trim();
    }

    private void enterInvalidRepeatPassword() {
        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(invalidPasswordRepeat);
        getDriver().findElement(By.id("registration-form")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Passwords do not match')]")));
    }

    private String getAnswerControlFieldError() {
        getDriver().findElement(By.xpath("//*[@id='mat-hint-3']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),' Please provide an answer to your security question')]")));
        return getDriver().findElement(By.xpath("//*[contains(text(),' Please provide an answer to your security question')]")).getAttribute("innerText").trim();
    }

    private void checkThatRegisterButtonIsDisabled() {
        System.out.println("Attribute 'disabled':" + getDriver().findElement(By.id("registerButton")).getAttribute("disabled"));
    }

    private void clickOnAnswerControlField(JavascriptExecutor jsx) {
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(By.id("securityAnswerControl")));
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("securityAnswerControl"))));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("securityAnswerControl"))).click();
    }

    private JavascriptExecutor selectSecurityQuestion() {
        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        getDriver().navigate().refresh();
        System.out.println("Negative case for Security section");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Selection list for the security question']")));
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//mat-select[@aria-label = 'Selection list for the security question']"))).click().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'maiden name')]")));
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(By.xpath("//*[contains(text(),'maiden name')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'maiden name')]")).click();
        return jsx;
    }

    private void checkIfRegistrationFormIsNotPresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        System.out.println("Check if the registration form isn't present");
        Assert.assertFalse(existsElement(id), "registration form check");
    }

    private void clickOnRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.cssSelector("[aria-label='Button to complete the registration'] span"))));
        getDriver().findElement(By.cssSelector("[aria-label='Button to complete the registration'] span")).click();
    }

    private void selectOptionFromList() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//mat-select[@aria-label = 'Selection list for the security question']"))).click().perform();
        getDriver().findElement(By.xpath("//*[contains(text(),'maiden name')]")).click();
    }

    private void inputAnswerControlText() {
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(maidenName);
    }

    private void repeatValidPasswordInput() {
        getDriver().findElement(By.id("repeatPasswordControl")).clear();
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(password);
    }

    private void inputValidPassword() {
        getDriver().findElement(By.id("passwordControl")).clear();
        getDriver().findElement(By.id("passwordControl")).sendKeys(password);
    }

    private void inputValidEmail() {
        System.out.println("Registration");
        getDriver().manage().addCookie(new Cookie("cookieconsent_status", "dismiss"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        getDriver().navigate().refresh();
        getDriver().findElement(By.id("emailControl")).clear();
        getDriver().findElement(By.id("emailControl")).sendKeys(email);
    }

    private boolean existsElement(String id) {
        try {
            getDriver().findElement(By.id(id));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @AfterClass
    public void AfterClass() {
       WebDriverSingleton.closeDriver();
    }
}
