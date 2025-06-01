package com.library.Library.repository;

import com.library.Library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<BookEntity,Integer> {
    BookEntity findByName(String name);

    List<BookEntity> findByGenre(String genre);

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findAllByName(String name);

}
