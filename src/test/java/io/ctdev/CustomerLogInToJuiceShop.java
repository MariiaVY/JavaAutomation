package io.ctdev;

import io.ctdev.framework.webDriverSingleton;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.webDriverSingleton.getDriver;

public class CustomerLogInToJuiceShop {

    static String validEmail = "test123@gmail.com";
    public String password = "123456789Test!";
    public String loginErrorText = "Invalid email or password.";
    public String emptyString = "                 ";
    public String invalidPassword = "aaaaa";
    public String invalidEmail = "test@test.com////";

    @BeforeClass
    public void BeforeClass() throws InterruptedException {
        getDriver().get("http://18.217.145.6/#/login");
        Thread.sleep(3000);
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
    }
    @Test
    public void NegativeCasesLogIn() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Negative case for login section 1");
        getDriver().findElement(By.id("email")).sendKeys(invalidEmail);
        getDriver().findElement(By.id("password")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("loginButton")).click();
        Thread.sleep(2000);

        String actualLoginError = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");

        System.out.println("Negative case for login section 2");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("email")).sendKeys(emptyString);
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("password")).sendKeys(emptyString);
        getDriver().findElement(By.id("loginButton")).click();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");

        System.out.println("Negative case for login section 3");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("loginButton")).click();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void UserIsAbleToLoginAfterRegistration() throws InterruptedException {

        System.out.println("Log in after registration");
        Thread.sleep(2000);
        getDriver().findElement(By.id("email")).sendKeys(validEmail);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("loginButton")).click();

        System.out.println("Assert user Email");
        getDriver().findElement(By.id("navbarAccount")).click();
        Thread.sleep(3000);
        String actualUserName = getDriver().findElement(By.cssSelector("[aria-label='Go to user profile'] span")).getAttribute("innerText").trim();
        Assert.assertEquals(actualUserName, validEmail, "User name doesn't match");

    }

    @AfterClass
    public void AfterClass() {
        webDriverSingleton.closeDriver();
    }

}
