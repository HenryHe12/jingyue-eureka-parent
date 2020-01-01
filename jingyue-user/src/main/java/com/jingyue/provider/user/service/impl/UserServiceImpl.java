package com.jingyue.provider.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jingyue.api.service.UserService;
import com.jingyue.common.entity.Result;
import com.jingyue.common.entity.StatusCode;
import com.jingyue.provider.user.mapper.UserMapper;
import com.jingyue.api.pojo.Dept;
import com.jingyue.api.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Dept selectUserDept(Long userId) {
        return null;
    }

    public Result selectUserAll(){
        QueryWrapper<User> queryWrapper  = new QueryWrapper<User>();
        QueryWrapper<User> wrapper = queryWrapper.isNotNull("id");
        return new Result(true, StatusCode.OK.getCode(), "查询成功", userMapper.selectList(wrapper));
    }
}
