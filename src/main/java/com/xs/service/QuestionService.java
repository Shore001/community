package com.xs.service;

import com.xs.dto.PaginationDTO;
import com.xs.dto.QuestionDTO;
import com.xs.mapper.QuestionMapper;
import com.xs.mapper.UserMapper;
import com.xs.model.Question;
import com.xs.model.User;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsResponseControl;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO getList(Integer pageIndex, Integer pageSize) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = questionMapper.count();//查询总数据量
        Integer totalPage = (int)Math.ceil(count/pageSize.doubleValue());//计算总页数
        //判断是否是第一页
        if (pageIndex<1){
            pageIndex=1;
        }
        //判断是否是最后一页
        if(pageIndex>totalPage){
            pageIndex=totalPage;
        }
        paginationDTO.setPagination(totalPage,pageIndex);//设置分页数据
        //数据库分页查询偏移量
        int offset = (pageIndex-1)*pageSize;
        List<Question> questionList = questionMapper.getList(offset,pageSize);//分页查询
        List<QuestionDTO> questionDTOList = new ArrayList<>();//查询结果的数据传输对象集合
        for (Question question:questionList){
            User user = userMapper.findById(question.getCreator());//查询作者
            QuestionDTO questionDTO = new QuestionDTO();//查询结果的数据传输对象
            BeanUtils.copyProperties(question,questionDTO);//对象属性copy
            questionDTO.setUser(user);//保存问题发布人信息
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO getList(Integer userId, Integer pageIndex, Integer pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = questionMapper.countByUserId(userId);//查询总数据量
        Integer totalPage = (int)Math.ceil(count/pageSize.doubleValue());//计算总页数
       //判断是否是第一页
        if (pageIndex<1){
            pageIndex=1;
        }
        //判断是否是最后一页
        if(pageIndex>totalPage){
            pageIndex=totalPage;
        }
        paginationDTO.setPagination(totalPage,pageIndex);//设置分页数据
        //数据库分页查询偏移量
        int offset = (pageIndex-1)*pageSize;
        List<Question> questionList = questionMapper.getListByUserId(userId,offset,pageSize);//分页查询
        List<QuestionDTO> questionDTOList = new ArrayList<>();//查询结果的数据传输对象集合
        for (Question question:questionList){
            User user = userMapper.findById(question.getCreator());//查询作者
            QuestionDTO questionDTO = new QuestionDTO();//查询结果的数据传输对象
            BeanUtils.copyProperties(question,questionDTO);//对象属性copy
            questionDTO.setUser(user);//保存问题发布人信息
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }
}
