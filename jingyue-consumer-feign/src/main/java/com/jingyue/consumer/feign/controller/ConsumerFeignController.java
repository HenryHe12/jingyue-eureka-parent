package com.jingyue.consumer.feign.controller;

import com.jingyue.api.service.DeptFeiginClientService;
import com.jingyue.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerFeignController {
    @Autowired
    private DeptFeiginClientService clientService = null;
    /**
     * 查询部门的所有信息
     * @return
     */
    @RequestMapping(value="/consumer/getAll")
    public Result getDeptAll(){
        return this.clientService.getDeptAll();
    }

    /**
     * 通过部门id获取该部门下的所用用户
     * @param deptId 部门id
     * @return
     */
    @RequestMapping(value="/consumer/getUser/{deptId}")
    public Result getDeptByUser(@PathVariable("deptId") Long deptId){
        return this.clientService.getDeptByUser(deptId);
    }
}
