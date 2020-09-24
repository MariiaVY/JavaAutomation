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
    public String petName = "Tom";
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
        System.out.println("Negative case for Email field");
        getDriver().findElement(By.id("emailControl")).sendKeys(invalidEmail);
        getDriver().findElement(By.id("registration-form")).click(); //clicking on any area to see an error

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Email address')]")));

        String actualInvalidEmail = getDriver().findElement(By.xpath("//*[contains(text(),'Email address')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidEmail, invalidEmailText, "Email Error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationPasswordField() {
        System.out.println("Negative case for Password field");
        getDriver().findElement(By.id("passwordControl")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("registration-form")).click();
        String actualInvalidPassword = getDriver().findElement(By.xpath("//*[contains(text(),'Password must be')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationRepeatPasswordField() {
        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(invalidPasswordRepeat);
        getDriver().findElement(By.id("registration-form")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Passwords do not match')]")));

        String actualInvalidPasswordRepeat = getDriver().findElement(By.xpath("//*[contains(text(),'Passwords do not match')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPasswordRepeat, invalidPasswordRepeatText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationSecuritySection() {
        System.out.println("Negative case for Security section");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Selection list for the security question']"))).click();
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//mat-select[@aria-label = 'Selection list for the security question']"))).click().perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'favorite pet')]")));

        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(By.xpath("//*[contains(text(),'favorite pet')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'favorite pet')]")).click();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(By.id("securityAnswerControl")));

        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("securityAnswerControl"))));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("securityAnswerControl"))).click();

        System.out.println("Attribute 'disabled':" + getDriver().findElement(By.id("registerButton")).getAttribute("disabled"));
        getDriver().findElement(By.xpath("//*[@id='mat-hint-3']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),' Please provide an answer to your security question')]")));

        String actualAnswerError = getDriver().findElement(By.xpath("//*[contains(text(),' Please provide an answer to your security question')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualAnswerError, answerErrorText, "Error text doesn't match");
    }

    @Test
    public void userRegistration() {
        System.out.println("Registration");
        getDriver().findElement(By.id("emailControl")).clear();
        getDriver().findElement(By.id("emailControl")).sendKeys(email);
        getDriver().findElement(By.id("passwordControl")).clear();
        getDriver().findElement(By.id("passwordControl")).sendKeys(password);
        getDriver().findElement(By.id("repeatPasswordControl")).clear();
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(password);
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(petName);
        getDriver().findElement(By.id("registerButton")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        System.out.println("Check if the registration form isn't present");
        Assert.assertFalse(existsElement(id), "registration form check");
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
