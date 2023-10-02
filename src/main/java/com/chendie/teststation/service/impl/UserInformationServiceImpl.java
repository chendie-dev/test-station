package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.UserInformation;
import com.chendie.teststation.mapper.UserInformationMapper;
import com.chendie.teststation.service.IUserInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@Service
public class UserInformationServiceImpl extends ServiceImpl<UserInformationMapper, UserInformation> implements IUserInformationService {

}
