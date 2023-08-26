package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.mapper.PaperMapper;
import service.IPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.baomidou.mybatisplus.generator.config.OutputFile.entity;
import static com.baomidou.mybatisplus.generator.config.OutputFile.mapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

}
