package com.chendie.teststation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chendie.teststation.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
