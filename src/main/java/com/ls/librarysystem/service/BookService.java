package com.ls.librarysystem.service;

import com.ls.librarysystem.dao.BookDAO;
import com.ls.librarysystem.entity.Book;
import com.ls.librarysystem.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDao;

    @Autowired
    private CategoryService categoryService;

    public List<Book> getAllBooks() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDao.findAll(sort);
    }

    public void addOrUpdateBook(Book book) {
        bookDao.save(book);
    }

    public void deleteById(int id) {
        bookDao.deleteById(id);
    }

    public List<Book> getBooksByCategoryId(int categoryId) {
        Category category = categoryService.findById(categoryId);
        return bookDao.findAllByCategory(category);
    }

    public List<Book> SearchBook(String keyword) {
        return bookDao.findAllByTitleLikeOrAuthorLike('%' + keyword + '%', '%' + keyword + '%');
    }
}
