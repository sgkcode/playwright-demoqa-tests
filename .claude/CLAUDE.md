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

## Adding a new page

1. Create `src/test/java/com/learning/demoqa/pages/<section>/YourPage.java` extending `BasePage`
2. Constructor signature: `public YourPage(Page page, String baseUrl)` — call `super(page, baseUrl)` then `page.navigate(baseUrl + "/path")`
3. Declare locators as `private final Locator` fields using `page.locator(...)` at field level
4. Return `this` for methods that stay on the same page; return a new page instance when navigating away

## Adding a new test

- UI test: extend `BaseTest`, tag with `@Tag("ui")`, annotate with `@Feature` / `@Story`
- API test: do NOT extend `BaseTest`; set `RestAssured.baseURI` in `@BeforeAll`
- Access config in tests via `CONFIG.baseUrl()`, `CONFIG.timeout()`, etc.

## Common commands

```bash
mvn test -Dtest=ElementsTest#textBoxOutputMatchesInput   # single test method
mvn test -Dheadless=true -Dgroups=ui                     # headless UI run
mvn allure:serve                                          # open Allure report
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"  # install browsers
```

## Useful Claude Code skills

| Skill | When to use |
|-------|-------------|
| `/run` | Launch tests and observe real output |
| `/verify` | Confirm a fix or new test works end-to-end |
| `/code-review` | Review a diff for correctness before committing |
| `/fewer-permission-prompts` | Reduce repeated `mvn`/`git` permission dialogs |

## Playwright API reminders

- Double click: `locator.dblclick()`
- Right click: `locator.click(new Locator.ClickOptions().setButton(MouseButton.RIGHT))`
- Filter rows with text: `locator.filter(new Locator.FilterOptions().setHasText("..."))`
- Filter rows containing child element: `locator.filter(new Locator.FilterOptions().setHas(page.locator("...")))`
- All text contents: `locator.allTextContents()` → `List<String>`
