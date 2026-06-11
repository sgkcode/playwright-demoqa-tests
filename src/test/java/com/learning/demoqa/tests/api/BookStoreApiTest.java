package com.learning.demoqa.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("api")
@Feature("BookStore API")
class BookStoreApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    @Story("Books")
    @DisplayName("GET /BookStore/v1/Books returns a non-empty list with required fields")
    void getAllBooksReturnsList() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/BookStore/v1/Books")
        .then()
            .statusCode(200)
            .body("books", not(empty()))
            .body("books[0].isbn",  not(emptyOrNullString()))
            .body("books[0].title", not(emptyOrNullString()))
            .body("books[0].author", not(emptyOrNullString()));
    }

    @Test
    @Story("Books")
    @DisplayName("Every book has isbn, title, and author")
    void allBooksHaveRequiredFields() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/BookStore/v1/Books")
        .then()
            .statusCode(200)
            .body("books.isbn",   everyItem(not(emptyOrNullString())))
            .body("books.title",  everyItem(not(emptyOrNullString())))
            .body("books.author", everyItem(not(emptyOrNullString())));
    }

    @Test
    @Story("Books")
    @DisplayName("Can retrieve a specific book by ISBN")
    void getBookByIsbn() {
        String isbn = "9781449325862"; // "Speaking JavaScript" — stable in demoqa fixture

        given()
            .contentType(ContentType.JSON)
            .queryParam("ISBN", isbn)
        .when()
            .get("/BookStore/v1/Book")
        .then()
            .statusCode(200)
            .body("isbn",  equalTo(isbn))
            .body("title", not(emptyOrNullString()));
    }

    @Test
    @Story("Authentication")
    @DisplayName("GenerateToken returns a valid token for valid credentials")
    void generateTokenForValidUser() {
        // Create user first (may return 406 if already exists — both outcomes are acceptable)
        String credentials = """
                {"userName":"demoqa_learner_99","password":"Test@1234!"}
                """;

        given().contentType(ContentType.JSON).body(credentials)
               .post("/Account/v1/User")
               .then().statusCode(anyOf(is(201), is(406)));

        given()
            .contentType(ContentType.JSON)
            .body(credentials)
        .when()
            .post("/Account/v1/GenerateToken")
        .then()
            .statusCode(200)
            .body("status", equalTo("Success"))
            .body("token", not(emptyOrNullString()));
    }

    @Test
    @Story("Authentication")
    @DisplayName("GenerateToken returns Failed status for wrong credentials")
    void invalidCredentialsFailTokenGeneration() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                  {"userName":"nobody","password":"wrongpassword"}
                  """)
        .when()
            .post("/Account/v1/GenerateToken")
        .then()
            .statusCode(200)
            .body("status", equalTo("Failed"))
            .body("result", equalTo("User authorization failed."));
    }
}
