package com.xs.controller;


import com.xs.mapper.UserMapper;
import com.xs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    //访问根目录时返回结果

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String hello(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user!=null){//用户信息放入Session中
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
