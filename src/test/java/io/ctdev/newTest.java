package io.ctdev;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class newTest {


    @Test
    public void openGooglePage() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://Google.com");
        Thread.sleep(30, 000);

        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Title is not equal Google", expectedTitle, actualTitle);
        driver.quit();
    }

}
