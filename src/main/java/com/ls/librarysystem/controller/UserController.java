package com.ls.librarysystem.controller;

import com.ls.librarysystem.entity.AdminPermission;
import com.ls.librarysystem.entity.AdminRole;
import com.ls.librarysystem.entity.User;
import com.ls.librarysystem.result.Result;
import com.ls.librarysystem.result.ResultFactory;
import com.ls.librarysystem.service.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    @GetMapping("/api/admin/user")
    public List<User> listUsers() throws Exception {
        return userService.list();
    }

    @PutMapping("/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User requestUser) {
        User user = userService.findByUsername(requestUser.getUsername());
        user.setEnabled(requestUser.isEnabled());
        userService.addOrUpdate(user);
        String message = "用户" + requestUser.getUsername() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody User requestUser) {
        User user = userService.findByUsername(requestUser.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        user.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        user.setPassword(encodedPassword);
        userService.addOrUpdate(user);
        String message = "重置密码成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser) {
        User user = userService.findByUsername(requestUser.getUsername());
        user.setName(requestUser.getName());
        user.setPhone(requestUser.getPhone());
        user.setEmail(requestUser.getEmail());
        userService.addOrUpdate(user);
        adminUserRoleService.saveRoleChanges(user.getId(),requestUser.getRoles());
        String message = "修改用户信息成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/admin/role")
    public List<AdminRole> listRoles(){
        return adminRoleService.list();
    }

    @PutMapping("/api/admin/role/status")
    public Result updateRoleStatus(@RequestBody AdminRole requestRole) {
        AdminRole adminRole = adminRoleService.findById(requestRole.getId());
        adminRole.setEnabled(requestRole.isEnabled());
        adminRoleService.addOrUpdate(adminRole);
        String message = "用户" + adminRole.getNameZh() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/role")
    public Result editRole(@RequestBody AdminRole requestRole) {
        adminRoleService.addOrUpdate(requestRole);
        adminRolePermissionService.savePermChanges(requestRole.getId(), requestRole.getPerms());
        String message = "修改角色信息成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/admin/perm")
    public List<AdminPermission> listPerms() {
        return adminPermissionService.list();
    }
}
