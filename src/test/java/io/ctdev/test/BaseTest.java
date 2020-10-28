package io.ctdev.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static io.ctdev.framework.WebDriverSingleton.closeDriver;
import static io.ctdev.framework.WebDriverSingleton.getDriver;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setupDriver() {
        driver = getDriver();
        wait = new WebDriverWait(getDriver(), 300);
    }

    @AfterClass
    public void tearDown() {
        closeDriver();
    }
}
