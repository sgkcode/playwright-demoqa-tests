# playwright-demoqa-tests

Maven + Playwright + REST Assured learning project. UI tests target demoqa.com; API tests target the demoqa.com BookStore API.

**GitHub:** https://github.com/sgkcode/playwright-demoqa-tests  
**Default branch:** `main`

## First-time setup

Install Playwright browsers before running UI tests (only needed once per machine / Docker image):

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args="install chromium"
```

## Running Tests

```bash
# All tests
mvn test

# UI tests only
mvn test -Dgroups=ui

# API tests only
mvn test -Dgroups=api

# Single class
mvn test -Dtest=ElementsTest

# Headless Chromium
mvn test -Dheadless=true

# Different browser (chromium / firefox / webkit)
mvn test -Dbrowser=firefox
```

## Allure Report

```bash
mvn allure:serve          # generate + open in browser
mvn allure:report         # generate to target/site/allure-maven-plugin/
```

## CI / Jenkins

```bash
docker compose up -d      # start Jenkins on http://localhost:8080
```

Required Jenkins plugin: **Docker Pipeline**. The `Jenkinsfile` uses a `maven:3.9-eclipse-temurin-21`
Docker agent and runs tests headless. Named volumes `maven-repo` and `playwright-browsers` cache
dependencies between builds.

## Configuration

Override any property via `-D` flag or `src/test/resources/config.properties`:

| Property        | Default               | Notes                          |
|-----------------|-----------------------|--------------------------------|
| base.url        | https://demoqa.com    |                                |
| browser         | chromium              | chromium / firefox / webkit    |
| browser.width   | 1920                  |                                |
| browser.height  | 1080                  |                                |
| headless        | false                 |                                |
| timeout         | 10000                 | Default timeout in ms          |

## Project Structure

```
src/test/java/com/learning/demoqa/
├── config/
│   └── TestConfig.java                   OWNER interface — reads system props then config.properties
├── pages/
│   ├── BasePage.java                     page + baseUrl; scrollAndClick helper
│   └── elements/
│       ├── TextBoxPage.java              fill + submit form, read output section
│       ├── CheckBoxPage.java             expand tree, check Home, read selected items
│       ├── RadioButtonPage.java          select Yes/Impressive, read success text
│       ├── WebTablesPage.java            add / search / delete rows
│       └── ButtonsPage.java             double-click, right-click, single click
└── tests/
    ├── BaseTest.java                     Playwright browser lifecycle + Allure screenshot on AfterEach
    ├── ui/
    │   ├── ElementsTest.java             TextBox, CheckBox, RadioButton, Buttons
    │   └── WebTablesTest.java            add / search / delete rows
    └── api/
        └── BookStoreApiTest.java         GET books, GET book by ISBN, token generation
```

## Key Patterns

- **Page Objects** — constructor navigates to the page; methods return `this` for fluent chaining
- **BasePage** — holds `Page page` and `String baseUrl`; page objects receive both via constructor
- **BaseTest** — `@BeforeAll` launches browser; `@BeforeEach` opens a fresh context+page; `@AfterEach` attaches screenshot to Allure and closes context
- **TestConfig** — OWNER-based typesafe config; properties can be overridden at runtime via `-D`
- **API tests** do not extend `BaseTest` — configure `RestAssured.baseURI` in `@BeforeAll`
- **Locators** are declared as `private final Locator` fields; Playwright evaluates them lazily on interaction
