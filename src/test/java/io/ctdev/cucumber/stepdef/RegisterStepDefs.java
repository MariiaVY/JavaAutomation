package io.ctdev.cucumber.stepdef;

import io.ctdev.cucumber.pages.SignInPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class RegisterStepDefs {

    private SignInPage signInPage = new SignInPage(getDriver());

    @When("User opens registration page")
    public void userOpensRegistrationPage() {
        signInPage.closeDialog();
    }

    @When("User enters valid {string} and {string}")
    public void userEntersValidLoginAndPassword(String login, String password) {
        signInPage.inputValidEmail(login);
        signInPage.inputValidPassword(password);
    }

    @When("User repeats the {string}")
    public void userRepeatsThePassword(String password) {
        signInPage.repeatValidPasswordInput(password);
    }

    @When("User select question and {string}")
    public void userSelectQuestionAndAnswer(String answer) {
        signInPage.selectSecurityQuestion();
        signInPage.inputAnswerControlText(answer);
    }

    @When("User clicks on Register button")
    public void userClicksOnRegisterButton() {
        signInPage.clickOnRegisterButton();
    }

    @Then("User is registered and can log in to application")
    public void userIsRegisteredAndLoggedInToApplication() {
        signInPage.checkIfRegistrationFormIsNotPresent();
    }

}
