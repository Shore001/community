package com.xs.controller;

import com.xs.dto.CommentDTO;
import com.xs.dto.QuestionDTO;
import com.xs.enums.CommentTypeEnum;
import com.xs.service.CommentService;
import com.xs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable Long id, Model model){

        List<CommentDTO> commentDTO = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTO);

        return "question";
    }
}
