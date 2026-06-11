package com.learning.google.tests.ui;

import com.learning.google.pages.GoogleHomePage;
import com.learning.google.pages.GoogleImagesPage;
import com.learning.google.tests.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Google Images")
class GoogleImagesTest extends BaseTest {

    private final GoogleHomePage homePage = new GoogleHomePage();

    @Test
    @DisplayName("Can navigate from search results to the Images tab")
    @Story("Images navigation")
    void shouldNavigateToImagesTab() {
        GoogleImagesPage imagesPage = homePage.open()
                .search("Selenide")
                .navigateToImages();

        assertThat(imagesPage.hasImageResults()).isTrue();
    }

    @Test
    @DisplayName("Images tab shows image tiles for the query")
    @Story("Images search")
    void shouldDisplayImageTilesForQuery() {
        GoogleImagesPage imagesPage = homePage.open()
                .search("Java programming")
                .navigateToImages();

        assertThat(imagesPage.getImageCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Images page retains the search query in the input")
    @Story("Images search state")
    void imagePageShouldRetainSearchQuery() {
        String query = "Maven";

        GoogleImagesPage imagesPage = homePage.open()
                .search(query)
                .navigateToImages();

        assertThat(imagesPage.getCurrentSearchQuery()).contains(query);
    }
}
