//Advanced:
//Create test for login
//Create at least 3 negative tests for login
package io.ctdev.test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import io.ctdev.framework.model.Customer;
import io.ctdev.framework.pages.login.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

@Epic("Sign In/Sign Up")
@Story("Login")
public class CustomerLogInToJuiceShop extends BaseTest {

    public String loginErrorText = "Invalid email or password.";
    private Customer customer;
    private Customer customer1;
    private LoginPage loginPage;

    @BeforeClass
    public void BeforeClass() {
        loginPage = new LoginPage(driver);
        loginPage.openPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
    }


    @Test
    @Description("Verify user can't logIn with invalid credentials")
    public void verifyUserCanNotLogInWithInvalidCredentials() {
        loginPage.openPage();
        loginPage.clearEmailAndPasswordField();
        customer1 = Customer.newBuilder().withName("test@test.com////").withPassword("aaaaa").build();
        loginPage.logIn(customer1.getEmail(), customer1.getPassword());
        loginPage.clickOnLoginButton();
        String actualLoginError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualLoginError, loginErrorText, "Error text doesn't match");
    }

    @Test
    @Description("Verify user can't logIn with fields filled in spaces")
    public void verifyUserCanNotLogInWithFieldsFilledInSpaces() {
        loginPage.openPage();
        loginPage.clearEmailAndPasswordField();
        loginPage.enterEmptyEmailAndPasswordString();
        loginPage.clickOnLoginButton();

        String actualError = loginPage.getInvalidEmailError();
        Assert.assertEquals(actualError, loginErrorText, "Error text doesn't match");
    }

    @Test
    @Description("Verify user can't logIn with empty fields")
    public void verifyUserCanNotLogInWithEmptyFields() {
        loginPage.openPage();
        loginPage.enterEmptyEmailAndPasswordString();
        loginPage.clearEmailAndPasswordField();
        loginPage.clickOnLoginButton();
        String errorLogin = loginPage.getInvalidEmailError();
        Assert.assertEquals(errorLogin, loginErrorText, "Error text doesn't match");
    }

    @Test
    @Description("Verify user can logIn with valid credentials")
    public void userIsAbleToLoginAfterRegistration() {
        loginPage.clearEmailAndPasswordField();
        customer = Customer.newBuilder().withName("test123@gmail.com").withPassword("123456789Test!").build();
        loginPage.logIn(customer.getEmail(), customer.getPassword());
        String actualUserName = loginPage.getCurrentUserName();
        Assert.assertEquals(actualUserName, customer.getEmail(), "User name doesn't match");
        loginPage.logOut();
    }
}
