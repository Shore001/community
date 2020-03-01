package com.xs.mapper;


import com.xs.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, gmt_create, creator, tags) values (#{title},#{description},#{gmtCreate},#{creator},#{tags})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> getList(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{offset},#{pageSize}")
    List<Question> getListByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(Integer userId);
}
