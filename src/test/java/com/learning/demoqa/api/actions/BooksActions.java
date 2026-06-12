package com.learning.demoqa.api.actions;

import com.learning.demoqa.api.models.response.BookResponseModel;
import com.learning.demoqa.api.models.response.BooksListResponseModel;
import com.learning.demoqa.api.services.BooksApiService;

import java.util.List;

public class BooksActions {

    private final BooksApiService booksService;

    public BooksActions(String baseUrl) {
        booksService = new BooksApiService(baseUrl);
    }

    public List<BookResponseModel> getAllBooksAndGetList() {
        return booksService.getAllBooks().as(BooksListResponseModel.class).getBooks();
    }

    public BookResponseModel getBookByIsbnAndGetDetails(String isbn) {
        return booksService.getBookByIsbn(isbn).as(BookResponseModel.class);
    }
}
