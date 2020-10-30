package io.ctdev.cucumber.runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty", "html:target/htplreport"},
        monochrome = true,
        tags = "smoke",
        glue = "io.ctdev.cucumber",
        features = "classpath:io/ctdev/features"
)

public class CucumberTest {


}
