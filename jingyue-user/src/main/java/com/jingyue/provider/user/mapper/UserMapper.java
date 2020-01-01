package com.jingyue.provider.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingyue.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
