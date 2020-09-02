package io.ctdev.framework.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:testing.properties" })

public interface testConfig extends Config {

    testConfig cfg = ConfigFactory.create(testConfig.class);

        @DefaultValue("chrome")
        String browser();
    }

