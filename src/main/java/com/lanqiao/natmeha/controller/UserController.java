package com.lanqiao.natmeha.controller;

import com.lanqiao.natmeha.model.Organization;
import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * @auther cheng
 * @create 2021-08-22 17:00
 */
@Controller
@Slf4j
@SessionAttributes({"logUser"})
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(){
        return "user/login";
    }

    //登录
    @RequestMapping("/user/login")
    public String selectForLogin(User user, Model model, HttpSession httpSession){

        final User logUser = this.userService.selectForLogin(user);
        if (logUser != null) {
            model.addAttribute("logUser", logUser);
            return "redirect:/numManage"; // 重定向只可以定向到控制器，不可定向到模板页面

        } else {
            return "user/login";
        }
    }

    //注册
    @RequestMapping("/user/reg")
    public Object insertForReg(User user, Organization organization){
        if (user.getUsername() != null && user.getPassword() != null){
            user.setType(1);
            userService.insertForReg(user);
//            userService.insertForRegOrg(organization);
            return "user/login";
        }
        return "user/reg";
    }

}
