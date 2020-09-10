//Navigate to Juice Shop  http://18.217.145.6/.
//Create a test for customer sign-up
//Create tests for fields validation on the register page
//Advanced:
//Create test for login
//Create at least 3 negative tests for login

package io.ctdev;

import io.ctdev.framework.webDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.webDriverSingleton.getDriver;

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


    @BeforeClass
    public void BeforeClass() {
        getDriver().get("http://18.217.145.6/");
        getDriver().findElement(By.cssSelector("[class*='close-dialog']")).click();
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();
        getDriver().findElement(By.id("navbarLoginButton")).click();
        getDriver().findElement(By.id("newCustomerLink")).click();
    }

    @Test
    public void NegativeCasesForRegistrationEmailField() throws InterruptedException {
        System.out.println("Negative case for Email field");
        getDriver().findElement(By.id("emailControl")).sendKeys(invalidEmail);
        getDriver().findElement(By.id("registration-form")).click(); //clicking on any area to see an error
        Thread.sleep(2000);
        String actualInvalidEmail = getDriver().findElement(By.xpath("//*[contains(text(),'Email address')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidEmail, invalidEmailText, "Email Error text doesn't match");
    }

    @Test
    public void NegativeCasesForRegistrationPasswordField() {
        System.out.println("Negative case for Password field");
        getDriver().findElement(By.id("passwordControl")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("registration-form")).click();
        String actualInvalidPassword = getDriver().findElement(By.xpath("//*[contains(text(),'Password must be')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");
    }

    @Test
    public void NegativeCasesForRegistrationRepeatPasswordField() throws InterruptedException {
        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(invalidPasswordRepeat);
        getDriver().findElement(By.id("registration-form")).click();
        Thread.sleep(2000);
        String actualInvalidPasswordRepeat = getDriver().findElement(By.xpath("//*[contains(text(),' Passwords do not match')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPasswordRepeat, invalidPasswordRepeatText, "Password error text doesn't match");
    }

    @Test
    public void NegativeCasesForRegistrationSecuritySection() throws InterruptedException {
        System.out.println("Negative case for Security section");
        getDriver().findElement(By.id("mat-select-1")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//*[contains(text(),'favorite pet')]")).click();
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(" ");
        System.out.println("Attribute 'disabled':" + getDriver().findElement(By.id("registerButton")).getAttribute("disabled"));
    }

    @Test
    public void UserRegistration() {

        System.out.println("Registration");
        getDriver().findElement(By.id("emailControl")).clear();
        getDriver().findElement(By.id("emailControl")).sendKeys(email);
        getDriver().findElement(By.id("passwordControl")).clear();
        getDriver().findElement(By.id("passwordControl")).sendKeys(password);
        getDriver().findElement(By.id("repeatPasswordControl")).clear();
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(password);
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(petName);
        getDriver().findElement(By.id("registerButton")).click();
    }

    @AfterClass
    public void AfterClass() {
        webDriverSingleton.closeDriver();
    }
}
