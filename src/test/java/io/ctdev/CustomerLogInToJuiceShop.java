//Advanced:
//Create test for login
//Create at least 3 negative tests for login
package io.ctdev;

import io.ctdev.framework.WebDriverSingleton;
import io.ctdev.framework.model.Customer;
import io.ctdev.framework.pages.login.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class CustomerLogInToJuiceShop {

    public String loginErrorText = "Invalid email or password.";
    WebDriverWait wait;
    private Customer customer;
    private LoginPage loginPage;
    private WebDriver driver = getDriver();

    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 3);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
        customer = Customer.newBuilder().withName("test123@gmail.com").withPassword("123456789Test!").withEmptyString("                 ").withInvalidEmail("test@test.com////").withInvalidPassword("aaaaa").build();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void negativeCaseLogIn1() {
        loginPage.enterInvalidEmail(customer.getInvalidEmail());
        loginPage.enterInvalidPassword(customer.getInvalidPassword());
        loginPage.clickOnLoginButton();
        String actualLoginError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void negativeCaseLogIn2() {
        loginPage.clearEmailField();
        loginPage.enterEmptyEmailString(customer.getEmptyString());
        loginPage.clearPasswordField();
        loginPage.enterEmptyPasswordString(customer.getEmptyString());
        loginPage.clickOnLoginButton();

        String actualError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void negativeCaseLogIn3() {
        loginPage.clearEmailField();
        loginPage.clearPasswordField();
        loginPage.clickOnLoginButton();
        String errorLogin = loginPage.getInvalidEmailError();
        Assert.assertEquals(errorLogin, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void userIsAbleToLoginAfterRegistration() {

        loginPage.enterUserEmail(customer.getEmail());
        loginPage.enterUserPassword(customer.getPassword());
        loginPage.clickOnLoginButton();
        String actualUserName = loginPage.getCurrentUserName();
        Assert.assertEquals(actualUserName, customer.getEmail(), "User name doesn't match");
    }

    @AfterClass
    public void AfterClass() {
        WebDriverSingleton.closeDriver();
    }

}
