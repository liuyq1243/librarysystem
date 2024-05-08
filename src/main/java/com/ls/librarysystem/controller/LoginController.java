package com.ls.librarysystem.controller;

import com.ls.librarysystem.result.Result;
import com.ls.librarysystem.entity.User;
import com.ls.librarysystem.result.ResultFactory;
import com.ls.librarysystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser, HttpSession session) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            User user = userService.findByUsername(username);
            if (!user.isEnabled()) {
                return ResultFactory.buildFailureResult("该用户已被禁用");
            }
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(usernamePasswordToken);
        } catch (AuthenticationException e) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailureResult(message);
        }
    }

    @GetMapping("/api/login")
    public Result login() {
        String msg = "非法登录";
        return ResultFactory.buildSuccessResult(msg);
    }

    @GetMapping("/api/logout")
    @ResponseBody
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String msg = "成功登出";
        System.out.println();
        return ResultFactory.buildSuccessResult(msg);
    }

    @PostMapping("/api/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {
        int status = userService.register(requestUser);
        switch (status) {
            case 0:
                return ResultFactory.buildFailureResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailureResult("用户已存在");
        }
        return ResultFactory.buildFailureResult("未知错误");
    }

    @GetMapping("/api/authentication")
    @ResponseBody
    public Result authentication(){
        return ResultFactory.buildSuccessResult("身份认证成功");
    }
}
