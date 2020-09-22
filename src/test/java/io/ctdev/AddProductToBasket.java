//1. Write a test that verifies actual information about a Product. (Click on it, check title, description, etc)
//2. Add test that adds a product to basket and verifies that its added to the right place.
//3. Add test, that clicks on 2nd page, finds a product that sold out, clicks on “Add to Basket” and verifies that error is present on the page.

package io.ctdev;

import io.ctdev.framework.webDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
    public void BeforeClass() {
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
    public void addingSoldOutProductToTheBasket() throws InterruptedException {
        getDriver().findElement(By.xpath("//*[@alt='OWASP Juice Shop']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@aria-label='dismiss cookie message']"))).click();
        JavascriptExecutor jsx = (JavascriptExecutor)getDriver();
        jsx.executeScript("window.scrollBy(0,450)", "");
        Thread.sleep(7000);
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

    @AfterClass
    public void AfterClass() {
        webDriverSingleton.closeDriver();
    }

}
