package com.xs.controller;


import com.xs.dto.PaginationDTO;
import com.xs.dto.QuestionDTO;
import com.xs.mapper.QuestionMapper;
import com.xs.mapper.UserMapper;
import com.xs.model.Question;
import com.xs.model.User;
import com.xs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    //访问根目录时返回结果
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex,
                        @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize
                        ){
        PaginationDTO pagination = questionService.getList(pageIndex,pageSize);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
