package com.jingyue.provider.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingyue.api.pojo.Dept;
import com.jingyue.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper  extends BaseMapper<Dept> {
    /**
     * 通过部门编号获取部门下所有的用户
     * @param deptId
     * @return
     */
    List<User> selectDeptByUser(@Param("deptId") Long deptId);
}
