package com.learning.demoqa.tests;

import com.learning.demoqa.api.actions.ApiApp;
import com.learning.demoqa.core.config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseApiTest {

    protected static final TestConfig CONFIG = ConfigFactory.create(TestConfig.class);
    protected ApiApp app;

    @BeforeAll
    void initApp() {
        app = new ApiApp(CONFIG.baseUrl());
    }
}
