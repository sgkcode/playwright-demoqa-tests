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

# Override parallel thread count
mvn test -Djunit.jupiter.execution.parallel.config.fixed.parallelism=4
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

Required Jenkins plugin: **Docker Pipeline**, **Allure Jenkins Plugin**.  
The `Jenkinsfile` exposes three build parameters:

| Parameter       | Default    | Description                                      |
|-----------------|------------|--------------------------------------------------|
| TEST_FILTER     | _(empty)_  | Surefire `-Dtest` filter: class, `Class#method`, `Class+Class`, wildcard (empty = all) |
| BROWSER         | chromium   | chromium / firefox / webkit                      |
| PARALLEL_COUNT  | 2          | JUnit 5 parallel thread count                    |

When `TEST_CLASS` is empty the pipeline runs API and UI test suites in parallel. When set it runs just that class headless with the selected browser.

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

Config is read by OWNER (`TestConfig`) with priority: system properties → config.properties → `@DefaultValue`.

## Architecture

```
Tests
  ↓  uses app / apiApp facade
Reusable Actions   (ui/actions/, api/actions/)
  ↓  delegates to
Page Objects       (ui/pages/)   /   API Services  (api/services/)
  ↓                                       ↓
Playwright Locators               REST Assured + Models (api/models/)
```

- Tests interact **only** with the `App` / `ApiApp` facade — never with page objects or services directly.
- Action methods perform multi-step workflows and return plain values (String, boolean, List, record) so tests can assert without knowing internals.
- Verifications belong in tests, not in action classes.

## Project Structure

```
src/test/java/com/learning/demoqa/
├── core/
│   └── config/
│       └── TestConfig.java               OWNER interface — reads system props then config.properties
├── ui/
│   ├── actions/
│   │   ├── App.java                      UI facade; lazy-initialized action instances per test
│   │   ├── TextBoxActions.java           fill + submit form, return Output record
│   │   ├── CheckBoxActions.java          expand tree, check Home, return selected items
│   │   ├── RadioButtonActions.java       select radio, return selected value
│   │   ├── ButtonsActions.java           double-click / right-click, return message text
│   │   └── WebTablesActions.java         add / search / delete rows
│   └── pages/
│       ├── BasePage.java                 holds Page + baseUrl; scrollAndClick helper
│       └── elements/
│           ├── TextBoxPage.java          fill + submit form, read output section
│           ├── CheckBoxPage.java         expand tree, check Home, read selected items
│           ├── RadioButtonPage.java      select Yes/Impressive, read success text
│           ├── ButtonsPage.java          double-click, right-click, read message text
│           └── WebTablesPage.java        add / search / delete rows
├── api/
│   ├── actions/
│   │   ├── ApiApp.java                   API facade; lazy-initialized action instances
│   │   ├── BooksActions.java             get all books, get book by ISBN
│   │   └── AccountActions.java           create user, generate token
│   ├── services/
│   │   ├── BaseApiService.java           abstract; builds RequestSpecification with baseUrl
│   │   ├── BooksApiService.java          GET /BookStore/v1/Books, GET /BookStore/v1/Book
│   │   └── AccountApiService.java        POST /Account/v1/User, POST /Account/v1/GenerateToken
│   └── models/
│       ├── request/
│       │   └── UserCredentialsRequestModel.java
│       └── response/
│           ├── BookResponseModel.java
│           ├── BooksListResponseModel.java
│           └── TokenResponseModel.java
└── tests/
    ├── BaseTest.java                     Playwright browser lifecycle; creates App per test
    ├── BaseApiTest.java                  creates ApiApp in @BeforeAll; @TestInstance(PER_CLASS)
    ├── ui/
    │   ├── ElementsTest.java             TextBox, CheckBox, RadioButton, Buttons
    │   └── WebTablesTest.java            add / search / delete rows
    └── api/
        └── BookStoreApiTest.java         GET books, GET book by ISBN, token generation
```

## Key Patterns

- **App facade** — created in `BaseTest.openContext()`, exposed as `protected App app`; caches action instances for the test's lifetime
- **ApiApp facade** — created in `BaseApiTest.initApp()` (`@BeforeAll`), exposed as `protected ApiApp app`
- **Action classes** — one per page (1:1); encapsulate business workflows; return plain values; no assertions
- **Page objects** — constructor takes `(Page, String baseUrl)` only; `open()` method navigates; methods return `this` for fluent chaining
- **BasePage** — holds `Page page` and `String baseUrl`; `scrollAndClick` helper for off-screen elements
- **BaseApiService** — builds `RequestSpecification` with baseUrl + JSON content type; avoids static global state
- **Models** — POJOs with Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` where needed); `@JsonProperty` + `@JsonIgnoreProperties(ignoreUnknown = true)` for deserialization; named `*RequestModel` / `*ResponseModel`
- **Parallel execution** — classes run concurrently, methods within a class run on the same thread (`junit-platform.properties`)
- **Test method naming** — all test methods begin with `verify` and end with `Test`, e.g. `verifyTextBoxOutputMatchesInputTest()`

## Adding a new UI section

1. Create `ui/pages/<section>/YourPage.java` extending `BasePage`
   - Constructor: `super(page, baseUrl)` — no navigation
   - `open()` method: `page.navigate(baseUrl + "/path"); return this;`
   - Locators as `private final Locator` fields
   - Methods return `this` for fluent chaining
2. Create `ui/actions/YourActions.java`
   - Field: `private final YourPage yourPage = new YourPage(page, baseUrl);`
   - Methods call `yourPage.open()` then chain page methods; return plain values
3. Add `public YourActions your() { ... }` (lazy-init) to `App.java`
4. Tests call `app.your().doSomething()` — never touch page objects directly

## Adding a new API section

1. Create `api/models/request/YourRequestModel.java` and `api/models/response/YourResponseModel.java`
2. Create `api/services/YourApiService.java` extending `BaseApiService`
3. Create `api/actions/YourActions.java` — inject `String baseUrl`, instantiate the service
4. Add `public YourActions your() { ... }` (lazy-init) to `ApiApp.java`
5. Tests extend `BaseApiTest` and call `app.your().doSomething()`
