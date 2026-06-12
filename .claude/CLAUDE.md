# Claude Instructions for this project

## Selectors

demoqa.com uses stable `id` attributes on most interactive elements. Prefer `#id` selectors.
When an element lacks an id, use `label[for='...']` for inputs or `[title='...']` for action icons.

Key selectors by page:
- **TextBox**: `#userName`, `#userEmail`, `#currentAddress`, `#permanentAddress`, `#submit`, `#output #name`
- **CheckBox**: `.rct-option-expand-all`, `label[for='tree-node-home']`, `.display-result > span`
- **RadioButton**: `label[for='yesRadio']`, `label[for='impressiveRadio']`, `p.mt-3 .text-success`
- **WebTables**: `#addNewRecordButton`, `#searchBox`, `.rt-tr-group`, `span[title='Delete']`, `span[title='Edit']`
- **Buttons**: `#doubleClickBtn`, `#rightClickBtn`, `#doubleClickMessage`, `#rightClickMessage`

## Architecture rules

- Tests must NOT use page objects or API services directly — only the `App` / `ApiApp` facade.
- Verifications belong in tests, not in action classes. Actions return data; tests assert on it.
- One action class per page (1:1 mapping). No action class that spans multiple pages.
- All test method names: begin with `verify`, end with `Test`.
- No `var` — all types must be explicit.

```java
// UI test — extend BaseTest, use app
TextBoxActions.Output output = app.textBox().submit("name", "email", "addr");
assertThat(output.name()).contains("name");

// API test — extend BaseApiTest, use app
List<BookResponseModel> books = app.books().getAllBooksAndGetList();
assertThat(books).isNotEmpty();
```

## Adding a new UI feature

1. **Page object**: `ui/pages/<section>/YourPage.java` extending `BasePage`
   - Constructor: `super(page, baseUrl)` — no navigation in constructor
   - `open()` method navigates and returns `this`
   - Locators as `private final Locator` fields
   - Methods return `this` for fluent chaining; no business logic
2. **Action class**: `ui/actions/YourActions.java`
   - Field: `private final YourPage yourPage = new YourPage(page, baseUrl);`
   - Methods call `yourPage.open()` then chain; return plain values (String, boolean, List, record)
   - Action methods that both act AND return data: use `AndGet` in the name, e.g. `clickAndGetMessage()`
3. **Wire up**: add `public YourActions your() { if (your == null) your = new YourActions(page, baseUrl); return your; }` to `App.java`

## Adding a new API feature

1. **Models**: `api/models/request/YourRequestModel.java`, `api/models/response/YourResponseModel.java`
   - Lombok `@Data @NoArgsConstructor`; `@AllArgsConstructor` on request models
   - `@JsonProperty` on every field; `@JsonIgnoreProperties(ignoreUnknown = true)` on response models
2. **Service**: `api/services/YourApiService.java` extending `BaseApiService`
   - Constructor calls `super(baseUrl)`; methods use `given(spec).when().get/post(...)`; return `Response`
3. **Actions**: `api/actions/YourActions.java`
   - Field: `private final YourApiService service = new YourApiService(baseUrl);`
   - Methods call service, deserialize with `.as(YourResponseModel.class)`, return the model
4. **Wire up**: add lazy-init method to `ApiApp.java`

## Adding a new test

- UI test: extend `BaseTest`, tag `@Tag("ui")`, annotate with `@Feature` / `@Story`
- API test: extend `BaseApiTest`, tag `@Tag("api")`, annotate with `@Feature` / `@Story`
- Config available in both via `CONFIG.baseUrl()`, `CONFIG.timeout()`, etc.

## Common commands

```bash
mvn test -Dtest=ElementsTest                          # single class
mvn test -Dtest=ElementsTest#verifyYesRadioButtonSelectionTest  # single method
mvn test -Dheadless=true -Dgroups=ui                 # headless UI run
mvn allure:serve                                      # open Allure report
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.classpathScope=test -Dexec.args="install chromium"
```

## Jenkins build parameters

| Parameter       | Default   | Effect                                                   |
|-----------------|-----------|----------------------------------------------------------|
| TEST_FILTER     | _(empty)_ | Surefire `-Dtest` filter: class, `Class#method`, `Class+Class` (empty = all) |
| BROWSER         | chromium  | Browser for UI tests; Playwright installs it automatically |
| PARALLEL_COUNT  | 2         | Overrides `junit-platform.properties` parallelism value  |

## Playwright API reminders

- Double click: `locator.dblclick()`
- Right click: `locator.click(new Locator.ClickOptions().setButton(MouseButton.RIGHT))`
- Filter rows with text: `locator.filter(new Locator.FilterOptions().setHasText("..."))`
- Filter rows containing child element: `locator.filter(new Locator.FilterOptions().setHas(page.locator("...")))`
- All text contents: `locator.allTextContents()` → `List<String>`
