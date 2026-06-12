package com.learning.demoqa.api.actions;

public class ApiApp {

    private final String baseUrl;

    private BooksActions books;
    private AccountActions account;

    public ApiApp(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public BooksActions books() {
        if (books == null) books = new BooksActions(baseUrl);
        return books;
    }

    public AccountActions account() {
        if (account == null) account = new AccountActions(baseUrl);
        return account;
    }
}
