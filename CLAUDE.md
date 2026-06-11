# selenide-google-tests

Maven + Selenide + REST Assured learning project. UI tests target google.com; API tests target the JSONPlaceholder public fake REST API.

## Running Tests

```bash
# All tests
mvn test

# UI tests only
mvn test -Dgroups=ui

# API tests only
mvn test -Dgroups=api

# Single class
mvn test -Dtest=GoogleSearchTest

# Headless Chrome
mvn test -Dheadless=true

# Different browser
mvn test -Dbrowser=firefox
```

## Allure Report

```bash
mvn allure:serve          # generate + open in browser (requires allure CLI or mvn plugin)
mvn allure:report         # generate to target/site/allure-maven-plugin/
```

## Configuration

Override any property via `-D` flag or `src/test/resources/config.properties`:

| Property      | Default                 | Notes                    |
|---------------|-------------------------|--------------------------|
| browser       | chrome                  | chrome / firefox / edge  |
| base.url      | https://www.google.com  |                          |
| browser.size  | 1920x1080               |                          |
| headless      | false                   |                          |
| timeout       | 10000                   | Element wait in ms       |

## Project Structure

```
src/test/java/com/learning/google/
├── config/
│   └── TestConfig.java           OWNER interface — reads system props then config.properties
├── pages/
│   ├── BasePage.java             Cookie consent helper
│   ├── GoogleHomePage.java       Search input + open
│   ├── GoogleSearchResultsPage.java  Results, stats, tab navigation
│   └── GoogleImagesPage.java     Image grid
└── tests/
    ├── BaseTest.java             Selenide + Allure setup, browser lifecycle
    ├── ui/
    │   ├── GoogleSearchTest.java   Basic search, parametrized, result state
    │   └── GoogleImagesTest.java   Images tab navigation
    └── api/
        └── JsonPlaceholderApiTest.java  CRUD + filter tests on jsonplaceholder.typicode.com
```

## Key Patterns

- **Page Objects** — each page is a class; methods return page instances for fluent chaining
- **BasePage** — handles Google's cookie consent dialog (EU regions)
- **BaseTest** — `@BeforeAll` registers Allure+Selenide listener; `@AfterEach` closes the browser
- **TestConfig** — OWNER-based typesafe config; properties can be overridden at runtime via `-D`
- **Parametrized tests** — `@ParameterizedTest` + `@ValueSource` in `GoogleSearchTest`
- **API tests** do not extend `BaseTest` — they configure REST Assured independently
