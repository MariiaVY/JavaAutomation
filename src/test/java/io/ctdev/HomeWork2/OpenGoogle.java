package io.ctdev.HomeWork2;

import io.ctdev.framework.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class OpenGoogle {

    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = getDriver();
    }

    @Test
    public void openGooglePage() throws InterruptedException {

        driver.get("https://Google.com");
        Thread.sleep(30, 000);
        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle, "Title is not equal Google");
    }

    @AfterClass
    public void afterClass() {
        WebDriverSingleton.closeDriver();
    }
}
