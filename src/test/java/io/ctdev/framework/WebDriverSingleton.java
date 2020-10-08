package io.ctdev.framework;

import io.ctdev.framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class WebDriverSingleton {

    private static WebDriver driver;

    private WebDriverSingleton() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (TestConfig.cfg.browser()) {
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                }

                default: {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
    }
}

