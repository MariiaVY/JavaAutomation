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

    public String Email = "test121!@gmail.com";  //change email each time when u run the test
    public String Password = "123456789Test!";
    public String PetName = "Tom";
    public String InvalidEmail = "test@test.com////";
    public String InvalidEmailText = "Email address is not valid.";
    public String invalidPassword = "aaaaa";
    public String invalidPasswordText = "Password must be 5-20 characters long.";
    public String InvalidPasswordRepeat = "322";
    public String InvalidPasswordRepeatText = "Passwords do not match";
    public String LoginErrorText = "Invalid email or password.";
    public String EmptyString = "                 ";


    @BeforeClass
    public void BeforeClass() {
        getDriver().get("http://18.217.145.6/");
        getDriver().findElement(By.cssSelector("[class*='close-dialog']")).click();
    }

    @Test
    public void UserIsAbleToLoginToJuiceShop() throws InterruptedException {

        System.out.println("Clicking on Sign In button");
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();

        System.out.println("Clicking on Login button");
        getDriver().findElement(By.id("navbarLoginButton")).click();

        System.out.println("Not yet a customer?");
        getDriver().findElement(By.id("newCustomerLink")).click();

        System.out.println("Negative case for Email field");
        getDriver().findElement(By.id("emailControl")).sendKeys(InvalidEmail);
        getDriver().findElement(By.id("registration-form")).click(); //clicking on any area to see an error
        Thread.sleep(2000);
        String actualInvalidEmail = getDriver().findElement(By.xpath("//*[contains(text(),'Email address')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidEmail, InvalidEmailText, "Email Error text doesn't match");


        System.out.println("Negative case for Password field");
        getDriver().findElement(By.id("passwordControl")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("registration-form")).click();
        String actualInvalidPassword = getDriver().findElement(By.xpath("//*[contains(text(),'Password must be')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");

        System.out.println("Negative case for Repeat Password field");
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(InvalidPasswordRepeat);
        getDriver().findElement(By.id("registration-form")).click();
        Thread.sleep(2000);
        String actualInvalidPasswordRepeat = getDriver().findElement(By.xpath("//*[contains(text(),' Passwords do not match')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualInvalidPasswordRepeat, InvalidPasswordRepeatText, "Password error text doesn't match");

        System.out.println("Negative case for Security section");
        getDriver().findElement(By.id("mat-select-1")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//*[contains(text(),'favorite pet')]")).click();
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(" ");
        System.out.println("Attribute 'disabled':" + getDriver().findElement(By.id("registerButton")).getAttribute("disabled"));


        System.out.println("Registration");
        getDriver().findElement(By.id("emailControl")).clear();
        getDriver().findElement(By.id("emailControl")).sendKeys(Email);
        getDriver().findElement(By.id("passwordControl")).clear();
        getDriver().findElement(By.id("passwordControl")).sendKeys(Password);
        getDriver().findElement(By.id("repeatPasswordControl")).clear();
        getDriver().findElement(By.id("repeatPasswordControl")).sendKeys(Password);
        getDriver().findElement(By.id("securityAnswerControl")).sendKeys(PetName);
        getDriver().findElement(By.id("registerButton")).click();
        Thread.sleep(3000);

        //case #2

        System.out.println("Negative case for login section 1");
        getDriver().findElement(By.id("email")).sendKeys(InvalidEmail);
        getDriver().findElement(By.id("password")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("loginButton")).click();
        Thread.sleep(2000);

        String actualLoginError = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualLoginError, LoginErrorText, "Error text doesn't match");

        System.out.println("Negative case for login section 2");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("email")).sendKeys(EmptyString);
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("password")).sendKeys(EmptyString);
        getDriver().findElement(By.id("loginButton")).click();
        Assert.assertEquals(actualLoginError, LoginErrorText, "Error text doesn't match");

        System.out.println("Negative case for login section 3");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("loginButton")).click();
        Assert.assertEquals(actualLoginError, LoginErrorText, "Error text doesn't match");

        System.out.println("Log in after registration");
        Thread.sleep(2000);
        getDriver().findElement(By.id("email")).sendKeys(Email);
        getDriver().findElement(By.id("password")).sendKeys(Password);
        getDriver().findElement(By.id("loginButton")).click();

        System.out.println("Assert user Email");
        getDriver().findElement(By.id("navbarAccount")).click();
        Thread.sleep(3000);
        String actualUserName = getDriver().findElement(By.cssSelector("[aria-label='Go to user profile'] span")).getAttribute("innerText").trim();
        Assert.assertEquals(actualUserName, Email, "User name doesn't match");

    }

    @AfterClass
    public void AfterClass() {
        webDriverSingleton.closeDriver();
    }
}
