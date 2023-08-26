package com.chendie.teststation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chendie.teststation.entity.Questions;
import com.chendie.teststation.mapper.QuestionsMapper;
import com.chendie.teststation.service.IQuestionsService;
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
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions> implements IQuestionsService {

}
