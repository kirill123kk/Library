package com.library.Library.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO для запроса на создание или обновление книги")
public class BookRequest {

    @Schema(description = "Название книги", example = "1984")
    private String name;

    @Schema(description = "Автор книги", example = "George Orwell")
    private String author;

    @Schema(description = "Жанр книги", example = "Dystopian Fiction")
    private String genre;

}
