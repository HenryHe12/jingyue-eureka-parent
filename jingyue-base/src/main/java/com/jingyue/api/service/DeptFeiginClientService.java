package com.jingyue.api.service;

import com.jingyue.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "JINGYUE-DEPT")
public interface DeptFeiginClientService {

    @RequestMapping(value="/dept/list")
    Result getDeptAll();

    /**
     * 通过部门id获取该部门下的所用用户
     *
     * @param deptId 部门id
     * @return
     */
    @RequestMapping(value="/dept/{deptId}")
    Result getDeptByUser(@PathVariable("deptId") Long deptId);
}
