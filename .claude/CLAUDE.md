# Claude Instructions for this project

## Test selectors

Google's HTML changes frequently. When a UI test fails due to a missing element:
1. Open Chrome DevTools, inspect the element, and find a stable attribute (`name`, `data-*`, `aria-label`)
2. Prefer `name` attributes or `id` over structural CSS selectors
3. For the search input: `textarea[name='q']`
4. For result titles: `#search h3`
5. For the images tab link: `a[href*='tbm=isch']`

## Adding a new page

1. Create `src/test/java/com/learning/google/pages/YourPage.java` extending `BasePage`
2. Declare elements as `private final SelenideElement` fields using `$()` / `$$()` at the top
3. Return `this` for chaining methods that stay on the same page; return a new page instance when navigating

## Adding a new test

- UI test: extend `BaseTest`, tag with `@Tag("ui")`, annotate with `@Feature` / `@Story`
- API test: do NOT extend `BaseTest`; configure `RestAssured.baseURI` in `@BeforeAll`

## Common commands

```bash
mvn test -Dtest=GoogleSearchTest#shouldReturnResultsForQuery   # run one test method
mvn test -Dheadless=true -Dgroups=ui                           # headless UI run
mvn allure:serve                                                # open report
```
