package com.xs.controller;

import com.xs.mapper.QuestionMapper;
import com.xs.mapper.UserMapper;
import com.xs.model.Question;
import com.xs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tags,
            HttpServletRequest request,
            Model model){
        //用于表单数据回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tags);
        //验证用户是否登录
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length != 0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user!=null){//用户信息放入Session中
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
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
        question.setGmtCreate(System.currentTimeMillis());
        questionMapper.create(question);
        return "redirect:/";
    }
}
