package com.ls.librarysystem.service;

import com.ls.librarysystem.dao.CategoryDAO;
import com.ls.librarysystem.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDao;

    public List<Category> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDao.findAll(sort);
    }

    public Category findById(int id) {
        return categoryDao.findById(id).orElse(null);
    }
}
