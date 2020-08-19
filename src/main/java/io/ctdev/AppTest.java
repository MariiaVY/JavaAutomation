package io.ctdev;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {

    @Test
    public void openGooglePage() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://Google.com");
        Thread.sleep(30, 000);
        driver.quit();
    }
}
