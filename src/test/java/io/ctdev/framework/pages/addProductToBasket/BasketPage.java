package io.ctdev.framework.pages.addProductToBasket;

import io.ctdev.framework.config.TestConfig;
import io.ctdev.framework.pages.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class BasketPage extends AbstractPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private By closeDialogElement = By.xpath("//*[@aria-label='Close Dialog']");
    private By itemPriceElement = By.xpath("//*[@class='container mat-typography']//*[@class='item-price']");
    private By productInfoElement = By.xpath("//*[contains(text(),'Monkeys love it the most')]");
    private By productTitleElement = By.xpath("//*[@class='container mat-typography']//h1");
    private By productContainerElement = By.xpath("//*[@class='table-container custom-slate']");
    private By productInfoTitleElement = By.xpath("//*[@alt='Banana Juice (1000ml)']");
    private By dialogContentElement = By.xpath("//*[@class='mat-dialog-content']");
    private By overlayElement = By.xpath("//*[@class='cdk-overlay-pane']");
    private By addedProductPriceElement = By.xpath("//*[@class='mat-cell cdk-cell cdk-column-price mat-column-price ng-star-inserted']");
    private By addedProductNameElement = By.xpath("//*[@class='mat-row cdk-row ng-star-inserted']//*[@role='gridcell'][2]");
    private By basketTitleElement = By.xpath("//*[@class='mat-card mat-focus-indicator mat-elevation-z6']//h1");
    private By openBasketElement = By.xpath("//*[@aria-label='Show the shopping cart']");
    private By basketInnerElement = By.xpath("//*[@class='ng-star-inserted']//h1");
    private By addedProductTitleElement = By.xpath("//*[contains(text(),'Placed Banana Juice')]");
    private By addProductToBasketElement = By.xpath("//*[@class='mat-grid-tile ng-star-inserted'][3]//button[@aria-label='Add to Basket']");
    private By totalPriceElement = By.xpath("//*[@id='price']");
    private By soldOutErrorElement = By.xpath("//*[@class='mat-simple-snackbar ng-star-inserted']");
    private By addSoldOutProductElement = By.xpath("//*[@class='mat-grid-tile ng-star-inserted'][4]//button[@aria-label='Add to Basket']");
    private By soldOutProductElement = By.xpath("//*[@class='ribbon ribbon-top-left ribbon-sold ng-star-inserted']/span");
    private By paginatorElement = By.cssSelector("[class*=mat-paginator-navigation-next]");
    private By nextPageElement = By.xpath("//*[@class='mat-paginator-range-actions']//button [2]");
    private By dismissCookieElement = By.xpath("//*[@aria-label='dismiss cookie message']");
    private By homePageElement = By.xpath("//*[@alt='OWASP Juice Shop']");
    private By dismissCookie = By.xpath("//*[@class='cc-btn cc-dismiss']");
    private String soldOutProductPath = "//*[contains(text(),'Juice Shop Coaster')]";


    public BasketPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(TestConfig.cfg.baseUrl() + "#/login");
    }

    @Step("Close the dialog")
    public void closeTheDialog() {
        wait.until(ExpectedConditions.presenceOfElementLocated(closeDialogElement)).click();
    }

    @Step("Click on dismiss")
    public void clickOnDismiss() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
    }

    @Step("Check the product price")
    public String checkProductPrice() {
        return getDriver().findElement(itemPriceElement).getAttribute("innerText").trim();
    }

    @Step("Check the product information")
    public String checkProductInfo() {
        return getDriver().findElement(productInfoElement).getAttribute("innerText").trim();
    }

    @Step("Check the product title")
    public String checkProductTitle() {
        WebElement titleElementText = wait.until(ExpectedConditions.presenceOfElementLocated(productTitleElement));
        return titleElementText.getAttribute("innerText").trim();
    }

    @Step("Click on the product")
    public void clickOnBananaJuiceProductIcon() {
        System.out.println("Information about product");
        wait.until(ExpectedConditions.presenceOfElementLocated(productContainerElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(productInfoTitleElement)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(dialogContentElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(overlayElement));
    }

    @Step("Verify added product price")
    public String verifyAddedProductPrice() {
        return getDriver().findElement(addedProductPriceElement).getAttribute("innerText").trim();
    }

    @Step("Verify added product name")
    public String verifyAddedProductName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productInfoTitleElement));
        return getDriver().findElement(addedProductNameElement).getAttribute("innerText").trim();
    }

    @Step("Check basket title")
    public void checkBasketTitle(String basketTitle) {
        System.out.println("Verifying added product in the basket");
        String actualBasketTitle = getDriver().findElement(basketTitleElement).getAttribute("innerText").trim();
        Assert.assertEquals(actualBasketTitle, basketTitle, "Basket title doesn't match");
    }

    @Step("Navigate to basket")
    public void navigateToBasket() {
        getDriver().findElement(openBasketElement).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(basketInnerElement));
    }

    @Step("Check added product title")
    public String checkAddedProductTitle() {
        WebElement addedProductElement = wait.until(ExpectedConditions.presenceOfElementLocated(addedProductTitleElement));
        return addedProductElement.getAttribute("innerText").trim();
    }

    @Step("Add product to the basket")
    public void addProductToBasket() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addProductToBasketElement)).click();
    }

    @Step("Verify sold out Product Is Not present in the basket")
    public void verifySoldOutProductIsNotInTheBasket() {
        System.out.println("Verifying sold out product isn't present in the basket");
        Assert.assertFalse(existsElement(soldOutProductPath), "Sold out product is present");
    }

    @Step("Verify basket total price is null")
    public void verifyBasketTotalPriceIsNull(String totalPriceSoldOutProduct) {
        String actualTotalPrice = getDriver().findElement(totalPriceElement).getAttribute("innerText").trim();
        Assert.assertEquals(actualTotalPrice, totalPriceSoldOutProduct, "Product total price doesn't match");
    }

    @Step("Verify sold out product error")
    public String verifySoldOutProductError() {
        System.out.println("Verifying error is shown");
        WebElement soldOutError = wait.until(ExpectedConditions.presenceOfElementLocated(soldOutErrorElement));
        return soldOutError.getAttribute("innerText").trim();
    }

    @Step("Verify if user can add sold out product to the basket")
    public void addSoldOutProductToBasket() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addSoldOutProductElement)).click();
    }

    @Step("Find sold out product")
    public String findSoldOutProduct() {
        WebElement soldOutElement = wait.until(ExpectedConditions.presenceOfElementLocated(soldOutProductElement));
        return soldOutElement.getAttribute("innerText").trim();
    }

    @Step("Navigate to the next page")
    public void navigateToNextPage() {
        getDriver().manage().addCookie(new Cookie("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6NTAsInVzZXJuYW1lIjoiIiwiZW1haWwiOiJ0ZXN0MTIzQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiODRhYTExM2NhOWQ1MjMzYWNmZjZhNjI3YjVmNmIwM2UiLCJyb2xlIjoiY3VzdG9tZXIiLCJkZWx1eGVUb2tlbiI6IiIsImxhc3RMb2dpbklwIjoiMC4wLjAuMCIsInByb2ZpbGVJbWFnZSI6Ii9hc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHQuc3ZnIiwidG90cFNlY3JldCI6IiIsImlzQWN0aXZlIjp0cnVlLCJjcmVhdGVkQXQiOiIyMDIwLTEwLTE3IDE2OjMxOjU4LjUzMCArMDA6MDAiLCJ1cGRhdGVkQXQiOiIyMDIwLTEwLTE3IDE2OjMxOjU4LjUzMCArMDA6MDAiLCJkZWxldGVkQXQiOm51bGx9LCJpYXQiOjE2MDM4ODA5NzYsImV4cCI6MTYwMzg5ODk3Nn0.d-Onhh63s31Sy9KqrK9RTZhdgrL-Z6Xoo8mbBLCAaM8DED6Bf6bHVBgdWDBZ_2EMRtj1qdasa42FcYICnfvneFfwRXzStGCEKpMfoMRfiHzs6T4hH20BcxKBpDJREWzUo39QG-KW8TegpMI3yD-PPOs5L7ShR1lakwtUoTHL-g4"));
        getDriver().manage().addCookie(new Cookie("language", "en"));
       // getDriver().findElement(dismissCookie).click();
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(paginatorElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(nextPageElement));
        getDriver().findElement(nextPageElement).click();
    }

    @Step("Dismiss cookie message")
    public void dismissCookieMessage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(dismissCookieElement)).click();
        wait.until(ExpectedConditions.invisibilityOf(getDriver().findElement(dismissCookieElement)));
    }

    @Step("Navigate to the Home Page")
    public void navigateToHomePage() {
        getDriver().manage().addCookie(new Cookie("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6NTAsInVzZXJuYW1lIjoiIiwiZW1haWwiOiJ0ZXN0MTIzQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiODRhYTExM2NhOWQ1MjMzYWNmZjZhNjI3YjVmNmIwM2UiLCJyb2xlIjoiY3VzdG9tZXIiLCJkZWx1eGVUb2tlbiI6IiIsImxhc3RMb2dpbklwIjoiMC4wLjAuMCIsInByb2ZpbGVJbWFnZSI6Ii9hc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHQuc3ZnIiwidG90cFNlY3JldCI6IiIsImlzQWN0aXZlIjp0cnVlLCJjcmVhdGVkQXQiOiIyMDIwLTEwLTE3IDE2OjMxOjU4LjUzMCArMDA6MDAiLCJ1cGRhdGVkQXQiOiIyMDIwLTEwLTE3IDE2OjMxOjU4LjUzMCArMDA6MDAiLCJkZWxldGVkQXQiOm51bGx9LCJpYXQiOjE2MDM4ODA5NzYsImV4cCI6MTYwMzg5ODk3Nn0.d-Onhh63s31Sy9KqrK9RTZhdgrL-Z6Xoo8mbBLCAaM8DED6Bf6bHVBgdWDBZ_2EMRtj1qdasa42FcYICnfvneFfwRXzStGCEKpMfoMRfiHzs6T4hH20BcxKBpDJREWzUo39QG-KW8TegpMI3yD-PPOs5L7ShR1lakwtUoTHL-g4"));
        getDriver().manage().addCookie(new Cookie("language", "en"));
        getDriver().navigate().refresh();
        getDriver().findElement(homePageElement).click();
    }

    public boolean existsElement(String soldOutProductPath) {
        try {
            getDriver().findElement(By.xpath(soldOutProductPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
