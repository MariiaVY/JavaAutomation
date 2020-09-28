//1. Write a test that verifies actual information about a Product. (Click on it, check title, description, etc)
//2. Add test that adds a product to basket and verifies that its added to the right place.
//3. Add test, that clicks on 2nd page, finds a product that sold out, clicks on “Add to Basket” and verifies that error is present on the page.

package io.ctdev;

import io.ctdev.framework.WebDriverSingleton;
import io.ctdev.framework.model.Customer;
import io.ctdev.framework.pages.addProductToBasket.BasketPage;
import io.ctdev.framework.pages.login.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import java.util.concurrent.TimeUnit;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class AddProductToBasket {
    private By juiceShopElement = By.xpath("//*[@alt='OWASP Juice Shop']");
    WebDriverWait wait;
    public String titleText = "Banana Juice (1000ml)";
    public String monkeysText = "Monkeys love it the most.";
    public String price = "1.99¤";
    public String productAddedText = "Placed Banana Juice (1000ml) into basket.";
    public String productName = "Banana Juice (1000ml)";
    public String soldOutTxt = "Sold Out";
    public String soldOutErrorText = "We are out of stock! Sorry for the inconvenience." + "\n" + "X";
    private Customer customer;
    private LoginPage loginPage;
    private BasketPage basketPage;
    private WebDriver driver = getDriver();

    @BeforeClass
    public void beforeClass() {
        wait = new WebDriverWait(getDriver(), 20);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
        loginPage = new LoginPage(driver);
        basketPage = new BasketPage(driver);
        customer = Customer.newBuilder().withName("test123@gmail.com").withPassword("123456789Test!").withBasketTitle("Your Basket").withSoldOutProductPath("//*[contains(text(),'Juice Shop Coaster')]").withTotalPriceSoldOutProduct("Total Price: 0¤").build();
        loginPage.enterUserEmail(customer.getEmail());
        loginPage.enterUserPassword(customer.getPassword());
        loginPage.clickOnLoginButton();
        getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void addingProductToTheBasket() {
        basketPage.addProductToBasket();
        String actualProductAddedText = basketPage.checkAddedProductTitle();
        Assert.assertEquals(actualProductAddedText, productAddedText, "The text doesn't match");
        basketPage.navigateToBasket();
        basketPage.checkBasketTitle(customer.getBasketTitle());
        String actualProductName = basketPage.verifyAddedProductName();
        Assert.assertEquals(actualProductName, productName, "Product Name doesn't match");
        String actualProductPrice = basketPage.verifyAddedProductPrice();
        Assert.assertEquals(actualProductPrice, price, "Product price doesn't match");
    }

    @Test
    public void addingSoldOutProductToTheBasket() {
        basketPage.navigateToHomePage();
        basketPage.dismissCookieMessage();
        basketPage.navigateToNextPage();

        String actualSoldOutText = basketPage.findSoldOutProduct();
        Assert.assertEquals(actualSoldOutText, soldOutTxt, "Text doesn't match");

        basketPage.addSoldOutProductToBasket();
        String actualSoldOutError = basketPage.verifySoldOutProductError();
        Assert.assertEquals(actualSoldOutError, soldOutErrorText, "Error text doesn't match");

        basketPage.navigateToBasket();
        basketPage.verifyBasketTotalPriceIsNull(customer.getTotalPriceSoldOutProduct());
        basketPage.verifySoldOutProductIsNotInTheBasket(customer.getSoldOutProductPath());
    }

    @Test
    public void verifyInformationAboutProduct() {

        basketPage.clickOnBananaJuiceProductIcon();
        String actualTitleText = basketPage.checkProductTitle();
        Assert.assertEquals(actualTitleText, titleText, "Title text doesn't match");

        String actualMonkeysText = basketPage.checkProductInfo();
        Assert.assertEquals(actualMonkeysText, monkeysText, "Monkeys text doesn't match");

        String actualPrice = basketPage.checkProductPrice();
        Assert.assertEquals(actualPrice, price, "Price doesn't match");
        basketPage.closeTheDialog();
    }

    @AfterMethod
    public void afterMethod() {
        getDriver().findElement(juiceShopElement).click();
    }

    @AfterClass
    public void afterClass() {
        WebDriverSingleton.closeDriver();
    }

}
