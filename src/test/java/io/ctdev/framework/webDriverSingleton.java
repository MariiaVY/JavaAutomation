package io.ctdev.framework;

import io.ctdev.framework.config.testConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class webDriverSingleton {

    private static WebDriver driver;

    private webDriverSingleton() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            switch (testConfig.cfg.browser()) {
                case "safari": {
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

