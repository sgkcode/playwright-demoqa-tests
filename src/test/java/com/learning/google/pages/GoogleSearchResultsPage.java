package com.learning.google.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

public class GoogleSearchResultsPage extends BasePage {

    private final ElementsCollection resultTitles = $$("#search h3");
    private final SelenideElement resultStats = $("#result-stats");
    private final SelenideElement searchInput = $("textarea[name='q']");

    public ElementsCollection getResultTitles() {
        resultTitles.shouldBe(sizeGreaterThan(0));
        return resultTitles;
    }

    public String getFirstResultTitle() {
        return resultTitles.first().shouldBe(visible).getText();
    }

    public String getResultStatsText() {
        return resultStats.shouldBe(visible).getText();
    }

    public String getCurrentSearchQuery() {
        return searchInput.getValue();
    }

    public GoogleSearchResultsPage refineSearch(String query) {
        searchInput.shouldBe(visible).clear();
        searchInput.setValue(query).pressEnter();
        return new GoogleSearchResultsPage();
    }

    public GoogleImagesPage navigateToImages() {
        // The "Images" tab link always contains tbm=isch in its href
        $("a[href*='tbm=isch']").shouldBe(visible).click();
        return new GoogleImagesPage();
    }
}
