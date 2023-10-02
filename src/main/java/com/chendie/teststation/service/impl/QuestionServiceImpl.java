package com.chendie.teststation.service.impl;

import com.chendie.teststation.entity.Question;
import com.chendie.teststation.mapper.QuestionMapper;
import com.chendie.teststation.service.IQuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
