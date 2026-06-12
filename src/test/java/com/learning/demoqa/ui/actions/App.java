package com.learning.demoqa.ui.actions;

import com.microsoft.playwright.Page;

public class App {

    private final Page page;
    private final String baseUrl;

    private TextBoxActions textBox;
    private CheckBoxActions checkBox;
    private RadioButtonActions radioButton;
    private ButtonsActions buttons;
    private WebTablesActions webTables;

    public App(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public TextBoxActions textBox() {
        if (textBox == null) textBox = new TextBoxActions(page, baseUrl);
        return textBox;
    }

    public CheckBoxActions checkBox() {
        if (checkBox == null) checkBox = new CheckBoxActions(page, baseUrl);
        return checkBox;
    }

    public RadioButtonActions radioButton() {
        if (radioButton == null) radioButton = new RadioButtonActions(page, baseUrl);
        return radioButton;
    }

    public ButtonsActions buttons() {
        if (buttons == null) buttons = new ButtonsActions(page, baseUrl);
        return buttons;
    }

    public WebTablesActions webTables() {
        if (webTables == null) webTables = new WebTablesActions(page, baseUrl);
        return webTables;
    }
}
