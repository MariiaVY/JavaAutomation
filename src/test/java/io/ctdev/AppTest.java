package io.ctdev;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void openGooglePage() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://Google.com");
        Thread.sleep(30, 000);
        driver.quit();
    }
}
