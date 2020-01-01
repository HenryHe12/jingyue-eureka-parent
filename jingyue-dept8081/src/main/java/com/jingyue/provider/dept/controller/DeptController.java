package com.jingyue.provider.dept.controller;

import com.jingyue.api.service.DeptService;
import com.jingyue.common.entity.Result;
import com.jingyue.common.entity.StatusCode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod="processFallBackMethod")
    public Result getDeptAll(){
        System.out.println("--------------------------------8081-----------------------------");
        try{
            /*//自动一测试异常，
            int i = 10/0;*/
        } catch (Exception e){
           throw new RuntimeException("查询出错********************");
        }
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

    public Result processFallBackMethod(){
       return new Result(false, StatusCode.ERROR.getCode(),"查询失败",null);
    }
}
