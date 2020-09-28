//Navigate to Juice Shop  http://18.217.145.6/.
//Create a test for customer sign-up
//Create tests for fields validation on the register page

package io.ctdev;

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
    private SignInPage signInPage;
    private WebDriver driver = getDriver();


    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 7);
        getDriver().get("http://18.217.145.6/");
        getDriver().findElement(By.cssSelector("[class*='close-dialog']")).click();
        WebElement element = getDriver().findElement(By.id("navbarAccount"));
        element.click();
        getDriver().findElement(By.id("navbarLoginButton")).click();
        getDriver().findElement(By.id("newCustomerLink")).click();
        signInPage = new SignInPage(driver);
        customer = Customer.newBuilder().withName("test" + System.currentTimeMillis() + "@gmail.com").withPassword("123456789Test!").withInvalidEmail("test@test.com////").withInvalidPassword("aaaaa").withInvalidPasswordRepeat("invalidPasswordRepeat").withMaidenName("Test").withId("registration-for").build();
    }

    @Test
    public void negativeCasesForRegistrationEmailField() {
        signInPage.enterInvalidEmail(customer.getInvalidEmail());
        String actualInvalidEmail = signInPage.getEmailFieldError();
        Assert.assertEquals(actualInvalidEmail, invalidEmailText, "Email Error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationPasswordField() {
        signInPage.enterInvalidPassword(customer.getInvalidPassword());
        String actualInvalidPassword = signInPage.getPasswordFieldError();
        Assert.assertEquals(actualInvalidPassword, invalidPasswordText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationRepeatPasswordField() {
        signInPage.enterInvalidRepeatPassword(customer.getInvalidPasswordRepeat());
        String actualInvalidPasswordRepeat = signInPage.getRepeatPasswordFieldError();
        Assert.assertEquals(actualInvalidPasswordRepeat, invalidPasswordRepeatText, "Password error text doesn't match");
    }

    @Test
    public void negativeCasesForRegistrationSecuritySection() {
        JavascriptExecutor jsx = signInPage.selectSecurityQuestion();
        signInPage.clickOnAnswerControlField(jsx);
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
        signInPage.inputAnswerControlText(customer.getMaidenName());
        signInPage.clickOnRegisterButton();
        signInPage.checkIfRegistrationFormIsNotPresent(customer.getId());
    }

    @AfterClass
    public void AfterClass() {
       WebDriverSingleton.closeDriver();
    }
}
