//Navigate to Juice Shop  http://18.217.145.6/.
//Create a test for customer sign-up
//Create tests for fields validation on the register page

package io.ctdev.test;

import io.ctdev.framework.WebDriverSingleton;
import io.ctdev.framework.model.Customer;
import io.ctdev.framework.pages.signIn.SignInPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class CustomerSignUpToJuiceShop {

    public String invalidEmailText = "Email address is not valid.";
    public String invalidPasswordText = "Password must be 5-20 characters long.";
    public String invalidPasswordRepeatText = "Passwords do not match";
    public String answerErrorText = "Please provide an answer to your security question.";
    WebDriverWait wait;
    private Customer customer;
    private Customer customer1;
    private SignInPage signInPage;
    private WebDriver driver = getDriver();


    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 7);
        signInPage = new SignInPage(driver);
        signInPage.openPage();
        getDriver().findElement(By.cssSelector("[class*='close-dialog']")).click();
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();
        getDriver().findElement(By.id("navbarLoginButton")).click();
        getDriver().findElement(By.id("newCustomerLink")).click();
        customer = Customer.newBuilder().withName("test" + System.currentTimeMillis() + "@gmail.com").withPassword("123456789Test!").build();
        customer1 = Customer.newBuilder().withName("test@test.com////").withPassword("aaaaa").build();
    }

    @Test
    public void negativeCasesForRegistrationEmailField() {
        signInPage.enterInvalidEmail(customer1.getEmail());
        String actualInvalidEmail = signInPage.getEmailFieldError();
        Assert.assertEquals(actualInvalidEmail, invalidEmailText, "Email Error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationPasswordField() {
        signInPage.enterInvalidPassword(customer1.getPassword());
        String actualInvalidPassword = signInPage.getPasswordFieldError();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationRepeatPasswordField() {
        signInPage.enterInvalidRepeatPassword();
        String actualInvalidPasswordRepeat = signInPage.getRepeatPasswordFieldError();
        Assert.assertEquals(actualInvalidPasswordRepeat, invalidPasswordRepeatText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationSecuritySection() {
        signInPage.selectSecurityQuestion();
        signInPage.clickOnAnswerControlField();
        signInPage.checkThatRegisterButtonIsDisabled();
        String actualAnswerError = signInPage.getAnswerControlFieldError();
        Assert.assertEquals(actualAnswerError, answerErrorText, "Error text doesn't match");
    }

    @Test
    public void userRegistration() {
        signInPage.inputValidEmail(customer.getEmail());
        signInPage.inputValidPassword(customer.getPassword());
        signInPage.repeatValidPasswordInput(customer.getPassword());
        signInPage.selectOptionFromList();
        signInPage.inputAnswerControlText();
        signInPage.clickOnRegisterButton();
        signInPage.checkIfRegistrationFormIsNotPresent();
    }

    @AfterClass
    public void AfterClass() {
       WebDriverSingleton.closeDriver();
    }
}
