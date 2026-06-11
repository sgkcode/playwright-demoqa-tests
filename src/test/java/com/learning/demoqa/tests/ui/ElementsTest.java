package com.learning.demoqa.tests.ui;

import com.learning.demoqa.pages.elements.ButtonsPage;
import com.learning.demoqa.pages.elements.CheckBoxPage;
import com.learning.demoqa.pages.elements.RadioButtonPage;
import com.learning.demoqa.pages.elements.TextBoxPage;
import com.learning.demoqa.tests.BaseTest;
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
    void textBoxOutputMatchesInput() {
        TextBoxPage textBoxPage = new TextBoxPage(page, CONFIG.baseUrl());
        textBoxPage.fillFullName("John Doe")
                   .fillEmail("john@example.com")
                   .fillCurrentAddress("123 Main St")
                   .submit();

        assertThat(textBoxPage.getOutputName()).contains("John Doe");
        assertThat(textBoxPage.getOutputEmail()).contains("john@example.com");
        assertThat(textBoxPage.getOutputCurrentAddress()).contains("123 Main St");
    }

    @Test
    @Story("Check Box")
    @DisplayName("Selecting Home checkbox selects all child items")
    void checkingHomeSelectsAll() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(page, CONFIG.baseUrl());
        checkBoxPage.expandAll().checkHome();

        assertThat(checkBoxPage.getSelectedItems()).contains("home");
    }

    @Test
    @Story("Radio Button")
    @DisplayName("Selecting Yes radio button shows success message")
    void selectYesRadioButton() {
        RadioButtonPage radioPage = new RadioButtonPage(page, CONFIG.baseUrl());
        radioPage.selectYes();

        assertThat(radioPage.getSelectedValue()).isEqualTo("Yes");
    }

    @Test
    @Story("Radio Button")
    @DisplayName("Selecting Impressive radio button shows success message")
    void selectImpressiveRadioButton() {
        RadioButtonPage radioPage = new RadioButtonPage(page, CONFIG.baseUrl());
        radioPage.selectImpressive();

        assertThat(radioPage.getSelectedValue()).isEqualTo("Impressive");
    }

    @Test
    @Story("Buttons")
    @DisplayName("Double click shows confirmation message")
    void doubleClickShowsMessage() {
        ButtonsPage buttonsPage = new ButtonsPage(page, CONFIG.baseUrl());
        buttonsPage.doubleClick();

        assertThat(buttonsPage.getDoubleClickMessage()).isEqualTo("You have done a double click");
    }

    @Test
    @Story("Buttons")
    @DisplayName("Right click shows confirmation message")
    void rightClickShowsMessage() {
        ButtonsPage buttonsPage = new ButtonsPage(page, CONFIG.baseUrl());
        buttonsPage.rightClick();

        assertThat(buttonsPage.getRightClickMessage()).isEqualTo("You have done a right click");
    }
}
