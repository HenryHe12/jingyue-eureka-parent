package com.jingyue.consumer.ribbon.controller;

import com.jingyue.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerRibbonController {
    private static final String SERVICE_NAME_URL = "http://JINGYUE-DEPT";
    @Autowired
    private RestTemplate template;
    /**
     * 查询部门的所有信息
     * @return
     */
    @RequestMapping(value="/consumer/getAll")
    public Result getDeptAll(){
        return template.getForObject(SERVICE_NAME_URL + "/dept/list", Result.class);
    }

    /**
     * 通过部门id获取该部门下的所用用户
     * @param deptId 部门id
     * @return
     */
    @RequestMapping(value="/consumer/getUser/{deptId}")
    public Result getDeptByUser(@PathVariable("deptId") Long deptId){
        return template.getForObject(SERVICE_NAME_URL + "/dept/" + deptId, Result.class);
    }
}
