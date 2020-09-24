//Open any desirable site and verify title
package io.ctdev.HomeWork2;

import io.ctdev.framework.WebDriverSingleton;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class AssertTitle {
    WebDriver driver;
    String expectedTitle = "Homepage - Mila's Beauty";


    @BeforeClass
    public void beforeClass() {
        driver = getDriver();
    }

    @Test
    public void testTitle() throws InterruptedException {

        driver.get("https://milasbeauty.com.ua/");
        Thread.sleep(30,000);
        String actualTitle = driver.getTitle();
        Assert.assertEquals( expectedTitle, actualTitle, "Title is Incorrect");
    }

    @AfterClass
    public void afterClass() {
       WebDriverSingleton.closeDriver();
    }
}
