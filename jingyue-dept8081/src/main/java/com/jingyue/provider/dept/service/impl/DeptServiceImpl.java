package com.jingyue.provider.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jingyue.api.service.DeptService;
import com.jingyue.provider.dept.mapper.DeptMapper;
import com.jingyue.common.entity.Result;
import com.jingyue.common.entity.StatusCode;
import com.jingyue.api.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有用户信息
     *
     * @return List<Dept> 返回所有用户信息
     */
    @Override
    public Result selectDeptAll() {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
        QueryWrapper<Dept> wrapper = queryWrapper.isNotNull("id");
        //List<Dept> depts = deptMapper.selectAll();
        return new Result(true, StatusCode.OK.getCode(), "查询成功", deptMapper.selectList(wrapper));
    }

    /**
     * 通过部门id查询用户信息
     *
     * @param deptId 部门id
     * @return
     */
    @Override
    public Result selectDeptByUser(Long deptId) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", deptMapper.selectDeptByUser(deptId));
    }
}
