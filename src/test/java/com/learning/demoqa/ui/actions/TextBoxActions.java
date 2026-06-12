package com.learning.demoqa.ui.actions;

import com.learning.demoqa.ui.pages.elements.TextBoxPage;
import com.microsoft.playwright.Page;

public class TextBoxActions {

    public record Output(String name, String email, String currentAddress) {}

    private final TextBoxPage textBoxPage;

    public TextBoxActions(Page page, String baseUrl) {
        textBoxPage = new TextBoxPage(page, baseUrl);
    }

    public Output submit(String name, String email, String currentAddress) {
        textBoxPage.open()
                   .fillFullName(name)
                   .fillEmail(email)
                   .fillCurrentAddress(currentAddress)
                   .submit();
        return new Output(
                textBoxPage.getOutputName(),
                textBoxPage.getOutputEmail(),
                textBoxPage.getOutputCurrentAddress()
        );
    }
}
