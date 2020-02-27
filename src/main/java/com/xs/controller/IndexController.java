package com.xs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    //访问根目录时返回结果
    @GetMapping("/")
    public String hello(){
        return "index";
    }
}