package io.ctdev.framework.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:testing.properties"})

public interface TestConfig extends Config, Reloadable {

    TestConfig cfg = ConfigFactory.create(TestConfig.class, System.getenv(), System.getProperties());

    @DefaultValue("chrome")
    String browser();

    String baseUrl();

    String env();

    boolean remote();

    String remoteUrl();
}

