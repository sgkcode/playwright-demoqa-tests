package com.learning.demoqa.tests.ui;

import com.learning.demoqa.tests.BaseTest;
import com.learning.demoqa.ui.actions.TextBoxActions;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Elements")
class ElementsTest extends BaseTest {

    @Test
    @Story("Text Box")
    @DisplayName("Submitted form data appears in the output section")
    void verifyTextBoxOutputMatchesInputTest() {
        TextBoxActions.Output output = app.textBox().submit("John Doe", "john@example.com", "123 Main St");

        assertThat(output.name()).contains("John Doe");
        assertThat(output.email()).contains("john@example.com");
        assertThat(output.currentAddress()).contains("123 Main St");
    }

    @Test
    @Story("Check Box")
    @DisplayName("Selecting Home checkbox selects all child items")
    void verifyCheckingHomeSelectsAllTest() {
        assertThat(app.checkBox().selectHomeAndGetItems()).contains("home");
    }

    @Test
    @Story("Radio Button")
    @DisplayName("Selecting Yes radio button shows success message")
    void verifyYesRadioButtonSelectionTest() {
        assertThat(app.radioButton().selectYesAndGetValue()).isEqualTo("Yes");
    }

    @Test
    @Story("Radio Button")
    @DisplayName("Selecting Impressive radio button shows success message")
    void verifyImpressiveRadioButtonSelectionTest() {
        assertThat(app.radioButton().selectImpressiveAndGetValue()).isEqualTo("Impressive");
    }

    @Test
    @Story("Buttons")
    @DisplayName("Double click shows confirmation message")
    void verifyDoubleClickShowsMessageTest() {
        assertThat(app.buttons().doubleClickAndGetMessage()).isEqualTo("You have done a double click");
    }

    @Test
    @Story("Buttons")
    @DisplayName("Right click shows confirmation message")
    void verifyRightClickShowsMessageTest() {
        assertThat(app.buttons().rightClickAndGetMessage()).isEqualTo("You have done a right click");
    }
}
