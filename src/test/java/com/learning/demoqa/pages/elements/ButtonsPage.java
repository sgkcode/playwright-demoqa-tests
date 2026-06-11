package com.learning.demoqa.pages.elements;

import com.learning.demoqa.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;

public class ButtonsPage extends BasePage {

    private final Locator doubleClickButton = page.locator("#doubleClickBtn");
    private final Locator rightClickButton = page.locator("#rightClickBtn");
    private final Locator dynamicClickButton = page.locator("button", new Page.LocatorOptions().setHasText("Click Me")).last();
    private final Locator doubleClickMessage = page.locator("#doubleClickMessage");
    private final Locator rightClickMessage = page.locator("#rightClickMessage");
    private final Locator dynamicClickMessage = page.locator("#dynamicClickMessage");

    public ButtonsPage(Page page, String baseUrl) {
        super(page, baseUrl);
        page.navigate(baseUrl + "/buttons");
    }

    public ButtonsPage doubleClick() {
        doubleClickButton.dblclick();
        return this;
    }

    public ButtonsPage rightClick() {
        rightClickButton.click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
        return this;
    }

    public ButtonsPage singleClick() {
        dynamicClickButton.click();
        return this;
    }

    public String getDoubleClickMessage() {
        return doubleClickMessage.textContent();
    }

    public String getRightClickMessage() {
        return rightClickMessage.textContent();
    }

    public String getDynamicClickMessage() {
        return dynamicClickMessage.textContent();
    }
}
