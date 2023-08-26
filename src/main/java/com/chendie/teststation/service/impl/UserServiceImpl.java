package com.chendie.teststation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chendie.teststation.entity.User;
import com.chendie.teststation.mapper.UserMapper;
import com.chendie.teststation.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
