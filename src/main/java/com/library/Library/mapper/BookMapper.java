package com.library.Library.mapper;

import com.library.Library.dto.BookRequest;
import com.library.Library.dto.BookResponse;
import com.library.Library.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "bookId", ignore = true)
    BookEntity toEntity (BookRequest bookRequest, String username);
    BookResponse toDto (BookEntity bookEntity);

    List<BookResponse> toDto ( List<BookEntity> bookEntity);
}
