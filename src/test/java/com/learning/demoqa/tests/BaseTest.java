package com.learning.demoqa.tests;

import com.learning.demoqa.config.TestConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static final TestConfig CONFIG = ConfigFactory.create(TestConfig.class);

    private Playwright playwright;
    private Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(CONFIG.headless());

        browser = switch (CONFIG.browser().toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit"  -> playwright.webkit().launch(options);
            default        -> playwright.chromium().launch(options);
        };
    }

    @BeforeEach
    void openContext() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(CONFIG.browserWidth(), CONFIG.browserHeight()));
        page = context.newPage();
        page.setDefaultTimeout(CONFIG.timeout());
    }

    @AfterEach
    void closeContext() {
        if (page != null) {
            Allure.addAttachment("Screenshot", "image/png",
                    new ByteArrayInputStream(page.screenshot()), "png");
        }
        context.close();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
