package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.Tag;
import com.chendie.teststation.mapper.TagMapper;
import com.chendie.teststation.service.ITagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
