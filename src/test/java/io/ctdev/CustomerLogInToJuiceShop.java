//Advanced:
//Create test for login
//Create at least 3 negative tests for login
package io.ctdev;

import io.ctdev.framework.WebDriverSingleton;
import io.ctdev.framework.model.Customer;
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

    public String loginErrorText = "Invalid email or password.";
    public String emptyString = "                 ";
    public String invalidPassword = "aaaaa";
    public String invalidEmail = "test@test.com////";
    WebDriverWait wait;
    private Customer customer;

    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 3);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
        customer = Customer.newBuilder().withName("test123@gmail.com").withPassword("123456789Test!").build();
    }

    @Test
    public void negativeCaseLogIn1() {
        enterInvalidEmail();
        enterInvalidPassword();
        clickOnLoginButton();
        String actualLoginError = getInvalidEmailError();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void negativeCaseLogIn2() {
        clearEmailField();
        enterEmptyEmailString();
        clearPasswordField();
        enterEmptyPasswordString();
        clickOnLoginButton();

        String actualError = getInvalidEmailError();
        Assert.assertEquals(actualError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void negativeCaseLogIn3() {
        clearEmailField();
        clearPasswordField();
        clickOnLoginButton();
        String errorLogin = getInvalidEmailError();
        Assert.assertEquals(errorLogin, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void userIsAbleToLoginAfterRegistration() {

        enterUserEmail();
        enterUserPassword();
        clickOnLoginButton();
        String actualUserName = getCurrentUserName();
        Assert.assertEquals(actualUserName, customer.getEmail(), "User name doesn't match");

    }

    private void enterInvalidPassword() {
        getDriver().findElement(By.id("password")).sendKeys(invalidPassword);
    }

    private void enterInvalidEmail() {
        getDriver().findElement(By.id("email")).sendKeys(invalidEmail);
    }

    private void clickOnLoginButton() {
        getDriver().findElement(By.id("loginButton")).click();
    }

    private void enterEmptyPasswordString() {
        getDriver().findElement(By.id("password")).sendKeys(emptyString);
    }

    private void enterEmptyEmailString() {
        getDriver().findElement(By.id("email")).sendKeys(emptyString);
    }

    private String getInvalidEmailError() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Invalid email')]")));
        return getDriver().findElement(By.xpath("//*[contains(text(),'Invalid email')]")).getAttribute("innerText").trim();
    }

    private void clearPasswordField() {
        getDriver().findElement(By.id("password")).clear();
    }

    private void clearEmailField() {
        System.out.println("Negative case for login section");
        getDriver().findElement(By.id("email")).clear();
    }

    private String getCurrentUserName() {
        getDriver().findElement(By.id("navbarAccount")).click();
        WebElement userNameElement =  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Go to user profile'] span")));

        return userNameElement.getAttribute("innerText").trim();
    }

    private void enterUserPassword() {
        getDriver().findElement(By.id("password")).sendKeys(customer.getPassword());
    }

    private void enterUserEmail() {
        System.out.println("Log in after registration");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        getDriver().findElement(By.id("email")).sendKeys(customer.getEmail());
    }

    @AfterClass
    public void AfterClass() {
        WebDriverSingleton.closeDriver();
    }

}
