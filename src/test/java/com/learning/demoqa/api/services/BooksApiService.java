package com.learning.demoqa.api.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BooksApiService extends BaseApiService {

    public BooksApiService(String baseUrl) {
        super(baseUrl);
    }

    public Response getAllBooks() {
        return given(spec).when().get("/BookStore/v1/Books");
    }

    public Response getBookByIsbn(String isbn) {
        return given(spec)
                .queryParam("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book");
    }
}
