package com.learning.demoqa.config;

import org.aeonbits.owner.Config;

@Config.Sources({"system:properties", "classpath:config.properties"})
public interface TestConfig extends Config {

    @Key("base.url")
    @DefaultValue("https://demoqa.com")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chromium")
    String browser();

    @Key("browser.width")
    @DefaultValue("1920")
    int browserWidth();

    @Key("browser.height")
    @DefaultValue("1080")
    int browserHeight();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();

    @Key("timeout")
    @DefaultValue("10000")
    int timeout();
}
