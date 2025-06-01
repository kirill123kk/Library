package com.library.Library.service;

import com.library.Library.dto.BookRequest;
import com.library.Library.dto.BookResponse;
import com.library.Library.entity.BookEntity;
import com.library.Library.mapper.BookMapper;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.UserRepository;
import com.library.Library.security.JwtTokenUtils;
import com.library.Library.service.api.BookService;
import com.library.Library.service.api.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestHandler;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserContextService userContextService;
    private final UserRepository userRepository;


    @Override
    public List<BookResponse> getBooksAll() {


        userRepository.findByUsername(userContextService.getCurrentUserDetails())
                .orElseThrow(() ->
                        new EntityNotFoundException("Нет такого пользователя "));

        List<BookResponse> responses = bookMapper.toDto(bookRepository.findAllByName(userContextService.getCurrentUserDetails()));
        log.info("Пользователь получил список своих книг");
        return responses;
    }

    @Override
    public BookResponse getBooksByName(String bookName) {
        BookResponse bookResponse = bookMapper.toDto(bookRepository.findByName(bookName));
        log.info("Пользователь получил книгу по назхванию");
        return bookResponse;
    }

    @Override
    public List<BookResponse> getBooksByAuthor(String author) {
        List<BookResponse> responses = bookMapper.toDto(bookRepository.findByAuthor(author));
        log.info("Пользователь получил книги по автору");
        return responses;
    }

    @Override
    public List<BookResponse> getBooksByGenre(String genre) {
        List<BookResponse> responses = bookMapper.toDto(bookRepository.findByGenre(genre));
        log.info("Пользователь получил книги по жанру");
        return responses;

    }


    @Override
    public String createBook(BookRequest bookRequest) {
        bookRepository.save(bookMapper.toEntity(bookRequest, userContextService.getCurrentUserDetails()));
        return "Книга: " + bookRequest.getName() + " сохронена";
    }

    @Override
    public String removeBook(Integer id) {

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
        bookRepository.delete(book);
        return "Книга: " + book + " удалена";
    }

    @Transactional
    @Override
    public String modifyBook(Integer id, BookRequest bookRequest) {

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
        if (!book.getUsername().equals(userContextService.getCurrentUserDetails())) {
            throw new AccessDeniedException("Пользователь не владеет этой книгой");
        }
        book.setName(bookRequest.getName());
        book.setAuthor(bookRequest.getAuthor());
        book.setGenre(bookRequest.getGenre());

        bookRepository.save(book);
        return "Книга: " + bookRequest.getName() + " обновлена";
    }
}
