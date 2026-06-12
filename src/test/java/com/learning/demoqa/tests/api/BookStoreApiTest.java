package com.learning.demoqa.tests.api;

import com.learning.demoqa.api.models.response.BookResponseModel;
import com.learning.demoqa.api.models.response.TokenResponseModel;
import com.learning.demoqa.tests.BaseApiTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@Feature("BookStore API")
class BookStoreApiTest extends BaseApiTest {

    @Test
    @Story("Books")
    @DisplayName("GET /BookStore/v1/Books returns a non-empty list with required fields")
    void verifyGetAllBooksReturnsListTest() {
        List<BookResponseModel> books = app.books().getAllBooksAndGetList();

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getIsbn()).isNotBlank();
        assertThat(books.get(0).getTitle()).isNotBlank();
        assertThat(books.get(0).getAuthor()).isNotBlank();
    }

    @Test
    @Story("Books")
    @DisplayName("Every book has isbn, title, and author")
    void verifyAllBooksHaveRequiredFieldsTest() {
        List<BookResponseModel> books = app.books().getAllBooksAndGetList();

        assertThat(books).allSatisfy(book -> {
            assertThat(book.getIsbn()).isNotBlank();
            assertThat(book.getTitle()).isNotBlank();
            assertThat(book.getAuthor()).isNotBlank();
        });
    }

    @Test
    @Story("Books")
    @DisplayName("Can retrieve a specific book by ISBN")
    void verifyGetBookByIsbnTest() {
        String isbn = "9781449325862"; // "Speaking JavaScript" — stable in demoqa fixture

        BookResponseModel book = app.books().getBookByIsbnAndGetDetails(isbn);

        assertThat(book.getIsbn()).isEqualTo(isbn);
        assertThat(book.getTitle()).isNotBlank();
    }

    @Test
    @Story("Authentication")
    @DisplayName("GenerateToken returns a valid token for valid credentials")
    void verifyGenerateTokenForValidUserTest() {
        TokenResponseModel response = app.account().createUserAndGenerateToken("demoqa_learner_99", "Test@1234!");

        assertThat(response.getStatus()).isEqualTo("Success");
        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    @Story("Authentication")
    @DisplayName("GenerateToken returns Failed status for wrong credentials")
    void verifyInvalidCredentialsFailTokenGenerationTest() {
        TokenResponseModel response = app.account().generateTokenAndGetResponse("nobody", "wrongpassword");

        assertThat(response.getStatus()).isEqualTo("Failed");
        assertThat(response.getResult()).isEqualTo("User authorization failed.");
    }
}
