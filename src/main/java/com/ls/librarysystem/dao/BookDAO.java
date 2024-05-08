package com.ls.librarysystem.dao;

import com.ls.librarysystem.entity.Book;
import com.ls.librarysystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDAO extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String title, String author);
}
