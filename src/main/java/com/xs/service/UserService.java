package com.xs.service;

import com.xs.mapper.UserMapper;
import com.xs.model.User;
import com.xs.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());//查询条件 account_id=?
        List<User> users = userMapper.selectByExample(example);//查询表中是否存在该用户
        if (users.size()==0){//用户不存在，新增一条
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else {//用户存在，更新github的用户信息及token
            User updateUser = new User();
            updateUser.setGmtModify(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example1 = new UserExample();
            example1.createCriteria().andIdEqualTo(users.get(0).getId());//更新条件 id=?
            userMapper.updateByExampleSelective(updateUser,example1);//有选择的更新
        }
    }
}
