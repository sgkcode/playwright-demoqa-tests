package com.learning.demoqa.ui.actions;

import com.learning.demoqa.ui.pages.elements.CheckBoxPage;
import com.microsoft.playwright.Page;

import java.util.List;

public class CheckBoxActions {

    private final CheckBoxPage checkBoxPage;

    public CheckBoxActions(Page page, String baseUrl) {
        checkBoxPage = new CheckBoxPage(page, baseUrl);
    }

    public List<String> selectHomeAndGetItems() {
        checkBoxPage.open().expandAll().checkHome();
        return checkBoxPage.getSelectedItems();
    }
}
