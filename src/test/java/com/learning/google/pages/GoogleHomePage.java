package com.learning.google.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.title;

public class GoogleHomePage extends BasePage {

    private final SelenideElement searchInput = $("textarea[name='q']");

    public GoogleHomePage open() {
        Selenide.open("https://www.google.com");
        acceptGoogleConsentIfPresent();
        return this;
    }

    public GoogleSearchResultsPage search(String query) {
        searchInput.shouldBe(Condition.visible).setValue(query).pressEnter();
        return new GoogleSearchResultsPage();
    }

    public String getPageTitle() {
        return title();
    }
}
