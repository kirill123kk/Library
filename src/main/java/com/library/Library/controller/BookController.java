package com.library.Library.controller;

import com.library.Library.dto.BookRequest;
import com.library.Library.service.api.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    @PostMapping("/addBook")
    public String addBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @DeleteMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Integer id) {
        return bookService.removeBook(id);

    }

    @PutMapping("/updateBook/{id}")
    public String updateBook(@PathVariable Integer id, @RequestBody BookRequest bookRequest) {
        return bookService.modifyBook(id, bookRequest);
    }


}
