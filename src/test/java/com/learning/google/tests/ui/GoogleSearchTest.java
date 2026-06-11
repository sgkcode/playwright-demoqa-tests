package com.learning.google.tests.ui;

import com.learning.google.pages.GoogleHomePage;
import com.learning.google.pages.GoogleSearchResultsPage;
import com.learning.google.tests.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Feature("Google Search")
class GoogleSearchTest extends BaseTest {

    private final GoogleHomePage homePage = new GoogleHomePage();

    @Test
    @DisplayName("Search returns non-empty results")
    @Story("Basic search")
    @Description("User types a query and gets at least one result title on the results page")
    void shouldReturnResultsForQuery() {
        GoogleSearchResultsPage results = homePage.open().search("Selenide Java");

        assertThat(results.getResultTitles().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("First result title relates to the query")
    @Story("Basic search")
    void firstResultShouldRelateToQuery() {
        GoogleSearchResultsPage results = homePage.open().search("Selenium WebDriver");

        assertThat(results.getFirstResultTitle().toLowerCase())
                .containsAnyOf("selenium", "webdriver", "browser");
    }

    @Test
    @DisplayName("Result stats are shown after search")
    @Story("Search metadata")
    void shouldShowResultStats() {
        GoogleSearchResultsPage results = homePage.open().search("Java testing");

        assertThat(results.getResultStatsText()).isNotBlank();
    }

    @Test
    @DisplayName("Search input retains the query after search")
    @Story("Search input state")
    void searchInputShouldRetainQuery() {
        String query = "JUnit 5 tutorial";

        GoogleSearchResultsPage results = homePage.open().search(query);

        assertThat(results.getCurrentSearchQuery()).isEqualTo(query);
    }

    @Test
    @DisplayName("Refining the search updates results")
    @Story("Search refinement")
    void shouldUpdateResultsOnSearchRefinement() {
        GoogleSearchResultsPage results = homePage.open()
                .search("Java")
                .refineSearch("Java Maven");

        assertThat(results.getCurrentSearchQuery()).isEqualTo("Java Maven");
        assertThat(results.getResultTitles().size()).isGreaterThan(0);
    }

    @ParameterizedTest(name = "Query: {0}")
    @DisplayName("Search works for various tech topics")
    @ValueSource(strings = {"REST Assured", "Allure Report", "Maven Surefire Plugin"})
    void shouldReturnResultsForVariousQueries(String query) {
        GoogleSearchResultsPage results = homePage.open().search(query);

        assertThat(results.getResultTitles().size()).isGreaterThan(0);
    }
}
