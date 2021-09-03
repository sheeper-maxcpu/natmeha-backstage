package com.lanqiao.natmeha.controller;

import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.UUID;

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
            httpSession.setAttribute("logUser", logUser);
            /*if (logUser.getType() == 99) {
                return "index"; // 重定向只可以定向到控制器，不可定向到模板页面
            } else if (logUser.getType() == 0) {
                return "index_county";
            } else if (logUser.getType() == 1) {
                return "index_municipal";
            } else if (logUser.getType() == 2) {
                return "index_provincial";
            }else if (logUser.getType() == 3) {
                return "index_county_audit";
            }else if (logUser.getType() == 4) {
                return "index_municipal_audit";
            }else if (logUser.getType() == 5) {
                return "index_provincial_audit";
            }*/
            return "main";
        } else {
            return "user/login";
        }
    }

    @GetMapping("/index")
    public String indexs(HttpSession httpSession) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = new User();
        user1.setUsername(((UserDetails) principal).getUsername());
        user1.setPassword(((UserDetails) principal).getPassword());
        final User logUser = this.userService.selectForLogin(user1);
        if (logUser.getType() == 99) {
                return "index"; // 重定向只可以定向到控制器，不可定向到模板页面
            } else if (logUser.getType() == 0) {
                return "index_county";
            } else if (logUser.getType() == 1) {
                return "index_municipal";
            } else if (logUser.getType() == 2) {
                return "index_provincial";
            }else if (logUser.getType() == 3) {
                return "index_county_audit";
            }else if (logUser.getType() == 4) {
                return "index_municipal_audit";
            }else if (logUser.getType() == 5) {
                return "index_provincial_audit";
            }
        return "user/login";
    }
    //注册
    @RequestMapping("/user/reg")
    public Object insertForReg(User user){
        if (user.getUsername() != null && user.getPassword() != null){
            user.setItemcode(UUID.randomUUID().toString());
            user.setType(99);
            userService.insertForReg(user);
//            userService.insertForRegOrg(organization);
            return "user/login";
        }
        return "user/reg";
    }

}
