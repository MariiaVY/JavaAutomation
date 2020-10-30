package io.ctdev.cucumber.hooks;
import io.ctdev.framework.WebDriverSingleton;
import io.ctdev.framework.config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static io.ctdev.framework.WebDriverSingleton.getDriver;

public class WebDriverHooks {
    @Before
    public void setUp() {
        getDriver().get(TestConfig.cfg.baseUrl() + "/#/register");

    }

    @After
    public void tearDown() {
        WebDriverSingleton.closeDriver();
    }

}
