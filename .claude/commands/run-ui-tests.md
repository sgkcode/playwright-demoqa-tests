Run the UI test suite (headless by default for CI, headed when $HEADED=true).

```bash
mvn test -Dgroups=ui -Dheadless=${HEADED:-true}
```

After the run, open the Allure report:

```bash
mvn allure:serve
```
