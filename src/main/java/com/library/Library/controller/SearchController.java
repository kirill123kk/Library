package com.library.Library.controller;

import com.library.Library.dto.BookResponse;
import com.library.Library.service.api.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final BookService bookService;

    @GetMapping(path = "/all")
    @Operation(description = "Получение всех книг пользователя")
    public @ResponseBody List<BookResponse> getAll() {
        return bookService.getBooksAll();
    }


    @GetMapping(path = "/name/{name}")
    @Operation(description = "Получение  книги по названию")
    public @ResponseBody BookResponse getName(@PathVariable String name) {
        return bookService.getBooksByName(name);
    }

    @GetMapping(path = "/author/{author}")
    @Operation(description = "Получение всех книг по автору")
    public @ResponseBody List<BookResponse> getAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping(path = "/genre/{genre}")
    @Operation(description = "Получение всех книг по жанру")
    public @ResponseBody List<BookResponse> getGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

}
