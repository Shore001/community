package com.xs.controller;

import com.xs.dto.QuestionDTO;
import com.xs.model.Question;
import com.xs.model.User;
import com.xs.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tags,
            @RequestParam(value = "id",required = false)Long id,
            HttpServletRequest request,
            Model model){
        //用于表单数据回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tags);

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //验证表单数据
        if (title==null || "".equals(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null || "".equals(description)){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if (tags==null || "".equals(tags)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        question.setId(id);
        question.setLikeCount(0);
        question.setViewCount(0);
        question.setCommentCount(0);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable Long id,Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTags());
        model.addAttribute("id",questionDTO.getId());
        return "publish";
    }
}
