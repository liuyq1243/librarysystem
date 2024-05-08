package com.ls.librarysystem.controller;

import com.ls.librarysystem.entity.Book;
import com.ls.librarysystem.service.BookService;
import com.ls.librarysystem.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/api/admin/content/books")
    public Book addOrUpdateBook(@RequestBody Book book) {
        bookService.addOrUpdateBook(book);
        return book;
    }

    @PostMapping("/api/admin/content/books/delete")
    public void deleteBook(@RequestBody Book book) {
        bookService.deleteById(book.getId());
    }

    @GetMapping("/api/categories/{cid}/books")
    public List<Book> getBooksByCategory(@PathVariable("cid") int cid) {
        if (0 != cid) {
            return bookService.getBooksByCategoryId(cid);
        } else {
            return getAllBooks();
        }
    }

    @GetMapping("/api/search")
    public List<Book> getBooksBySearch(@RequestParam("keywords") String keywords) {
        if ("".equals(keywords)) {
            return getAllBooks();
        } else {
            return bookService.SearchBook(keywords);
        }
    }

    @PostMapping("/api/admin/content/books/covers")
    public String coversUpload(MultipartFile file){
        // 上传到本地硬盘
        String folder = "D:/code/Java/img";
        File imgFolder = new File(folder);
        File f = new File(imgFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            return "http://localhost:8443/api/file/" + f.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
