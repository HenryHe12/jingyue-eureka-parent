package com.jingyue.provider.dept.controller;

import com.jingyue.api.service.DeptService;
import com.jingyue.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门controller
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门的所有信息
     * @return
     */
    @RequestMapping(value="/dept/list")
    public Result getDeptAll(){
        System.out.println("--------------------------------8082-----------------------------");
        return deptService.selectDeptAll();
    }

    /**
     * 通过部门id获取该部门下的所用用户
     * @param deptId 部门id
     * @return
     */
    @RequestMapping(value="/dept/{deptId}")
    public Result getDeptByUser(@PathVariable("deptId") Long deptId){
        return deptService.selectDeptByUser(deptId);
    }
}
