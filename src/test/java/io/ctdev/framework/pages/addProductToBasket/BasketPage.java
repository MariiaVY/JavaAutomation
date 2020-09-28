package io.ctdev.framework.pages.addProductToBasket;

import io.ctdev.framework.config.testConfig;
import io.ctdev.framework.model.Customer;
import io.ctdev.framework.pages.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

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
    private By basketInnerElement =  By.xpath("//*[@class='ng-star-inserted']//h1");
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

    public BasketPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIME_OUT);
    }

    @Override
    public void openPage() {
        driver.get(testConfig.cfg.baseUrl() + "#/login");
    }

    public void closeTheDialog() {
        wait.until(ExpectedConditions.presenceOfElementLocated(closeDialogElement)).click();
    }

    public String checkProductPrice() {
        return getDriver().findElement(itemPriceElement).getAttribute("innerText").trim();
    }

    public String checkProductInfo() {
        return getDriver().findElement(productInfoElement).getAttribute("innerText").trim();
    }

    public String checkProductTitle() {
        WebElement titleElementText = wait.until(ExpectedConditions.presenceOfElementLocated(productTitleElement));
        return titleElementText.getAttribute("innerText").trim();
    }

    public void clickOnBananaJuiceProductIcon() {
        System.out.println("Information about product");
        wait.until(ExpectedConditions.presenceOfElementLocated(productContainerElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(productInfoTitleElement)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(dialogContentElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(overlayElement));
    }

    public String verifyAddedProductPrice() {
        return getDriver().findElement(addedProductPriceElement).getAttribute("innerText").trim();
    }

    public String verifyAddedProductName() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productInfoTitleElement));
        return getDriver().findElement(addedProductNameElement).getAttribute("innerText").trim();
    }

    public void checkBasketTitle(String basketTitle) {
        System.out.println("Verifying added product in the basket");
        String actualBasketTitle = getDriver().findElement(basketTitleElement).getAttribute("innerText").trim();
        Assert.assertEquals(actualBasketTitle, basketTitle, "Basket title doesn't match");
    }

    public void navigateToBasket() {
        getDriver().findElement(openBasketElement).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(basketInnerElement));
    }

    public String checkAddedProductTitle() {
        WebElement addedProductElement = wait.until(ExpectedConditions.presenceOfElementLocated(addedProductTitleElement));
        return addedProductElement.getAttribute("innerText").trim();
    }

    public void addProductToBasket() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addProductToBasketElement)).click();
    }

    public void verifySoldOutProductIsNotInTheBasket(String soldOutProductPath) {
        System.out.println("Verifying sold out product isn't present in the basket");
        Assert.assertFalse(existsElement(soldOutProductPath), "Sold out product is present");
    }

    public void verifyBasketTotalPriceIsNull(String totalPriceSoldOutProduct) {
        String actualTotalPrice = getDriver().findElement(totalPriceElement).getAttribute("innerText").trim();
        Assert.assertEquals(actualTotalPrice, totalPriceSoldOutProduct, "Product total price doesn't match");
    }

    public String verifySoldOutProductError() {
        System.out.println("Verifying error is shown");
        WebElement soldOutError = wait.until(ExpectedConditions.presenceOfElementLocated(soldOutErrorElement));
        return soldOutError.getAttribute("innerText").trim();
    }

    public void addSoldOutProductToBasket() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addSoldOutProductElement)).click();
    }

    public String findSoldOutProduct() {
        WebElement soldOutElement = wait.until(ExpectedConditions.presenceOfElementLocated(soldOutProductElement));
        return soldOutElement.getAttribute("innerText").trim();
    }

    public void navigateToNextPage() {
        JavascriptExecutor jsx = (JavascriptExecutor) getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(paginatorElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(nextPageElement)).click();
    }

    public void dismissCookieMessage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(dismissCookieElement)).click();
        wait.until(ExpectedConditions.invisibilityOf(getDriver().findElement(dismissCookieElement)));
    }

    public void navigateToHomePage() {
        getDriver().manage().addCookie(new Cookie("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6NDEsInVzZXJuYW1lIjoiIiwiZW1haWwiOiJ0ZXN0MTIzQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiODRhYTExM2NhOWQ1MjMzYWNmZjZhNjI3YjVmNmIwM2UiLCJyb2xlIjoiY3VzdG9tZXIiLCJkZWx1eGVUb2tlbiI6IiIsImxhc3RMb2dpbklwIjoiMC4wLjAuMCIsInByb2ZpbGVJbWFnZSI6Ii9hc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHQuc3ZnIiwidG90cFNlY3JldCI6IiIsImlzQWN0aXZlIjp0cnVlLCJjcmVhdGVkQXQiOiIyMDIwLTA5LTA3IDE5OjUwOjUwLjA0NiArMDA6MDAiLCJ1cGRhdGVkQXQiOiIyMDIwLTA5LTA3IDE5OjUwOjUwLjA0NiArMDA6MDAiLCJkZWxldGVkQXQiOm51bGx9LCJpYXQiOjE2MDA4ODkzNzEsImV4cCI6MTYwMDkwNzM3MX0.dDTYXKnht5U2Vg_5ndee1Zmyji0p4yeZ3MxAXjiE_MjxacbYvtxKF2fGHhsplA4ZBiPPKnFzYH_3INmpWgmzEtvBSWuyXGLI1KHEUq4Imzp20C1dg4QfaQkcRZ628s9vCfJkt3dfeLAEhB57ONtzHaJ0ud0DBoFCqSKNeO8feRg"));
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
