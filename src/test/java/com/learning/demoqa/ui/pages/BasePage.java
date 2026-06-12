package com.learning.demoqa.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected final Page page;
    protected final String baseUrl;

    protected BasePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    protected void scrollAndClick(Locator locator) {
        locator.scrollIntoViewIfNeeded();
        locator.click();
    }
}
