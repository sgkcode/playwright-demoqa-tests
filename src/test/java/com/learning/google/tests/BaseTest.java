package com.learning.google.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.learning.google.config.TestConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {

    protected static final TestConfig config = TestConfig.get();

    @BeforeAll
    static void registerAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }

    @BeforeEach
    void configureBrowser() {
        Configuration.browser = config.browser();
        Configuration.browserSize = config.browserSize();
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout();
        Configuration.headless = config.headless();
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }
}
