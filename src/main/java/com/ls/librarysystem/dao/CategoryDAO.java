package com.ls.librarysystem.dao;

import com.ls.librarysystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
