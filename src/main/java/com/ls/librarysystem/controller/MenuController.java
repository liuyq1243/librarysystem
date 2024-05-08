package com.ls.librarysystem.controller;

import com.ls.librarysystem.entity.AdminMenu;
import com.ls.librarysystem.result.Result;
import com.ls.librarysystem.result.ResultFactory;
import com.ls.librarysystem.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private AdminMenuService adminMenuService;

    @CrossOrigin
    @GetMapping("/api/menu")
    public List<AdminMenu> getMenu() {
        List<AdminMenu> menus = adminMenuService.getMenusByCurrentUser();
        for (AdminMenu menu : menus) {
            menu.setChildren(adminMenuService.getAllByParentId(menu.getId()));
        }

        System.out.println("menu size: " + menus.size());

        menus.removeIf(menu -> menu.getParentId() != 0);
        return menus;
    }
}
