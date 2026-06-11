package com.learning.google.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface TestConfig extends Config {

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("base.url")
    @DefaultValue("https://www.google.com")
    String baseUrl();

    @Key("browser.size")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();

    @Key("timeout")
    @DefaultValue("10000")
    long timeout();

    static TestConfig get() {
        return ConfigFactory.create(TestConfig.class, System.getProperties());
    }
}
