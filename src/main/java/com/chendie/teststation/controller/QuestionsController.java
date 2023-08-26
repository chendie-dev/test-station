package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.Questions;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IQuestionsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@RestController
@RequestMapping("/questions")
public class QuestionsController {

    @Resource
    private IQuestionsService questionsService;

    @PostMapping("/addOrUpdateQuestions")
    public ResultView<Long> addQuestions(
            @RequestBody Questions questions
    ) {
        questionsService.saveOrUpdate(questions);
        return ResultView.success(questions.getQid());
    }

    @PostMapping("/getQuestions")
    public ResultView<List<Questions>> getQuestions(
            @RequestBody Questions questions
    ) {
        LambdaQueryWrapper<Questions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Questions::getUid, questions.getUid());
        List<Questions> questionsList = questionsService.list(queryWrapper);
        return ResultView.success(questionsList);
    }

    @PostMapping("/delete")
    public ResultView<Long> deleteQuestions(
            @RequestBody Questions questions
    ) {
        LambdaQueryWrapper<Questions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Questions::getQid, questions.getQid())
                .eq(Questions::getUid, questions.getUid());
        questionsService.remove(queryWrapper);
        return ResultView.success(questions.getQid());
    }

}
