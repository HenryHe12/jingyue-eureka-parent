package com.jingyue.api.service;

import com.jingyue.api.pojo.Dept;
import com.jingyue.common.entity.Result;

public interface UserService {
    /**
     * 通过用户id查询用户所在的部门信息
     * @param userId
     * @return
     */
    Dept selectUserDept(Long userId);

    Result selectUserAll();
}
