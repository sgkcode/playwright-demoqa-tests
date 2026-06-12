package com.learning.demoqa.tests.ui;

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
    void verifyNewRecordAppearsAfterAddingTest() {
        app.webTables().addRecord("Alice", "Smith", "alice@test.com", "30", "60000", "QA");

        assertThat(app.webTables().hasRecord("Alice")).isTrue();
    }

    @Test
    @Story("Search")
    @DisplayName("Search filters the table to matching rows only")
    void verifySearchFiltersRowsTest() {
        assertThat(app.webTables().searchAndGetRowCount("Cierra")).isEqualTo(1);
    }

    @Test
    @Story("Delete record")
    @DisplayName("Deleted record no longer appears in the table")
    void verifyDeletedRecordDisappearsTest() {
        app.webTables().deleteRecord("Cierra");

        assertThat(app.webTables().hasRecord("Cierra")).isFalse();
    }
}
