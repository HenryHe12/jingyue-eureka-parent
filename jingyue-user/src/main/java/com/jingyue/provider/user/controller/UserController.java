package com.jingyue.provider.user.controller;

import com.jingyue.api.service.UserService;
import com.jingyue.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询用户controller
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询所有的用户信息
     * @return List<User> 返回类型
     */
    @RequestMapping(value="/user/getAll")
    public Result getUser(){
        return userService.selectUserAll();
    }
}
