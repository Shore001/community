package com.xs.service;

import com.xs.dto.PaginationDTO;
import com.xs.dto.QuestionDTO;
import com.xs.enums.CustomizeErrorCode;
import com.xs.exception.CustomizeException;
import com.xs.mapper.QuestionExtMapper;
import com.xs.mapper.QuestionMapper;
import com.xs.mapper.UserMapper;
import com.xs.model.Question;
import com.xs.model.QuestionExample;
import com.xs.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO getList(Integer pageIndex, Integer pageSize) {

        PaginationDTO paginationDTO = new PaginationDTO();
        long count = questionMapper.countByExample(null);//查询总数据量
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
        QuestionExample example = new QuestionExample();
        /*分页查询*/
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(null,new RowBounds(offset,pageSize));//分页查询
        List<QuestionDTO> questionDTOList = new ArrayList<>();//查询结果的数据传输对象集合
        questionList.forEach(question -> {
            User user = userMapper.selectByPrimaryKey(question.getCreator());//查询作者
            QuestionDTO questionDTO = new QuestionDTO();//查询结果的数据传输对象
            BeanUtils.copyProperties(question,questionDTO);//对象属性copy
            questionDTO.setUser(user);//保存问题发布人信息
            questionDTOList.add(questionDTO);
        });
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO getList(Long userId, Integer pageIndex, Integer pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);//设置统计条件 creator=?
        long count = questionMapper.countByExample(example);//查询总数据量
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
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(null,new RowBounds(offset,pageSize));//分页查询
        List<QuestionDTO> questionDTOList = new ArrayList<>();//查询结果的数据传输对象集合
        for (Question question:questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());//查询作者
            QuestionDTO questionDTO = new QuestionDTO();//查询结果的数据传输对象
            BeanUtils.copyProperties(question,questionDTO);//对象属性copy
            questionDTO.setUser(user);//保存问题发布人信息
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());//查询作者
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){//新增问题
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.insert(question);
        }else {//更新问题
            question.setGmtModify(System.currentTimeMillis());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updateResult = questionMapper.updateByExampleSelective(question, example);
            if (updateResult!=1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 更新阅读数
     * @param id
     */
    public void incView(Long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);
    }
}
