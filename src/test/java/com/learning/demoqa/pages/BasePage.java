package com.learning.demoqa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasePage {

    protected final Page page;
    protected final String baseUrl;

    public BasePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    protected void scrollAndClick(Locator locator) {
        locator.scrollIntoViewIfNeeded();
        locator.click();
    }
}
