package com.learning.google.tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

// API tests do not extend BaseTest — no browser setup needed here.
@Tag("api")
@Feature("JSONPlaceholder REST API")
class JsonPlaceholderApiTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @DisplayName("GET /posts returns 100 posts")
    void shouldGetAllPosts() {
        given()
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(100));
    }

    @Test
    @DisplayName("GET /posts/{id} returns the correct post")
    void shouldGetPostById() {
        int postId = 1;

        given()
                .pathParam("id", postId)
            .when()
                .get("/posts/{id}")
            .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", not(emptyString()))
                .body("body", not(emptyString()))
                .body("userId", notNullValue());
    }

    @Test
    @DisplayName("POST /posts creates a new post and returns 201")
    void shouldCreateNewPost() {
        String body = """
                {
                  "title": "Learning Test Automation",
                  "body": "Selenide and REST Assured are great tools",
                  "userId": 1
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Learning Test Automation"))
                .extract().response();

        assertThat(response.jsonPath().getInt("id")).isPositive();
    }

    @Test
    @DisplayName("PUT /posts/{id} updates an existing post")
    void shouldUpdatePost() {
        String body = """
                {
                  "id": 1,
                  "title": "Updated Title",
                  "body": "Updated body content",
                  "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("id", 1)
            .when()
                .put("/posts/{id}")
            .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"));
    }

    @Test
    @DisplayName("DELETE /posts/{id} returns 200")
    void shouldDeletePost() {
        given()
                .pathParam("id", 1)
            .when()
                .delete("/posts/{id}")
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /posts?userId={id} filters posts by user")
    void shouldFilterPostsByUserId() {
        int userId = 1;

        given()
                .queryParam("userId", userId)
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(userId)));
    }

    @Test
    @DisplayName("GET /posts/{id}/comments returns comments for that post")
    void shouldGetCommentsForPost() {
        given()
                .pathParam("id", 1)
            .when()
                .get("/posts/{id}/comments")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(1)));
    }

    @Test
    @DisplayName("GET /todos/{id} returns a todo with completed field")
    void shouldGetTodoById() {
        given()
                .pathParam("id", 1)
            .when()
                .get("/todos/{id}")
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", not(emptyString()))
                .body("completed", notNullValue());
    }
}
