package com.learning.google.pages;

import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage {

    // Google shows a GDPR consent overlay on first visit in EU regions.
    // This handles it silently if not present.
    protected void acceptGoogleConsentIfPresent() {
        try {
            var btn = $x("//button[contains(.,'Accept all') or @id='L2AGLb']");
            if (btn.exists()) {
                btn.click();
            }
        } catch (Exception ignored) {
        }
    }
}
