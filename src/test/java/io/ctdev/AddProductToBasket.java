//1. Write a test that verifies actual information about a Product. (Click on it, check title, description, etc)
//2. Add test that adds a product to basket and verifies that its added to the right place.

package io.ctdev;

import io.ctdev.framework.webDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    @BeforeClass
    public void BeforeClass() {
        wait = new WebDriverWait(getDriver(), 5);
        getDriver().get("http://18.217.145.6/#/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Dismiss')]")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Dismiss')]")).click();
        getDriver().findElement(By.id("email")).sendKeys(validEmail);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("loginButton")).click();
    }

    @Test
    public void verifyInformationAboutProduct() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@alt='Banana Juice (1000ml)']")));
        getDriver().findElement(By.xpath("//*[contains(text(),'Banana Juice')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='cdk-overlay-pane']")));

        String actualTitleText = getDriver().findElement(By.xpath("//*[@class='container mat-typography']//h1")).getAttribute("innerText").trim();
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



    }



    @AfterClass
    public void AfterClass() {
      webDriverSingleton.closeDriver();
    }

}
