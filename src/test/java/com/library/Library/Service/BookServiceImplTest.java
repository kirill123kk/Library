package com.library.Library.Service;

import com.library.Library.dto.BookRequest;
import com.library.Library.dto.BookResponse;
import com.library.Library.entity.BookEntity;
import com.library.Library.entity.UserEntity;
import com.library.Library.mapper.BookMapper;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.UserRepository;
import com.library.Library.service.BookServiceImpl;
import com.library.Library.service.UserContextService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private UserContextService userContextService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Получение всех книг пользователя — успех")
    void getBooksAll_Success() {
        String currentUser = "User1";
        var bookEntities = List.of(new BookEntity(1,"Книга1","Автор","Жанр","User1"), new BookEntity(2,"Книга2","Автор","Жанр","User1"));
        var bookResponses = List.of(new BookResponse(1,"Книга1", "Автор", "Жанр"), new BookResponse(2,"Книга2", "Автор", "Жанр"));

        when(userContextService.getCurrentUserDetails()).thenReturn(currentUser);
        when(userRepository.findByUsername(currentUser)).thenReturn(Optional.of(new UserEntity()));
        when(bookRepository.findAllByName(currentUser)).thenReturn(bookEntities);
        when(bookMapper.toDto(bookEntities)).thenReturn(bookResponses);

        List<BookResponse> response = bookService.getBooksAll();

        assertEquals(2, response.size());
        assertEquals("Книга1", response.get(0).getName());
        assertEquals("Книга2", response.get(1).getName());
    }

    @Test
    @DisplayName("Ошибка получения всех книг (пользователь не найден)")
    void getBooksAll_UserNotFound() {
        when(userContextService.getCurrentUserDetails()).thenReturn("unknownUser");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, bookService::getBooksAll);
    }

    @Test
    @DisplayName("Поиск книги по названию")
    void getBookByName_Success() {
        String bookName = "Книга1";
        var bookEntity = new BookEntity(1,"Книга1","Автор","Жанр","User1");
        var bookResponse = new BookResponse(1,"Книга1", "Автор", "Жанр");

        when(bookRepository.findByName(bookName)).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(bookResponse);

        BookResponse response = bookService.getBooksByName(bookName);

        assertEquals(bookName, response.getName());
    }

    @Test
    @DisplayName("Успешное обновление книги")
    void modifyBook_Success() {
        int bookId = 1;
        String currentUser = "User1";
        BookRequest bookRequest = new BookRequest("Книга2", "Новый автор", "Новый жанр");
        BookEntity bookEntity = new BookEntity(bookId,"Книга1","Автор","Жанр", currentUser);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(userContextService.getCurrentUserDetails()).thenReturn(currentUser);
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String response = bookService.modifyBook(bookId, bookRequest);

        assertEquals("Книга: Книга2 обновлена", response);
    }

    @Test
    @DisplayName("Ошибка обновления книги (нет доступа)")
    void modifyBook_AccessDenied() {
        int bookId = 1;
        String currentUser = "User1";
        BookRequest bookRequest = new BookRequest("Новое название", "Новый автор", "Новый жанр");
        BookEntity bookEntity = new BookEntity(bookId,"Книга1","Автор","Жанр", "User2");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(userContextService.getCurrentUserDetails()).thenReturn(currentUser);

        assertThrows(AccessDeniedException.class, () -> bookService.modifyBook(bookId, bookRequest));
    }
}
