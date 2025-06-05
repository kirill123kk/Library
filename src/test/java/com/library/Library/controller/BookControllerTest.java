package com.library.Library.controller;

import com.library.Library.dto.BookRequest;
import com.library.Library.service.api.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    @DisplayName("Успешное добавление книги")
    void addBook_Success() {
        var bookRequest = new BookRequest("Книги", "Автор", "Жанр");
        when(bookService.createBook(any())).thenReturn("Книга добавлена");

        String response = bookController.addBook(bookRequest);

        assertEquals("Книга добавлена", response);
    }

    @Test
    @DisplayName("️Успешное удаление книги")
    void deleteBook_Success() {
        when(bookService.removeBook(anyInt())).thenReturn("Книга удалена");

        String response = bookController.deleteBook(1);

        assertEquals("Книга удалена", response);
    }

    @Test
    @DisplayName("Успешное обновление книги")
    void updateBook_Success() {
        var bookRequest = new BookRequest("Книги", "Автор", "Жанр");
        when(bookService.modifyBook(anyInt(), any())).thenReturn("Книга обновлена");

        String response = bookController.updateBook(1, bookRequest);

        assertEquals("Книга обновлена", response);
    }
}
