package com.ls.librarysystem.service;

import com.ls.librarysystem.dao.UserDAO;
import com.ls.librarysystem.entity.AdminRole;
import com.ls.librarysystem.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDao;
    @Autowired
    AdminRoleService adminRoleService;

    public boolean isExist(String username) {
        User user = userDao.findByUsername(username);

        return null != user;
    }

    public List<User> list() {
        List<User> users =  userDao.findAll();
        List<AdminRole> roles;
        for (User user : users) {
            roles = adminRoleService.listRolesByUser(user.getUsername());
            user.setRoles(roles);
        }
        return users;
    }

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByUsername(String username) {return userDao.findByUsername(username);}

    public User get(String username, String password) {
        return userDao.getByUsernameAndPassword(username, password);
    }

    public void addOrUpdate(User user) {
        userDao.save(user);
    }

    public int register(User user) {
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if (username.equals("") || password.equals("")) {
            return 0;
        }

        boolean exist = isExist(username);

        if (exist) {
            return 2;
        }

        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        userDao.save(user);

        return 1;
    }
}
