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
    private Customer customer1;
    private LoginPage loginPage;
    private WebDriver driver = getDriver();

    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 3);
        loginPage = new LoginPage(driver);
        loginPage.openPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
    }


    @Test
    public void verifyUserCanNotLogInWithInvalidCredentials() {
        loginPage.clearEmailAndPasswordField();
        customer1 = Customer.newBuilder().withName("test@test.com////").withPassword("aaaaa").build();
        loginPage.logIn(customer1.getEmail(),customer1.getPassword());
        loginPage.clickOnLoginButton();
        String actualLoginError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void verifyUserCanNotLogInWithFieldsFilledInSpaces() {
        loginPage.clearEmailAndPasswordField();
        loginPage.enterEmptyEmailAndPasswordString();
        loginPage.clickOnLoginButton();

        String actualError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualError, loginErrorText, "Error text doesn't match");
    }

    @Test
    public void verifyUserCanNotLogInWithEmptyFields() {
        loginPage.enterEmptyEmailAndPasswordString();
        loginPage.clearEmailAndPasswordField();
        loginPage.clickOnLoginButton();
        String errorLogin = loginPage.getInvalidEmailError();
        Assert.assertEquals(errorLogin, loginErrorText, "Error text doesn't match");
    }

    @AfterClass
    public void AfterClass() {
        //public void userIsAbleToLoginAfterRegistration() { //moved this test here because it was run firstly and failed all tests!
            loginPage.clearEmailAndPasswordField();
            customer = Customer.newBuilder().withName("test123@gmail.com").withPassword("123456789Test!").build();
            loginPage.logIn(customer.getEmail(),customer.getPassword());
            String actualUserName = loginPage.getCurrentUserName();
            Assert.assertEquals(actualUserName, customer.getEmail(), "User name doesn't match");
       // }
        WebDriverSingleton.closeDriver();
    }

}
