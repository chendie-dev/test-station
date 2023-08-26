package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.Questions;
import com.chendie.teststation.mapper.QuestionsMapper;
import service.IQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
