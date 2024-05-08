package com.ls.librarysystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ls.librarysystem.entity.AdminPermission;

public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);
}
