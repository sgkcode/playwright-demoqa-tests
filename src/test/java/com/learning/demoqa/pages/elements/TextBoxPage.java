package com.learning.demoqa.pages.elements;

import com.learning.demoqa.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TextBoxPage extends BasePage {

    private final Locator fullNameInput = page.locator("#userName");
    private final Locator emailInput = page.locator("#userEmail");
    private final Locator currentAddressInput = page.locator("#currentAddress");
    private final Locator permanentAddressInput = page.locator("#permanentAddress");
    private final Locator submitButton = page.locator("#submit");
    private final Locator outputName = page.locator("#output #name");
    private final Locator outputEmail = page.locator("#output #email");
    private final Locator outputCurrentAddress = page.locator("#output #currentAddress");
    private final Locator outputPermanentAddress = page.locator("#output #permanentAddress");

    public TextBoxPage(Page page, String baseUrl) {
        super(page, baseUrl);
        page.navigate(baseUrl + "/text-box");
    }

    public TextBoxPage fillFullName(String name) {
        fullNameInput.fill(name);
        return this;
    }

    public TextBoxPage fillEmail(String email) {
        emailInput.fill(email);
        return this;
    }

    public TextBoxPage fillCurrentAddress(String address) {
        currentAddressInput.fill(address);
        return this;
    }

    public TextBoxPage fillPermanentAddress(String address) {
        permanentAddressInput.fill(address);
        return this;
    }

    public TextBoxPage submit() {
        scrollAndClick(submitButton);
        return this;
    }

    public String getOutputName() {
        return outputName.textContent();
    }

    public String getOutputEmail() {
        return outputEmail.textContent();
    }

    public String getOutputCurrentAddress() {
        return outputCurrentAddress.textContent();
    }

    public String getOutputPermanentAddress() {
        return outputPermanentAddress.textContent();
    }
}
