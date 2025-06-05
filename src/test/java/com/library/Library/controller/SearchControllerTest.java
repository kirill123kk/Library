package com.library.Library.controller;

import com.library.Library.dto.BookResponse;
import com.library.Library.service.api.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    @Mock
    private BookService bookService;


    @Test
    @DisplayName("Получение всех книг пользователя")
    void getAllBooks_Success() {
        var books = List.of(new BookResponse(1,"Книга1", "Автор1","Жанр1"), new BookResponse(2,"Книга2", "Автор2","Жанр2"));
        when(bookService.getBooksAll()).thenReturn(books);

        var response = searchController.getAll();

        assertEquals(2, response.size());
        assertEquals("Книга1", response.get(0).getName());
        assertEquals("Книга2", response.get(1).getName());
    }

    @Test
    @DisplayName("Поиск книги по названию")
    void getBookByName_Success() {
        BookResponse book = new BookResponse(1,"Книга1", "Автор1","Жанр1");
        when(bookService.getBooksByName(anyString())).thenReturn(book);

        var response = searchController.getName("Книга1");

        assertEquals("Книга1", response.getName());
        assertEquals("Автор1", response.getAuthor());
    }

    @Test
    @DisplayName("Поиск книг по автору")
    void getBooksByAuthor_Success() {
        List<BookResponse> books = List.of(new BookResponse(1,"Книга1", "Автор1","Жанр1"), new BookResponse(1,"Книга2", "Автор1","Жанр1"));
        when(bookService.getBooksByAuthor(anyString())).thenReturn(books);

        var response = searchController.getAuthor("Автор1");

        assertEquals(2, response.size());
        assertEquals("Автор1", response.get(0).getAuthor());
        assertEquals("Автор1", response.get(1).getAuthor());
    }

    @Test
    @DisplayName("Поиск книг по жанру")
    void getBooksByGenre_Success() {
        var books = List.of(new BookResponse(1,"Книга1", "Автор1","Жанр1"), new BookResponse(1,"Книга2", "Автор1","Жанр1"));

        when(bookService.getBooksByGenre(anyString())).thenReturn(books);

        var response = searchController.getGenre("Жанр1");

        assertEquals(2, response.size());
        assertEquals("Жанр1", response.get(0).getGenre());
        assertEquals("Жанр1", response.get(1).getGenre());
    }

}
