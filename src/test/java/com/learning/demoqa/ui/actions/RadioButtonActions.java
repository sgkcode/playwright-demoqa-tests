package com.learning.demoqa.ui.actions;

import com.learning.demoqa.ui.pages.elements.RadioButtonPage;
import com.microsoft.playwright.Page;

public class RadioButtonActions {

    private final RadioButtonPage radioButtonPage;

    public RadioButtonActions(Page page, String baseUrl) {
        radioButtonPage = new RadioButtonPage(page, baseUrl);
    }

    public String selectYesAndGetValue() {
        radioButtonPage.open().selectYes();
        return radioButtonPage.getSelectedValue();
    }

    public String selectImpressiveAndGetValue() {
        radioButtonPage.open().selectImpressive();
        return radioButtonPage.getSelectedValue();
    }
}
