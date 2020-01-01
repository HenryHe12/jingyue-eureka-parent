package com.jingyue.api.service;

import com.jingyue.common.entity.Result;

/**
 * 定义部门接口
 */
public interface DeptService {
    /**
     * 通过部门编号查询该部门下的所有用户
     * @return
     */
    Result selectDeptAll();

    /**
     * 通过部门编号查询该部门下的员工
     * @param deptId
     * @return
     */
    Result selectDeptByUser(Long deptId);

}
