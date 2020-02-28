package com.xs.mapper;


import com.xs.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modify) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify})")
    public void insert(User user);

    @Select("select * from USER where TOKEN=#{token}")
    User findByToken(String token);
}
