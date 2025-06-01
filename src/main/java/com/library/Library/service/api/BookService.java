package com.library.Library.service.api;

import com.library.Library.dto.BookRequest;
import com.library.Library.dto.BookResponse;

import java.util.List;

public interface BookService {


    List<BookResponse> getBooksAll ();
    BookResponse getBooksByName(String bookName);
    List<BookResponse> getBooksByAuthor(String author);

    List<BookResponse> getBooksByGenre(String genre);
    String createBook(BookRequest bookRequest);

    String removeBook(Integer id);
    String  modifyBook(Integer id, BookRequest bookRequest);
}
