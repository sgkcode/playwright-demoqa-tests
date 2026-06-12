package com.learning.demoqa.pages.elements;

import com.learning.demoqa.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class CheckBoxPage extends BasePage {

    private final Locator expandAllButton = page.locator(".rct-option-expand-all");
    private final Locator homeCheckboxLabel = page.locator("label[for='tree-node-home']");
    private final Locator resultItems = page.locator(".display-result > span");

    public CheckBoxPage(Page page, String baseUrl) {
        super(page, baseUrl);
        page.navigate(baseUrl + "/checkbox");
    }

    public CheckBoxPage expandAll() {
        expandAllButton.click(new Locator.ClickOptions().setForce(true));
        return this;
    }

    public CheckBoxPage checkHome() {
        homeCheckboxLabel.click();
        return this;
    }

    public List<String> getSelectedItems() {
        return resultItems.allTextContents();
    }
}
