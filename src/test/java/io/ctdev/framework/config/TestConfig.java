package io.ctdev.framework.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:testing.properties"})

public interface TestConfig extends Config {

    TestConfig cfg = ConfigFactory.create(TestConfig.class, System.getenv(), System.getProperties());

    @DefaultValue("chrome")
    String browser();

    String baseUrl();

    String env();

    boolean remote();

    String remoteUrl();
}

