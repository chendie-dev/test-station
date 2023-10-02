package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.Information;
import com.chendie.teststation.mapper.InformationMapper;
import com.chendie.teststation.service.IInformationService;
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
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements IInformationService {

}
