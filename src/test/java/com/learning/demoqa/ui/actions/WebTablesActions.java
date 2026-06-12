package com.learning.demoqa.ui.actions;

import com.learning.demoqa.ui.pages.elements.WebTablesPage;
import com.microsoft.playwright.Page;

public class WebTablesActions {

    private final WebTablesPage webTablesPage;

    public WebTablesActions(Page page, String baseUrl) {
        webTablesPage = new WebTablesPage(page, baseUrl);
        webTablesPage.open();
    }

    public void addRecord(String firstName, String lastName, String email,
                          String age, String salary, String department) {
        webTablesPage.addRecord(firstName, lastName, email, age, salary, department);
    }

    public boolean hasRecord(String firstName) {
        return webTablesPage.hasRecord(firstName);
    }

    public int searchAndGetRowCount(String query) {
        webTablesPage.search(query);
        return webTablesPage.getVisibleRowCount();
    }

    public void deleteRecord(String firstName) {
        webTablesPage.deleteRecord(firstName);
    }
}
