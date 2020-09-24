//Advanced:
//Create test for login
//Create at least 3 negative tests for login
package io.ctdev;

import io.ctdev.framework.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class CustomerLogInToJuiceShop {

    static String validEmail = "test123@gmail.com";
    public String password = "123456789Test!";
    public String loginErrorText = "Invalid email or password.";
    public String emptyString = "                 ";
    public String invalidPassword = "aaaaa";
    public String invalidEmail = "test@test.com////";
    WebDriverWait wait;

    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 3);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
    }

    @Test
    public void negativeCaseLogIn1() {
        System.out.println("Negative case for login section 1");
        getDriver().findElement(By.id("email")).sendKeys(invalidEmail);
        getDriver().findElement(By.id("password")).sendKeys(invalidPassword);
        getDriver().findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Invalid email')]")));

        String actualLoginError = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }
    @Test
    public void negativeCaseLogIn2() {
        System.out.println("Negative case for login section 2");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("email")).sendKeys(emptyString);
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("password")).sendKeys(emptyString);
        getDriver().findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Invalid email')]")));

        String actualError = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void negativeCaseLogIn3() {
        System.out.println("Negative case for login section 3");
        getDriver().findElement(By.id("email")).clear();
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Invalid email')]")));

        String errorLogin = getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
        Assert.assertEquals(errorLogin, loginErrorText, "Error text doesn't match");    }

    @Test
    public void userIsAbleToLoginAfterRegistration() {

        System.out.println("Log in after registration");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));

        getDriver().findElement(By.id("email")).sendKeys(validEmail);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("loginButton")).click();
        System.out.println("Assert user Email");
        getDriver().findElement(By.id("navbarAccount")).click();

        WebElement userNameElement =  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Go to user profile'] span")));

        String actualUserName = userNameElement.getAttribute("innerText").trim();
        Assert.assertEquals(actualUserName, validEmail, "User name doesn't match");

    }

    @AfterClass
    public void AfterClass() {
        WebDriverSingleton.closeDriver();
    }

}
