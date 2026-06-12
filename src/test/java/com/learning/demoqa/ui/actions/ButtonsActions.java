package com.learning.demoqa.ui.actions;

import com.learning.demoqa.ui.pages.elements.ButtonsPage;
import com.microsoft.playwright.Page;

public class ButtonsActions {

    private final ButtonsPage buttonsPage;

    public ButtonsActions(Page page, String baseUrl) {
        buttonsPage = new ButtonsPage(page, baseUrl);
    }

    public String doubleClickAndGetMessage() {
        buttonsPage.open().doubleClickDoubleClickMeButton();
        return buttonsPage.getDoubleClickMessage();
    }

    public String rightClickAndGetMessage() {
        buttonsPage.open().rightClickRightClickMeButton();
        return buttonsPage.getRightClickMessage();
    }
}
