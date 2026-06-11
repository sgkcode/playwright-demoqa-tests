package com.learning.google.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

public class GoogleImagesPage extends BasePage {

    // Google Images uses data-ri on each image tile
    private final ElementsCollection imageTiles = $$("div[data-ri]");
    private final SelenideElement searchInput = $("textarea[name='q']");

    public boolean hasImageResults() {
        imageTiles.shouldBe(sizeGreaterThan(0));
        return true;
    }

    public int getImageCount() {
        return imageTiles.size();
    }

    public String getCurrentSearchQuery() {
        return searchInput.shouldBe(visible).getValue();
    }

    public GoogleImagesPage clickFirstImage() {
        imageTiles.first().shouldBe(visible).click();
        return this;
    }
}
