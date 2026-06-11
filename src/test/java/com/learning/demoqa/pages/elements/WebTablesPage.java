package com.learning.demoqa.pages.elements;

import com.learning.demoqa.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WebTablesPage extends BasePage {

    private final Locator addButton = page.locator("#addNewRecordButton");
    private final Locator searchBox = page.locator("#searchBox");

    // Registration form modal fields
    private final Locator firstNameInput = page.locator("#firstName");
    private final Locator lastNameInput = page.locator("#lastName");
    private final Locator emailInput = page.locator("#userEmail");
    private final Locator ageInput = page.locator("#age");
    private final Locator salaryInput = page.locator("#salary");
    private final Locator departmentInput = page.locator("#department");
    private final Locator submitButton = page.locator("#submit");

    public WebTablesPage(Page page, String baseUrl) {
        super(page, baseUrl);
        page.navigate(baseUrl + "/webtables");
    }

    public WebTablesPage addRecord(String firstName, String lastName, String email,
                                   String age, String salary, String department) {
        addButton.click();
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        emailInput.fill(email);
        ageInput.fill(age);
        salaryInput.fill(salary);
        departmentInput.fill(department);
        submitButton.click();
        return this;
    }

    public WebTablesPage search(String query) {
        searchBox.fill(query);
        return this;
    }

    public boolean hasRecord(String firstName) {
        return page.locator(".rt-tbody .rt-tr-group")
                   .filter(new Locator.FilterOptions().setHasText(firstName))
                   .filter(new Locator.FilterOptions().setHas(page.locator("span[title='Delete']")))
                   .count() > 0;
    }

    public int getVisibleRowCount() {
        return page.locator(".rt-tbody .rt-tr-group")
                   .filter(new Locator.FilterOptions().setHas(page.locator("span[title='Delete']")))
                   .count();
    }

    public WebTablesPage deleteRecord(String firstName) {
        page.locator(".rt-tbody .rt-tr-group")
            .filter(new Locator.FilterOptions().setHasText(firstName))
            .locator("span[title='Delete']")
            .first()
            .click();
        return this;
    }
}
