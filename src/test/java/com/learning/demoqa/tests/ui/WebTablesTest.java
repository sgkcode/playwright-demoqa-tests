package com.learning.demoqa.tests.ui;

import com.learning.demoqa.pages.elements.WebTablesPage;
import com.learning.demoqa.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Web Tables")
class WebTablesTest extends BaseTest {

    @Test
    @Story("Add record")
    @DisplayName("New record appears in the table after adding")
    void addNewRecord() {
        WebTablesPage tablePage = new WebTablesPage(page, CONFIG.baseUrl());
        tablePage.addRecord("Alice", "Smith", "alice@test.com", "30", "60000", "QA");

        assertThat(tablePage.hasRecord("Alice")).isTrue();
    }

    @Test
    @Story("Search")
    @DisplayName("Search filters the table to matching rows only")
    void searchFiltersRows() {
        WebTablesPage tablePage = new WebTablesPage(page, CONFIG.baseUrl());
        tablePage.search("Cierra");

        assertThat(tablePage.getVisibleRowCount()).isEqualTo(1);
    }

    @Test
    @Story("Delete record")
    @DisplayName("Deleted record no longer appears in the table")
    void deleteRecord() {
        WebTablesPage tablePage = new WebTablesPage(page, CONFIG.baseUrl());
        tablePage.deleteRecord("Cierra");

        assertThat(tablePage.hasRecord("Cierra")).isFalse();
    }
}
