package com.learning.demoqa.pages.elements;

import com.learning.demoqa.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RadioButtonPage extends BasePage {

    private final Locator yesLabel = page.locator("label[for='yesRadio']");
    private final Locator impressiveLabel = page.locator("label[for='impressiveRadio']");
    private final Locator selectedValueText = page.locator("p.mt-3 .text-success");

    public RadioButtonPage(Page page, String baseUrl) {
        super(page, baseUrl);
        page.navigate(baseUrl + "/radio-button");
    }

    public RadioButtonPage selectYes() {
        yesLabel.click();
        return this;
    }

    public RadioButtonPage selectImpressive() {
        impressiveLabel.click();
        return this;
    }

    public String getSelectedValue() {
        return selectedValueText.textContent();
    }
}
