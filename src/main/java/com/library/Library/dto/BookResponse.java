package com.library.Library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO ответа с информацией о книге")
public class BookResponse {

    @Schema(description = "Идентификатор книги", example = "1")
    private Integer bookId;

    @Schema(description = "Название книги", example = "1984")
    private String name;

    @Schema(description = "Автор книги", example = "George Orwell")
    private String author;

    @Schema(description = "Жанр книги", example = "Dystopian Fiction")
    private String genre;
}
