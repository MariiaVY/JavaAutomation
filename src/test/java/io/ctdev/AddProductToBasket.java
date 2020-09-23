//1. Write a test that verifies actual information about a Product. (Click on it, check title, description, etc)
//2. Add test that adds a product to basket and verifies that its added to the right place.
//3. Add test, that clicks on 2nd page, finds a product that sold out, clicks on “Add to Basket” and verifies that error is present on the page.

package io.ctdev;

import io.ctdev.framework.webDriverSingleton;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

import static io.ctdev.framework.webDriverSingleton.getDriver;

public class AddProductToBasket {
    WebDriverWait wait;
    static String validEmail = "test123@gmail.com";
    public String password = "123456789Test!";
    public String titleText = "Banana Juice (1000ml)";
    public String monkeysText = "Monkeys love it the most.";
    public String price = "1.99¤";
    public String productAddedText = "Placed Banana Juice (1000ml) into basket.";
    public String productName = "Banana Juice (1000ml)";
    public String basketTitle = "Your Basket";
    public String soldOutTxt = "Sold Out";
    public String soldOutErrorText = "We are out of stock! Sorry for the inconvenience." + "\n" + "X";
    public String soldOutProductPath = "//*[contains(text(),'Juice Shop Coaster')]";

    @BeforeClass
    public void beforeClass() {
        wait = new WebDriverWait(getDriver(), 20);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
        getDriver().findElement(By.id("email")).sendKeys(validEmail);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("loginButton")).click();
        getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void verifyInformationAboutProduct() {

        System.out.println("Information about product");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='table-container custom-slate']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@alt='Banana Juice (1000ml)']"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='mat-dialog-content']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='cdk-overlay-pane']")));

        WebElement titleElementText = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='container mat-typography']//h1")));
        String actualTitleText = titleElementText.getAttribute("innerText").trim();
        Assert.assertEquals(actualTitleText, titleText, "Title text doesn't match");

        String actualMonkeysText = getDriver().findElement(By.xpath("//*[contains(text(),'Monkeys love it the most')]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualMonkeysText, monkeysText, "Monkeys text doesn't match");

        String actualPrice = getDriver().findElement(By.xpath("//*[@class='container mat-typography']//*[@class='item-price']")).getAttribute("innerText").trim();
        Assert.assertEquals(actualPrice, price, "Price doesn't match");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='Close Dialog']"))).click();
    }

    @Test
    public void addingProductToTheBasket() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='mat-grid-tile ng-star-inserted'][3]//button[@aria-label='Add to Basket']"))).click();

        WebElement addedProductElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Placed Banana Juice')]")));
        String actualProductAddedText = addedProductElement.getAttribute("innerText").trim();
        Assert.assertEquals(actualProductAddedText, productAddedText, "The text doesn't match");

        getDriver().findElement(By.xpath("//*[@aria-label='Show the shopping cart']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ng-star-inserted']//h1")));

        System.out.println("Verifying added product in the basket");
        String actualBasketTitle = getDriver().findElement(By.xpath("//*[@class='mat-card mat-focus-indicator mat-elevation-z6']//h1")).getAttribute("innerText").trim();
        Assert.assertEquals(actualBasketTitle, basketTitle, "Basket title doesn't match");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@alt='Banana Juice (1000ml)']")));
        String actualProductName = getDriver().findElement(By.xpath("//*[@class='mat-row cdk-row ng-star-inserted']//*[@role='gridcell'][2]")).getAttribute("innerText").trim();
        Assert.assertEquals(actualProductName, productName, "Product Name doesn't match");

        String actualProductPrice = getDriver().findElement(By.xpath("//*[@class='mat-cell cdk-cell cdk-column-price mat-column-price ng-star-inserted']")).getAttribute("innerText").trim();
        Assert.assertEquals(actualProductPrice, price, "Product price doesn't match");
    }

    @Test
    public void addingSoldOutProductToTheBasket() {
        getDriver().manage().addCookie(new Cookie ("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6NDEsInVzZXJuYW1lIjoiIiwiZW1haWwiOiJ0ZXN0MTIzQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiODRhYTExM2NhOWQ1MjMzYWNmZjZhNjI3YjVmNmIwM2UiLCJyb2xlIjoiY3VzdG9tZXIiLCJkZWx1eGVUb2tlbiI6IiIsImxhc3RMb2dpbklwIjoiMC4wLjAuMCIsInByb2ZpbGVJbWFnZSI6Ii9hc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHQuc3ZnIiwidG90cFNlY3JldCI6IiIsImlzQWN0aXZlIjp0cnVlLCJjcmVhdGVkQXQiOiIyMDIwLTA5LTA3IDE5OjUwOjUwLjA0NiArMDA6MDAiLCJ1cGRhdGVkQXQiOiIyMDIwLTA5LTA3IDE5OjUwOjUwLjA0NiArMDA6MDAiLCJkZWxldGVkQXQiOm51bGx9LCJpYXQiOjE2MDA4ODkzNzEsImV4cCI6MTYwMDkwNzM3MX0.dDTYXKnht5U2Vg_5ndee1Zmyji0p4yeZ3MxAXjiE_MjxacbYvtxKF2fGHhsplA4ZBiPPKnFzYH_3INmpWgmzEtvBSWuyXGLI1KHEUq4Imzp20C1dg4QfaQkcRZ628s9vCfJkt3dfeLAEhB57ONtzHaJ0ud0DBoFCqSKNeO8feRg"));
        getDriver().manage().addCookie(new Cookie ("language", "en"));
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//*[@alt='OWASP Juice Shop']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='dismiss cookie message']"))).click();
        wait.until(ExpectedConditions.invisibilityOf(getDriver().findElement(By.xpath("//*[@aria-label='dismiss cookie message']"))));
        JavascriptExecutor jsx = (JavascriptExecutor)getDriver();
        jsx.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(By.cssSelector("[class*=mat-paginator-navigation-next]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='mat-paginator-range-actions']//button [2]"))).click();

        WebElement soldOutElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ribbon ribbon-top-left ribbon-sold ng-star-inserted']/span")));
        String actualSoldOutText = soldOutElement.getAttribute("innerText").trim();
        Assert.assertEquals(actualSoldOutText, soldOutTxt, "Text doesn't match");

       wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='mat-grid-tile ng-star-inserted'][4]//button[@aria-label='Add to Basket']"))).click();

        System.out.println("Verifying error is shown");
        WebElement soldOutError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='mat-simple-snackbar ng-star-inserted']")));
        String actualSoldOutError = soldOutError.getAttribute("innerText").trim();
        Assert.assertEquals(actualSoldOutError, soldOutErrorText, "Error text doesn't match");

        getDriver().findElement(By.xpath("//*[@aria-label='Show the shopping cart']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='ng-star-inserted']//h1")));

        System.out.println("Verifying sold out product isn't present in the basket");
        //String actualTotalPrice = getDriver().findElement(By.xpath("//*[@id='price']")).getAttribute("innerText").trim();
        //Assert.assertEquals(actualTotalPrice, price, "Product total price doesn't match");

        Assert.assertFalse(existsElement(soldOutProductPath), "Sold out product is present");

    }

    private boolean existsElement(String soldOutProductPath) {
        try {
            getDriver().findElement(By.xpath(soldOutProductPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @AfterMethod
    public void afterMethod() {
        getDriver().findElement(By.xpath("//*[@alt='OWASP Juice Shop']")).click();
    }

    @AfterClass
    public void afterClass() {
        webDriverSingleton.closeDriver();
    }

}
